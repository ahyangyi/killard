package com.killard.card.skill;

import com.killard.card.Action;
import com.killard.card.Attack;
import com.killard.card.AttackType;
import com.killard.card.CardInstance;
import com.killard.card.ElementSchool;
import com.killard.card.action.ChangePlayerHealthAction;

import java.util.List;

public class HealingSkill extends BasicSkill {

    private final Attack healing;

    public HealingSkill(ElementSchool elementSchool, int cost, int healing) {
        super(elementSchool, cost);
        this.healing = new Attack(getElementSchool(), AttackType.MAGIC, -healing);
    }

    public Attack getHealing() {
        return healing;
    }

    public List<Action> execute(CardInstance owner, CardInstance target) {
        List<Action> actions = super.execute(owner, target);
        actions.add(new ChangePlayerHealthAction(owner, owner.getOwner(), getHealing()));
        return actions;
    }
}
