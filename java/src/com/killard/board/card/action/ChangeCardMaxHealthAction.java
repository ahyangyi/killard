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
public class ChangeCardMaxHealthAction extends CardAction<Card> {

    private final Attack maxHealthChange;

    public ChangeCardMaxHealthAction(Card source, Card target, Attack maxHealthChange) {
        super(source, target);
        this.maxHealthChange = maxHealthChange;
    }

    public ChangeCardMaxHealthAction(Card source, Card target,
                                     ElementSchool elementSchool, AttackType attackType, Integer attackValue) {
        super(source, target);
        this.maxHealthChange = new Attack(elementSchool, attackType, attackValue);
    }

    public Attack getMaxHealthChange() {
        return maxHealthChange;
    }

}
