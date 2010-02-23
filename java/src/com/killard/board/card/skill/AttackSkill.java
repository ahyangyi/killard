package com.killard.board.card.skill;

import com.killard.board.card.Action;
import com.killard.board.card.Attack;
import com.killard.board.card.AttackType;
import com.killard.board.card.Board;
import com.killard.board.card.Card;
import com.killard.board.card.ElementSchool;
import com.killard.board.card.SkillTarget;

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

    public List<Action> execute(Board board, Card owner, Object... target) {
        List<Action> actions = super.execute(board, owner, target);
        return actions;
    }
}
