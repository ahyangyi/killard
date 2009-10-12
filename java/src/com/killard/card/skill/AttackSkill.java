package com.killard.card.skill;

import com.killard.card.Action;
import com.killard.card.Attack;
import com.killard.card.AttackType;
import com.killard.card.CardInstance;
import com.killard.card.ElementSchool;
import com.killard.card.action.ChangeCardHealthAction;
import com.killard.card.action.ChangePlayerHealthAction;

import java.util.List;

public class AttackSkill extends BasicSkill {

    private final Attack attack;

    public AttackSkill(ElementSchool elementSchool, int cost, int attack) {
        super(elementSchool, cost);
        this.attack = new Attack(getElementSchool(), AttackType.MAGIC, attack);
    }

    public Attack getAttack() {
        return attack;
    }

    public List<Action> execute(CardInstance owner, CardInstance target) {
        List<Action> actions = super.execute(owner, target);
        if (owner.getTarget() != null && owner.getTarget() != owner.getOwner()) {
            CardInstance opponent = owner.getTarget().getEquippedCard(owner.getPosition());
            if (opponent != null) {
                actions.add(new ChangePlayerHealthAction(owner, owner.getTarget(), getAttack()));
            } else {
                actions.add(new ChangeCardHealthAction(owner, opponent, getAttack()));
            }
        }
        return actions;
    }
}
