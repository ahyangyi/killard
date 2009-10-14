package com.killard.board.card.action;

import com.killard.board.card.Card;
import com.killard.board.card.Skill;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class RemoveSkillAction extends CardAction<Card> {

    private final Skill skill;

    public RemoveSkillAction(Card source, Card target, Skill skill) {
        super(source, target);
        this.skill = skill;
    }

    public Skill getSkill() {
        return skill;
    }
}
