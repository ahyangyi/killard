package com.killard.board.environment.record;

import com.killard.board.card.Action;
import com.killard.board.card.Attack;
import com.killard.board.card.Attribute;
import com.killard.board.card.Card;
import com.killard.board.card.Player;
import com.killard.board.card.Skill;
import com.killard.board.environment.Record;
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
public abstract class AbstractMetaCardRecord implements Card, Record, Comparable<AbstractMetaCardRecord> {

    private final Set<StateListener> stateListeners;

    public AbstractMetaCardRecord() {
        stateListeners = new HashSet<StateListener>();
    }

    public boolean hasSkill() {
        return getSkills().length > 0;
    }

    public boolean hasAttribute() {
        return hasVisibleAttribute() || hasHiddenAttribute();
    }

    public Attribute[] getAttributes() {
        Attribute[] hidden = getHiddenAttributes();
        Attribute[] visible = getVisibleAttributes();
        Attribute[] attributes = new Attribute[hidden.length + visible.length];
        System.arraycopy(hidden, 0, attributes, 0, hidden.length);
        System.arraycopy(visible, 0, attributes, hidden.length, visible.length);
        return attributes;
    }

    public boolean hasHiddenAttribute() {
        return getHiddenAttributes().length > 0;
    }

    public boolean hasVisibleAttribute() {
        return getVisibleAttributes().length > 0;
    }

    public boolean addStateListener(StateListener listener) {
        return stateListeners.add(listener);
    }

    public boolean removeStateListener(StateListener listener) {
        return stateListeners.remove(listener);
    }

    protected abstract boolean addAttribute(Attribute attribute);

    protected abstract boolean removeAttribute(Attribute attribute);

    protected abstract void setHealth(int health);

    protected abstract void setMaxHealth(int maxHealth);

    protected abstract void setAttack(Attack attack);

    protected abstract void setOwner(Player owner);

    protected abstract void setTarget(Player target);

    protected abstract void setPosition(int position);

    protected abstract boolean removeSkill(Skill skill);

    protected abstract void setCasted(boolean casted);

    protected void addAttribute(Attribute attribute, Action action) {
        addAttribute(attribute);
        fireStateChanged(new StateEvent(this, action));
    }

    protected void removeAttribute(Attribute attribute, Action action) {
        removeAttribute(attribute);
        fireStateChanged(new StateEvent(this, action));
    }

    protected void changeHealth(Attack healthChange, Action action) {
        setHealth(getHealth() - healthChange.getValue());
        if (getHealth() < 0) setHealth(0);
        fireStateChanged(new StateEvent(this, action));
    }

    protected void changeMaxHealth(Attack maxHealthChange, Action action) {
        setMaxHealth(getMaxHealth() + maxHealthChange.getValue());
        fireStateChanged(new StateEvent(this, action));
    }

    protected void changeAttack(Attack attackChange, Action action) {
        setAttack(new Attack(getAttack().getElementSchool(), getAttack().getType(),
                getAttack().getValue() + attackChange.getValue()));
        fireStateChanged(new StateEvent(this, action));
    }

    protected void changeSkill(Skill skill, Action action) {
        removeSkill(skill);
        fireStateChanged(new StateEvent(this, action));
    }

    protected void changeCasted(boolean casted, Action action) {
        setCasted(casted);
        fireStateChanged(new StateEvent(this, action));
    }

    protected void fireStateChanged(StateEvent event) {
        for (StateListener listener : stateListeners) listener.stateChanged(event);
    }

    public int compareTo(AbstractMetaCardRecord record) {
        return getPosition() - record.getPosition();
    }
}
