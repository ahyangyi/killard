package com.killard.board.card.action;

import com.killard.board.card.Attack;
import com.killard.board.card.AttackType;
import com.killard.board.card.Card;
import com.killard.board.card.Element;

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
                                  Element element, AttackType attackType, Integer attackValue) {
        super(source, target);
        int value = attackValue == null ? 0 : attackValue;
        this.attack = new Attack(element, attackType, value);
    }

    public Attack getAttack() {
        return attack;
    }
}
