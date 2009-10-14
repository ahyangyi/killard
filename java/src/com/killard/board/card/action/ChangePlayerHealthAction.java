package com.killard.board.card.action;

import com.killard.board.card.Attack;
import com.killard.board.card.AttackType;
import com.killard.board.card.CardInstance;
import com.killard.board.card.Player;
import com.killard.board.card.ElementSchool;

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
