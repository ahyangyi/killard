package com.killard.board.card.action;

import com.killard.board.card.Attack;
import com.killard.board.card.AttackType;
import com.killard.board.card.Card;
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
public final class ChangeCardHealthAction extends CardAction<Card> {

    private final Attack healthChange;

    public ChangeCardHealthAction(Card source, Card target, Attack healthChange) {
        super(source, target);
        this.healthChange = healthChange;
    }

    public ChangeCardHealthAction(Card source, Card target,
                                  ElementSchool elementSchool, AttackType attackType, Integer attackValue) {
        super(source, target);
        int value = attackValue == null ? 0 : attackValue;
        this.healthChange = new Attack(elementSchool, attackType, value);
    }

    public Attack getHealthChange() {
        return healthChange;
    }
}
