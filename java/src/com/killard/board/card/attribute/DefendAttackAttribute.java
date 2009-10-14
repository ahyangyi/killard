package com.killard.board.card.attribute;

import com.killard.board.card.Action;
import com.killard.board.card.Attack;
import com.killard.board.card.AttackType;
import com.killard.board.card.CardInstance;
import com.killard.board.card.action.ChangeCardHealthAction;
import com.killard.board.environment.ActionValidator;

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
public class DefendAttackAttribute extends BasicAttribute {

    private final AttackType attackType;

    private final int attackThreshold;

    public DefendAttackAttribute(AttackType attackType, int attackThreshold) {
        this.attackType = attackType;
        this.attackThreshold = attackThreshold;
    }

    public DefendAttackAttribute(AttackType attackType, int attackThreshold, boolean hidden) {
        super(hidden);
        this.attackType = attackType;
        this.attackThreshold = attackThreshold;
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public int getAttackThreshold() {
        return attackThreshold;
    }

    @ActionValidator(actionClass = ChangeCardHealthAction.class)
    public List<Action> changeAttack(CardInstance owner, ChangeCardHealthAction action) {
        Attack attack = action.getHealthChange();
        if (getAttackType() == attack.getType() &&
                (attack.getValue() < 0 || attack.getValue() == getAttackThreshold())) {
            return null;
        }
        List<Action> actions = new ArrayList<Action>();
        Attack oldAttack = action.getHealthChange();
        if (attackThreshold > 0 &&
                oldAttack.getType() == getAttackType() && oldAttack.getValue() > getAttackThreshold()) {
            Attack newAttack = new Attack(oldAttack.getElementSchool(), oldAttack.getType(), getAttackThreshold());
            actions.add(new ChangeCardHealthAction(action.getSource(), owner, newAttack));
        }
        return actions;
    }
}
