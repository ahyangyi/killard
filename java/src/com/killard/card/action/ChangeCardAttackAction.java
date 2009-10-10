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
public final class ChangeCardAttackAction extends CardAction<CardInstance> {

    private final Attack attack;

    public ChangeCardAttackAction(CardInstance source, CardInstance target, Attack value) {
        super(source, target);
        this.attack = value;
    }

    public ChangeCardAttackAction(CardInstance source, CardInstance target,
                                  ElementSchool elementSchool, AttackType attackType, Integer attackValue) {
        super(source, target);
        this.attack = new Attack(elementSchool, attackType, attackValue);
    }

    public Attack getAttack() {
        return attack;
    }
}
