package com.killard.board.environment;

import com.killard.board.card.Action;
import com.killard.board.card.Board;
import com.killard.board.card.Card;
import com.killard.board.card.MetaCard;
import com.killard.board.card.Player;
import com.killard.board.card.Skill;
import com.killard.board.card.action.CastCardAction;
import com.killard.board.card.action.EndPlayerCallAction;
import com.killard.board.card.action.EndTurnAction;
import com.killard.board.card.action.EquipCardAction;
import com.killard.board.card.record.ExecutableActionUtil;
import com.killard.board.environment.event.ActionEvent;
import com.killard.board.environment.event.ActionListener;
import com.killard.board.environment.event.StateEvent;
import com.killard.board.environment.event.StateListener;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * <p>
 * This class defines manager that execute all operations on the board.
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public abstract class BoardManager<T extends BoardManager> implements Board<T>, StateListener {

    private final SortedMap<ActionListener, SortedSet<Comparable>> listeners = new TreeMap<ActionListener, SortedSet<Comparable>>();

    private final Map<Class<? extends Action>, ExecutableAction> executables =
            ExecutableActionUtil.buildExecutableActionsMap();

    public BoardManager() {
    }

    public Player getActivePlayer() {
        for (Player player : getPlayers()) {
            if (player.isCalled()) return player;
        }
        return getCurrentPlayer();
    }

    public void playCard(int cardIndex, int cardPosition, int targetPlayerPosition) throws BoardException {
        Player owner = getCurrentPlayer();
        MetaCard card = owner.getDealtCard(cardIndex);
        Player target = getPlayer(targetPlayerPosition);
        Card instance = createCardRecord(card, owner, target, cardPosition);
        if (instance.isEquippable()) {
            executeAction(new EquipCardAction(owner, instance));
        } else {
            for (Skill skill : instance.getSkills()) {
                executeAction(new CastCardAction(owner, instance, skill, instance));
            }
        }
    }

    public void cast(int cardPosition, int skillIndex) throws BoardException {
        cast(cardPosition, skillIndex, 1, cardPosition);
    }

    public void cast(int cardPosition, int skillIndex, int targetPlayerPosition, int targetCardPosition)
            throws BoardException {
        Player player = getCurrentPlayer();
        Card card = player.getEquippedCard(cardPosition);

        Player targetPlayer = getPlayer(targetPlayerPosition);
        Card targetCard = targetPlayer.getEquippedCard(targetCardPosition);
        if (targetCard == null) targetCard = card;

        executeAction(new CastCardAction(player, card, card.getSkills()[skillIndex], targetCard));
    }

    public void endTurn() throws BoardException {
        executeAction(new EndTurnAction(this, getCurrentPlayer()));
    }

    public void endCall() throws BoardException {
        executeAction(new EndPlayerCallAction(getCurrentPlayer(), getCurrentPlayer()));
    }

    public void addActionListener(ActionListener listener, Comparable host) {
        if (!listeners.containsKey(listener)) listeners.put(listener, new TreeSet<Comparable>());
        listeners.get(listener).add(host);
    }

    public ActionListener[] getActionListeners() {
        return listeners.keySet().toArray(new ActionListener[listeners.size()]);
    }

    public void removeActionListener(ActionListener listener) {
        listeners.remove(listener);
    }

    public void stateChanged(StateEvent event) {
    }

    protected Comparable[] getActionListenerHost(ActionListener listener) {
        SortedSet<Comparable> hosts = listeners.get(listener);
        return hosts.toArray(new Comparable[hosts.size()]);
    }

    protected boolean invokeActionListener(Action action, ActionListener listener,
                                           Method method, Class actionClass, boolean selfTargeted, Object host)
            throws BoardException {
        if (selfTargeted && action.getTarget() != host) return false;

        try {
            Object result = method.invoke(listener, this, host, action);
            if (result == null) return false;
            if (result.equals(Boolean.TRUE)) return false;
            if (result instanceof List) {
                List actions = (List) result;
                executeActions(actions);
            } else if (result instanceof Action) {
                executeAction((Action) result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    protected boolean invokeActionListener(Action action, ActionListener listener,
                                           Method method, Class actionClass, boolean selfTargeted)
            throws BoardException {
        if (!actionClass.isAssignableFrom(action.getClass())) return false;

        for (Comparable host : getActionListenerHost(listener)) {
            if (invokeActionListener(action, listener, method, actionClass, selfTargeted, host)) return true;
        }
        return false;
    }

    protected boolean validateAction(ActionEvent event) throws BoardException {
        for (ActionListener listener : getActionListeners()) {
            for (Method method : listener.getClass().getMethods()) {
                ActionValidator annotation = method.getAnnotation(ActionValidator.class);
                if (annotation == null) continue;
                Class actionClass = annotation.actionClass();
                boolean selfTargeted = annotation.selfTargeted();
                if (invokeActionListener(event.getAction(), listener, method, actionClass, selfTargeted)) return false;
            }
        }
        return true;
    }

    protected void fireActionEventBefore(ActionEvent event) throws BoardException {
        for (ActionListener listener : getActionListeners()) {
            for (Method method : listener.getClass().getMethods()) {
                BeforeAction annotation = method.getAnnotation(BeforeAction.class);
                if (annotation == null) continue;
                Class actionClass = annotation.actionClass();
                boolean selfTargeted = annotation.selfTargeted();
                invokeActionListener(event.getAction(), listener, method, actionClass, selfTargeted);
            }
        }
    }

    protected void fireActionEventAfter(ActionEvent event) throws BoardException {
        for (ActionListener listener : getActionListeners()) {
            for (Method method : listener.getClass().getMethods()) {
                AfterAction annotation = method.getAnnotation(AfterAction.class);
                if (annotation == null) continue;
                Class actionClass = annotation.actionClass();
                boolean selfTargeted = annotation.selfTargeted();
                invokeActionListener(event.getAction(), listener, method, actionClass, selfTargeted);
            }
        }
    }

    protected ExecutableAction getExecutableAction(Action action) {
        return executables.get(action.getClass());
    }

    protected void executeActions(List<Action> actions) throws BoardException {
        for (Action action : actions) executeAction(action);
    }

    protected void executeAction(Action action) throws BoardException {
        ActionEvent event = new ActionEvent(this, action);
        if (!validateAction(event)) return;
        fireActionEventBefore(event);
        ExecutableAction executable = getExecutableAction(action);
        if (executable != null) {
            executable.execute(action.getTarget(), action);
        }
        fireActionEventAfter(new ActionEvent(this, action));
    }

    public abstract Player addPlayer(String playerName, int health) throws BoardException;

    protected abstract Card createCardRecord(MetaCard card, Player owner, Player target, int cardPosition);

    protected abstract void moveToNext();
}
