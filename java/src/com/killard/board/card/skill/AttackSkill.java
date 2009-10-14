package com.killard.board.card.skill;

import com.killard.board.card.Action;
import com.killard.board.card.Attack;
import com.killard.board.card.AttackType;
import com.killard.board.card.Card;
import com.killard.board.card.ElementSchool;
import com.killard.board.card.SkillTarget;
import com.killard.board.card.action.ChangeCardHealthAction;
import com.killard.board.card.action.ChangePlayerHealthAction;

import java.util.List;

public class AttackSkill extends BasicSkill<AttackSkill> {

    private final Attack attack;

    public AttackSkill(ElementSchool elementSchool, int cost, int attack) {
        super(elementSchool, cost);
        this.attack = new Attack(getElementSchool(), AttackType.MAGIC, attack);
    }

    public Attack getAttack() {
        return attack;
    }

    public SkillTarget[] getTargets() {
        return new SkillTarget[]{SkillTarget.other};
    }

    public List<Action> execute(Card owner, Object... target) {
        List<Action> actions = super.execute(owner, target);
        if (owner.getTarget() != null && owner.getTarget() != owner.getOwner()) {
            Card opponent = owner.getTarget().getEquippedCard(owner.getPosition());
            if (opponent != null) {
                actions.add(new ChangePlayerHealthAction(owner, owner.getTarget(), getAttack()));
            } else {
                actions.add(new ChangeCardHealthAction(owner, opponent, getAttack()));
            }
        }
        return actions;
    }
}
