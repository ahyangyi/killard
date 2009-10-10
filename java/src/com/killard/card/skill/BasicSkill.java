package com.killard.card.skill;

import com.killard.card.Action;
import com.killard.card.CardInstance;
import com.killard.card.ElementSchool;
import com.killard.card.Skill;
import com.killard.card.action.ChangePlayerElementAction;

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

    public String getId() {
        return getClass().getSimpleName();
    }

    public ElementSchool getElementSchool() {
        return elementSchool;
    }

    public int getCost() {
        return cost;
    }

    public List<Action> execute(CardInstance owner, CardInstance target) {
        List<Action> actions = new LinkedList<Action>();
        if (getCost() != 0) actions.add(new ChangePlayerElementAction(owner, owner.getOwner(), getElementSchool(), -getCost()));
        return actions;
    }

}
