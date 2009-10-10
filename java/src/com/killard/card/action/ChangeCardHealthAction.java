package com.killard.card.action;

import com.killard.card.Attack;
import com.killard.card.CardInstance;
import com.killard.card.ElementSchool;
import com.killard.card.AttackType;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public final class ChangeCardHealthAction extends CardAction<CardInstance> {

    private final Attack healthChange;

    public ChangeCardHealthAction(CardInstance source, CardInstance target, Attack healthChange) {
        super(source, target);
        this.healthChange = healthChange;
    }

    public ChangeCardHealthAction(CardInstance source, CardInstance target,
                                  ElementSchool elementSchool, AttackType attackType, Integer attackValue) {
        super(source, target);
        this.healthChange = new Attack(elementSchool, attackType, attackValue);
    }

    public Attack getHealthChange() {
        return healthChange;
    }
}
