package com.killard.board.parser;

import com.killard.board.card.Action;
import com.killard.board.card.AttackType;
import com.killard.board.card.record.ExecutableActions;

import java.lang.reflect.Constructor;
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
public class GlobalContext implements Context {

    private final FlowControl flowControl = new FlowControl();

    private boolean rejected = false;

    private final List<Action> actions = new LinkedList<Action>();

    private final List<Object> stack = new LinkedList<Object>();

    private final Map<String, Object> variables = new HashMap<String, Object>();

    public GlobalContext(Object owner) {
        variables.put("owner", owner);
        variables.put("MAGIC", AttackType.MAGIC);
        variables.put("PHYSICAL", AttackType.PHYSICAL);
    }

    public GlobalContext(Object owner, Action action) {
        this(owner);
        variables.put("action", action);
    }

    public FlowControl getFlowControl() {
        return flowControl;
    }

    public void addAction(Action action) {
        actions.add(action);
    }

    public boolean isRejected() {
        return rejected;
    }

    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }

    public List<Action> getActions() {
        return actions;
    }

    public Object peek() {
        if (stack.isEmpty()) return null;
        return stack.get(0);
    }

    public Object pop() {
        if (stack.isEmpty()) return null;
        return stack.remove(0);
    }

    public void push(Object obj) {
        stack.add(0, obj);
    }

    public Object getVariable(String name) {
        return variables.get(name);
    }

    public void addVariable(String name, Object value) {
        variables.put(name, value);
    }

    public void setVariable(String name, Object value) {
        variables.put(name, value);
    }

    public Constructor getActionConstructor(String name, Class... types) throws NoSuchMethodException {
        for (Class c : ExecutableActions.instance.getRegisterActions()) {
            if (c.getSimpleName().equalsIgnoreCase(name)) {
                for (Constructor ctor : c.getConstructors()) {
                    Class[] argTypes = ctor.getParameterTypes();
                    if (argTypes.length != types.length) continue;
                    boolean match = true;
                    for (int i = 0; i < types.length; i++) {
                        if (!argTypes[i].isAssignableFrom(types[i])) match = false;
                    }
                    if (match) return ctor;
                }
            }
        }
        return null;
    }

}
