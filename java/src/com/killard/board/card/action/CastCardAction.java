package com.killard.board.card.action;

import com.killard.board.card.CardInstance;
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

    private final CardInstance targetCard;

    public CastCardAction(Player source, CardInstance target, Skill skill, CardInstance targetCard) {
        super(source, target);
        this.skill = skill;
        this.targetCard = targetCard;
    }

    public Skill getSkill() {
        return skill;
    }

    public CardInstance getTargetCard() {
        return targetCard;
    }
}
