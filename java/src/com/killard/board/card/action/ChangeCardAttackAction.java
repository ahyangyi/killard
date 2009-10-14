package com.killard.board.card.action;

import com.killard.board.card.Attack;
import com.killard.board.card.Card;
import com.killard.board.card.ElementSchool;
import com.killard.board.card.AttackType;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public final class ChangeCardAttackAction extends CardAction<Card> {

    private final Attack attack;

    public ChangeCardAttackAction(Card source, Card target, Attack value) {
        super(source, target);
        this.attack = value;
    }

    public ChangeCardAttackAction(Card source, Card target,
                                  ElementSchool elementSchool, AttackType attackType, Integer attackValue) {
        super(source, target);
        this.attack = new Attack(elementSchool, attackType, attackValue);
    }

    public Attack getAttack() {
        return attack;
    }
}
