package com.killard.board.card.skill;

import com.killard.board.card.Action;
import com.killard.board.card.Board;
import com.killard.board.card.Card;
import com.killard.board.card.Element;
import com.killard.board.card.Skill;
import com.killard.board.card.action.ChangePlayerElementAction;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public abstract class BasicSkill<T extends BasicSkill> implements Skill<T> {

    private final Element element;

    private final int cost;

    private final Map<String, Object> properties = new HashMap<String, Object>();

    protected BasicSkill(Element element) {
        this(element, 0);
    }

    protected BasicSkill(Element element, int cost) {
        this.element = element;
        this.cost = cost;
    }

    public String getName() {
        return getClass().getSimpleName();
    }

    public Element getElement() {
        return element;
    }

    public int getCost() {
        return cost;
    }

    public List<Action> execute(Board board, Card owner, Object... target) {
        List<Action> actions = new LinkedList<Action>();
        if (getCost() != 0) actions.add(new ChangePlayerElementAction(owner, owner.getOwner(), getElement(), -getCost()));
        return actions;
    }

    public Object getProperty(String name) {
        return properties.get(name);
    }

    public int compareTo(T compare) {
        return getCost() - compare.getCost();
    }
}
