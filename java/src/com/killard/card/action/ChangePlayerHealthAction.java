package com.killard.card.action;

import com.killard.card.Attack;
import com.killard.card.AttackType;
import com.killard.card.CardInstance;
import com.killard.card.Player;
import com.killard.card.ElementSchool;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public final class ChangePlayerHealthAction extends PlayerAction<CardInstance> {

    private final Attack healthChange;

    public ChangePlayerHealthAction(CardInstance source, Player target, Attack healthChange) {
        super(source, target);
        this.healthChange = healthChange;
    }

    public ChangePlayerHealthAction(CardInstance source, Player target,
                                    ElementSchool elementSchool, AttackType attackType, Integer attackValue) {
        super(source, target);
        this.healthChange = new Attack(elementSchool, attackType, attackValue);
    }

    public Attack getHealthChange() {
        return healthChange;
    }
}
