package com.killard.board.card.action;

import com.killard.board.card.Card;
import com.killard.board.card.Player;
import com.killard.board.card.Skill;

import java.util.Arrays;

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

    private final Object[] targets;

    public CastCardAction(Player source, Card target, Skill skill, Object[] targets) {
        super(source, target);
        this.skill = skill;
        this.targets = Arrays.copyOf(targets, targets.length);
    }

    public Skill getSkill() {
        return skill;
    }

    public Object[] getTargets() {
        return targets;
    }
}
