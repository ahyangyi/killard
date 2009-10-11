package com.killard.environment.record;

import com.killard.card.Action;
import com.killard.card.Attack;
import com.killard.card.CardInstance;
import com.killard.card.ElementSchool;
import com.killard.card.Player;
import com.killard.environment.Record;
import com.killard.environment.event.StateEvent;
import com.killard.environment.event.StateListener;

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
public abstract class AbstractPlayerRecord implements Player, Record {

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

    protected abstract void setCardPlayed(boolean cardPlayed);

    protected abstract boolean addLivingCard(CardInstance card);

    protected abstract boolean removeLivingCard(CardInstance card);

    protected abstract void setHealth(int health);

    protected abstract void setElementAmount(ElementSchool elementSchool, int amount);

    protected abstract void setTurnCount(int turnCount);

    protected void nextTurn() {
        for (CardInstance instance : getLivingCards()) {
            AbstractCardRecord record = (AbstractCardRecord) instance;
            record.setCasted(false);
        }
        setCardPlayed(false);
        setTurnCount(getTurnCount() + 1);
    }

    protected void changeHealth(Attack healthChange, Action action) {
        setHealth(getHealth() - healthChange.getValue());
        if (getHealth() < 0) setHealth(0);
        fireStateChanged(new StateEvent(this, action));
    }

    protected void changeElementForSchool(ElementSchool elementSchool, int elementChange, Action action) {
        setElementAmount(elementSchool, getElementAmount(elementSchool) + elementChange);
        fireStateChanged(new StateEvent(this, action));
    }

    protected void addLivingCard(CardInstance card, Action action) {
        addLivingCard(card);
        fireStateChanged(new StateEvent(this, action));
    }

    protected void removeLivingCard(CardInstance card, Action action) {
        removeLivingCard(card);
        fireStateChanged(new StateEvent(this, action));
    }

    protected void nextTurn(Action action) {
        nextTurn();
        fireStateChanged(new StateEvent(this, action));
    }

    protected void fireStateChanged(StateEvent event) {
        for (StateListener listener : stateListeners) listener.stateChanged(event);
    }

}
