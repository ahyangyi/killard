package com.killard.parser;

import com.killard.card.Action;

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
public class LocalContext implements Context {

    private final Context parent;

    private final List<Object> stack = new LinkedList<Object>();

    private final Map<String, Object> variables = new HashMap<String, Object>();

    public LocalContext(Context parent) {
        this.parent = parent;
    }

    public void addAction(Action action) {
        parent.addAction(action);
    }

    public boolean isRejected() {
        return parent.isRejected();
    }

    public void setRejected(boolean reject) {
        parent.setRejected(reject);
    }

    public List<Action> getActions() {
        return parent.getActions();
    }

    public FlowControl getFlowControl() {
        return parent.getFlowControl();
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
        if (variables.containsKey(name)) return variables.get(name);
        else return parent.getVariable(name);
    }

    public void addVariable(String name, Object value) {
        variables.put(name, value);
    }

    public void setVariable(String name, Object value) {
        if (variables.containsKey(name)) variables.put(name, value);
        else parent.setVariable(name, value);
    }

    public Constructor getActionConstructor(String name, Class... types) throws NoSuchMethodException {
        return parent.getActionConstructor(name, types);
    }
}
