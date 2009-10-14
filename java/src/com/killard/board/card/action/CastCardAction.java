package com.killard.board.card.action;

import com.killard.board.card.Card;
import com.killard.board.card.Player;
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
public final class CastCardAction extends CardAction<Player> {

    private final Skill skill;

    private final Card targetCard;

    public CastCardAction(Player source, Card target, Skill skill, Card targetCard) {
        super(source, target);
        this.skill = skill;
        this.targetCard = targetCard;
    }

    public Skill getSkill() {
        return skill;
    }

    public Card getTargetCard() {
        return targetCard;
    }
}
