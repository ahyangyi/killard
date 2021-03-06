package com.killard.board.card.skill;

import com.killard.board.card.Action;
import com.killard.board.card.Board;
import com.killard.board.card.Card;
import com.killard.board.card.Element;
import com.killard.board.card.Skill;
import com.killard.board.card.SkillTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class CompositeSkill extends BasicSkill<CompositeSkill> {

    private List<Skill> skills = new ArrayList<Skill>();

    protected CompositeSkill(Element element, List<Skill> skills) {
        this(element, 0, skills);
    }

    protected CompositeSkill(Element element, int cost, List<Skill> skills) {
        super(element, cost);
        this.skills.addAll(skills);
    }

    public List<Skill> getSkills() {
        return new ArrayList<Skill>(skills);
    }

    public List<Action> execute(Board board, Card owner, Object... target) {
        List<Action> actions = super.execute(board, owner, target);
        for (Skill skill : skills) actions.addAll(skill.execute(board, owner, target));
        return actions;
    }

    public SkillTarget[] getTargets() {
        return new SkillTarget[]{SkillTarget.all};
    }
}
