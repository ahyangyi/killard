package com.killard.card.attribute;

import com.killard.card.Action;
import com.killard.card.Attack;
import com.killard.card.AttackType;
import com.killard.card.CardInstance;
import com.killard.card.action.ChangeCardHealthAction;
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
public class ChangeCardHealthAttribute extends BasicAttribute {

    private final int value;

    public ChangeCardHealthAttribute(int value) {
        this.value = value;
    }

    public ChangeCardHealthAttribute(boolean hidden, int value) {
        super(hidden);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @AfterAction(actionClass = ChangeCardHealthAction.class)
    public List<Action> reactAction(CardInstance owner, ChangeCardHealthAction action) {
        List<Action> actions = new ArrayList<Action>();
        Attack incHealth = new Attack(owner.getElementSchool(), AttackType.MAGIC, value);
        actions.add(new ChangeCardHealthAction(owner, owner, incHealth));
        return actions;
    }
}
