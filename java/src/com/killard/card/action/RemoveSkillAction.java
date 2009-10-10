package com.killard.card.action;

import com.killard.card.CardInstance;
import com.killard.card.Skill;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class RemoveSkillAction extends CardAction<CardInstance> {

    private final Skill skill;

    public RemoveSkillAction(CardInstance source, CardInstance target, Skill skill) {
        super(source, target);
        this.skill = skill;
    }

    public Skill getSkill() {
        return skill;
    }
}
