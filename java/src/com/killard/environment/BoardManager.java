package com.killard.environment;

import com.killard.card.Action;
import com.killard.card.Card;
import com.killard.card.CardInstance;
import com.killard.card.Player;
import com.killard.card.Board;
import com.killard.card.Skill;
import com.killard.card.action.CastCardAction;
import com.killard.card.action.EndTurnAction;
import com.killard.card.action.EquipCardAction;
import com.killard.card.action.EndPlayerCallAction;
import com.killard.environment.event.ActionEvent;
import com.killard.environment.event.ActionListener;
import com.killard.environment.event.StateEvent;
import com.killard.environment.event.StateListener;
import com.killard.environment.record.ExecutableActionUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * This class defines manager that execute all operations on the board.
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public abstract class BoardManager implements Board, StateListener {

    private final Map<ActionListener, Object> listeners = new HashMap<ActionListener, Object>();

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
        Card card = owner.getDealtCard(cardIndex);
        Player target = getPlayer(targetPlayerPosition);
        CardInstance instance = createCardRecord(card, owner, target, cardPosition);
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
        CardInstance card = player.getEquippedCard(cardPosition);

        Player targetPlayer = getPlayer(targetPlayerPosition);
        CardInstance targetCard = targetPlayer.getEquippedCard(targetCardPosition);
        if (targetCard == null) targetCard = card;

        executeAction(new CastCardAction(player, card, card.getSkills()[skillIndex], targetCard));
    }

    public void endTurn() throws BoardException {
        executeAction(new EndTurnAction(this, getCurrentPlayer()));
    }

    public void endCall() throws BoardException {
        executeAction(new EndPlayerCallAction(getCurrentPlayer(), getCurrentPlayer()));
    }

    public void addActionListener(ActionListener listener, Object host) {
        listeners.put(listener, host);
    }

    public ActionListener[] getActionListeners() {
        return listeners.keySet().toArray(new ActionListener[listeners.size()]);
    }

    public void removeActionListener(ActionListener listener) {
        listeners.remove(listener);
    }

    public void stateChanged(StateEvent event) {
    }

    protected Object getActionListenerHost(ActionListener listener) {
        return listeners.get(listener);
    }

    protected boolean invokeActionListener(Action action, ActionListener listener,
                                           Method method, Class actionClass, boolean selfTargeted)
            throws BoardException {
        if (!actionClass.isAssignableFrom(action.getClass())) return false;

        Object host = getActionListenerHost(listener);
        if (selfTargeted && action.getTarget() != host) return false;

        try {
            Object result = method.invoke(listener, host, action);
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
                Object host = getActionListenerHost(listener);
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

    protected abstract CardInstance createCardRecord(Card card, Player owner, Player target, int cardPosition);

    protected abstract void moveToNext();
}
