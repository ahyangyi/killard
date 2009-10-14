package com.killard.board.card.skill;

import com.killard.board.card.Action;
import com.killard.board.card.CardInstance;
import com.killard.board.card.ElementSchool;
import com.killard.board.card.Skill;
import com.killard.board.card.action.ChangePlayerElementAction;

import java.util.LinkedList;
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
public abstract class BasicSkill implements Skill {

    private final ElementSchool elementSchool;

    private final int cost;

    protected BasicSkill(ElementSchool elementSchool) {
        this(elementSchool, 0);
    }

    protected BasicSkill(ElementSchool elementSchool, int cost) {
        this.elementSchool = elementSchool;
        this.cost = cost;
    }

    public String getName() {
        return getClass().getSimpleName();
    }

    public ElementSchool getElementSchool() {
        return elementSchool;
    }

    public int getCost() {
        return cost;
    }

    public List<Action> execute(CardInstance owner, Object... target) {
        List<Action> actions = new LinkedList<Action>();
        if (getCost() != 0) actions.add(new ChangePlayerElementAction(owner, owner.getOwner(), getElementSchool(), -getCost()));
        return actions;
    }

}
