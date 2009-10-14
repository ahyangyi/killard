package com.killard.board.parser;

import com.killard.board.card.Action;

import java.lang.reflect.Constructor;
import java.util.List;

public interface Context {

    public FlowControl getFlowControl();

    public Object peek();

    public Object pop();

    public void push(Object obj);

    public Object getVariable(String name);

    public void addVariable(String name, Object value);

    public void setVariable(String name, Object value);

    public Constructor getActionConstructor(String name, Class... types) throws NoSuchMethodException;

    public boolean isRejected();

    public void setRejected(boolean rejected);

    public void addAction(Action action);

    public List<Action> getActions();
}
