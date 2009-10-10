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
public class ChangeCardMaxHealthAction extends CardAction<CardInstance> {

    private final Attack maxHealthChange;

    public ChangeCardMaxHealthAction(CardInstance source, CardInstance target, Attack maxHealthChange) {
        super(source, target);
        this.maxHealthChange = maxHealthChange;
    }

    public ChangeCardMaxHealthAction(CardInstance source, CardInstance target,
                                     ElementSchool elementSchool, AttackType attackType, Integer attackValue) {
        super(source, target);
        this.maxHealthChange = new Attack(elementSchool, attackType, attackValue);
    }

    public Attack getMaxHealthChange() {
        return maxHealthChange;
    }

}
