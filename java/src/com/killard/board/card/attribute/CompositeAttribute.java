package com.killard.board.card.attribute;

import com.killard.board.card.Action;
import com.killard.board.card.Attribute;
import com.killard.board.card.Card;
import com.killard.board.card.ElementSchool;
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
public class CompositeAttribute extends BasicAttribute {

    private List<Attribute> attributes = new ArrayList<Attribute>();

    public CompositeAttribute(ElementSchool elementSchool) {
        super(elementSchool);
    }

    public CompositeAttribute(ElementSchool elementSchool, List<Attribute> attributes) {
        super(elementSchool);
        this.attributes.addAll(attributes);
    }

    public CompositeAttribute(ElementSchool elementSchool, Attribute[] attributes) {
        super(elementSchool);
        this.attributes.addAll(Arrays.asList(attributes));
    }

    public CompositeAttribute(ElementSchool elementSchool, boolean visible, List<Attribute> attributes) {
        super(elementSchool, visible);
        this.attributes.addAll(attributes);
    }

    public CompositeAttribute(ElementSchool elementSchool, boolean visible, Attribute[] attributes) {
        super(elementSchool, visible);
        this.attributes.addAll(Arrays.asList(attributes));
    }

    public List<Attribute> getAttributes() {
        return new ArrayList<Attribute>(attributes);
    }

    @ActionValidator(actionClass = Action.class, selfTargeted = false)
    protected List<Action> validateAction(Card card, Action action) {
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
    public List<Action> beforeAction(Card card, Action action) {
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
    public List<Action> afterAction(Card card, Action action) {
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
}
