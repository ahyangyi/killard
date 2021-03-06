package com.killard.board.card.skill;

import com.killard.board.card.Action;
import com.killard.board.card.Attack;
import com.killard.board.card.AttackType;
import com.killard.board.card.Board;
import com.killard.board.card.Card;
import com.killard.board.card.Element;
import com.killard.board.card.SkillTarget;
import com.killard.board.card.action.ChangePlayerHealthAction;

import java.util.List;

public class HealingSkill extends BasicSkill<HealingSkill> {

    private final Attack healing;

    public HealingSkill(Element element, int cost, int healing) {
        super(element, cost);
        this.healing = new Attack(getElement(), AttackType.MAGIC, -healing);
    }

    public SkillTarget[] getTargets() {
        return new SkillTarget[]{SkillTarget.self};
    }

    public Attack getHealing() {
        return healing;
    }

    public List<Action> execute(Board board, Card owner, Object... target) {
        List<Action> actions = super.execute(board, owner, target);
        actions.add(new ChangePlayerHealthAction(owner, owner.getOwner(), getHealing()));
        return actions;
    }
}
