package com.killard.board.card.attribute;

import com.killard.board.card.Action;
import com.killard.board.card.Attribute;
import com.killard.board.card.Board;
import com.killard.board.card.Card;
import com.killard.board.card.Element;
import com.killard.board.environment.ActionValidator;
import com.killard.board.environment.AfterAction;
import com.killard.board.environment.BeforeAction;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
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
public class CompositeAttribute extends BasicAttribute<CompositeAttribute> {

    private List<Attribute> attributes = new ArrayList<Attribute>();

    public CompositeAttribute(Element element) {
        super(element);
    }

    public CompositeAttribute(Element element, List<Attribute> attributes) {
        super(element);
        this.attributes.addAll(attributes);
    }

    public CompositeAttribute(Element element, Attribute[] attributes) {
        super(element);
        this.attributes.addAll(Arrays.asList(attributes));
    }

    public CompositeAttribute(Element element, boolean visible, List<Attribute> attributes) {
        super(element, visible);
        this.attributes.addAll(attributes);
    }

    public CompositeAttribute(Element element, boolean visible, Attribute[] attributes) {
        super(element, visible);
        this.attributes.addAll(Arrays.asList(attributes));
    }

    public List<Attribute> getAttributes() {
        return new ArrayList<Attribute>(attributes);
    }

    @ActionValidator(actionClass = Action.class, selfTargeted = false)
    protected List<Action> validateAction(Board board, Card card, Action action) {
        List<Action> actions = new LinkedList<Action>();
        boolean valid = true;
        for (Attribute attribute : attributes) {
            for (Method method : attribute.getClass().getMethods()) {
                ActionValidator annotation = method.getAnnotation(ActionValidator.class);
                if (annotation == null) continue;
                Class actionClass = annotation.actionClass();
                boolean selfTargeted = annotation.selfTargeted();
                List<Action> result = invokeAttribute(action, attribute, method, actionClass, selfTargeted, card);
                if (result != null) {
                    actions.addAll(result);
                    if (result.isEmpty()) valid = false;
                }
            }
        }
        if (valid && actions.isEmpty()) return null;
        return actions;
    }

    @BeforeAction(actionClass = Action.class, selfTargeted = false)
    public List<Action> beforeAction(Board board, Card card, Action action) {
        List<Action> actions = new LinkedList<Action>();
        for (Attribute attribute : attributes) {
            for (Method method : attribute.getClass().getMethods()) {
                BeforeAction annotation = method.getAnnotation(BeforeAction.class);
                if (annotation == null) continue;
                Class actionClass = annotation.actionClass();
                boolean selfTargeted = annotation.selfTargeted();
                List<Action> result = invokeAttribute(action, attribute, method, actionClass, selfTargeted, card);
                if (result != null) actions.addAll(result);
            }
        }
        return actions;
    }

    @AfterAction(actionClass = Action.class, selfTargeted = false)
    public List<Action> afterAction(Board board, Card card, Action action) {
        List<Action> actions = new LinkedList<Action>();
        for (Attribute attribute : attributes) {
            for (Method method : attribute.getClass().getMethods()) {
                AfterAction annotation = method.getAnnotation(AfterAction.class);
                if (annotation == null) continue;
                Class actionClass = annotation.actionClass();
                boolean selfTargeted = annotation.selfTargeted();
                List<Action> result = invokeAttribute(action, attribute, method, actionClass, selfTargeted, card);
                if (result != null) actions.addAll(result);
            }
        }
        return actions;
    }

    protected List<Action> invokeAttribute(Action action, Attribute attribute,
                                           Method method, Class actionClass, boolean selfTargeted,
                                           Card host) {
        if (!actionClass.isAssignableFrom(action.getClass())) return null;

        if (selfTargeted && action.getTarget() != host) return null;

        try {
            Object result = method.invoke(attribute, host, action);
            if (result == null) return null;
            if (result.equals(Boolean.TRUE)) return null;
            if (result instanceof List) {
                return (List<Action>) result;
            } else if (result instanceof Action) {
                List<Action> actions = new LinkedList<Action>();
                actions.add((Action) result);
                return actions;
            } else return new ArrayList<Action>();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int compareTo(CompositeAttribute compare) {
        return getName().compareTo(compare.getName());
    }
}
