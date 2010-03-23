package com.killard.board.card.record;

import com.killard.board.card.Action;
import com.killard.board.card.Attack;
import com.killard.board.card.Card;
import com.killard.board.card.Element;
import com.killard.board.card.MetaCard;
import com.killard.board.card.Player;
import com.killard.board.environment.event.StateEvent;
import com.killard.board.environment.event.StateListener;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public abstract class AbstractPlayerRecord<T extends AbstractPlayerRecord> implements Player<T> {

    private final Set<StateListener> stateListeners;

    protected AbstractPlayerRecord() {
        stateListeners = new HashSet<StateListener>();
    }

    public void addStateListener(StateListener listener) {
        stateListeners.add(listener);
    }

    public void removeStateListener(StateListener listener) {
        stateListeners.remove(listener);
    }

    public int compareTo(T compare) {
        return getTurnCount() - compare.getTurnCount();
    }

    protected abstract boolean addDealtCard(MetaCard metaCard);

    protected abstract boolean removeDealtCard(MetaCard metaCard);

    protected abstract void setCardPlayed(boolean cardPlayed);

    protected abstract boolean addEquippedCard(Card card);

    protected abstract boolean removeEquippedCard(Card card);

    protected abstract void setHealth(int health);

    protected abstract void setElementResource(Element element, int resource);

    protected abstract void setProperty(String name, Object data);

    protected abstract void setAlive(boolean alive);

    protected abstract void setCalled(boolean called);

    protected abstract void setRoleVisible(boolean visible);

    protected abstract void setWinner(boolean winner);

    protected abstract void setLoser(boolean loser);

    protected abstract void setTurnCount(int turnCount);

    protected void beginTurn() {
        for (Card instance : getEquippedCards()) {
            AbstractCardRecord record = (AbstractCardRecord) instance;
            record.setCasted(false);
        }
        setCardPlayed(false);
    }

    protected void endTurn() {
        setTurnCount(getTurnCount() + 1);
    }

    protected boolean addDealtCard(MetaCard metaCard, Action action) {
        boolean result = addDealtCard(metaCard);
        fireStateChanged(new StateEvent(this, action));
        return result;
    }

    protected boolean removeDealtCard(MetaCard metaCard, Action action) {
        boolean result = removeDealtCard(metaCard);
        fireStateChanged(new StateEvent(this, action));
        return result;
    }

    protected void changeHealth(Attack healthChange, Action action) {
        setHealth(getHealth() - healthChange.getValue());
        if (getHealth() < 0) setHealth(0);
        fireStateChanged(new StateEvent(this, action));
    }

    protected void changeElementForSchool(Element element, int elementChange, Action action) {
        setElementResource(element, getElementResource(element) + elementChange);
        fireStateChanged(new StateEvent(this, action));
    }

    protected void setProperty(String name, Object data, Action action) {
        setProperty(name, data);
        fireStateChanged(new StateEvent(this, action));
    }

    protected boolean addEquippedCard(Card card, Action action) {
        boolean result = addEquippedCard(card);
        fireStateChanged(new StateEvent(this, action));
        return result;
    }

    protected boolean removeEquippedCard(Card card, Action action) {
        boolean result = removeEquippedCard(card);
        fireStateChanged(new StateEvent(this, action));
        return result;
    }

    protected void beginTurn(Action action) {
        beginTurn();
        fireStateChanged(new StateEvent(this, action));
    }

    protected void endTurn(Action action) {
        endTurn();
        fireStateChanged(new StateEvent(this, action));
    }

    protected void setAlive(boolean alive, Action action) {
        setAlive(alive);
        fireStateChanged(new StateEvent(this, action));
    }

    protected void setCalled(boolean called, Action action) {
        setCalled(called);
        fireStateChanged(new StateEvent(this, action));
    }

    protected void setRoleVisible(boolean visible, Action action) {
        setRoleVisible(visible);
        fireStateChanged(new StateEvent(this, action));
    }

    protected void win(Action action) {
        setWinner(true);
        fireStateChanged(new StateEvent(this, action));
    }

    protected void lose(Action action) {
        setLoser(true);
        fireStateChanged(new StateEvent(this, action));
    }

    protected void fireStateChanged(StateEvent event) {
        for (StateListener listener : stateListeners) listener.stateChanged(event);
    }

}
