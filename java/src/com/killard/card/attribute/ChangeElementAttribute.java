package com.killard.card.attribute;

import com.killard.card.Action;
import com.killard.card.CardInstance;
import com.killard.card.ElementSchool;
import com.killard.card.action.ChangePlayerElementAction;
import com.killard.card.action.NextTurnAction;
import com.killard.environment.AfterAction;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class ChangeElementAttribute extends BasicAttribute {

    private final ElementSchool elementSchool;

    private final int value;

    public ChangeElementAttribute(ElementSchool elementSchool, int value) {
        this.elementSchool = elementSchool;
        this.value = value;
    }

    public ChangeElementAttribute(boolean hidden, ElementSchool elementSchool,
                                  int value) {
        super(hidden);
        this.elementSchool = elementSchool;
        this.value = value;
    }

    public ElementSchool getElementSchool() {
        return elementSchool;
    }

    public int getValue() {
        return value;
    }

    @AfterAction(actionClass = NextTurnAction.class, selfTargeted = false)
    public List<Action> reactAction(CardInstance owner, NextTurnAction action) {
        List<Action> actions = new ArrayList<Action>();
        if (owner.getOwner() == action.getTarget())
            actions.add(new ChangePlayerElementAction(owner, owner.getOwner(), elementSchool, value));
        return actions;
    }
}
