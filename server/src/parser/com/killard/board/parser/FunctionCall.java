package com.killard.board.parser;

import com.killard.board.card.Action;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class FunctionCall extends GlobalFunctionCall {

    private static final long serialVersionUID = -4013987710712368443L;

    public FunctionCall(String text, Sequence arguments) {
        super(text, arguments);
    }

    public boolean validateMethod() throws ExecutionException {
        if (getText().startsWith("set")) return false;
        if (getText().startsWith("change")) return false;
        if (getText().startsWith("add")) return false;
        if (getText().startsWith("remove")) return false;
        return true;
    }

    public Object execute(Context context) throws ExecutionException {
        Object owner = context.pop();
        if (owner == null) throw new ExecutionException("Can not invoke function " + getText() + " from null.");
        if (!validateMethod()) throw new ExecutionException("Can not invoke setter function " + getText() + ".");

        Expression[] argNodes = getArguments();
        Class[] types = new Class[argNodes.length];
        Object[] args = new Object[argNodes.length];
        for (int i = 0; i < argNodes.length; i++) {
            args[i] = argNodes[i].execute(context);
            if (args[i] == null) {
                throw new ExecutionException(
                        "Can not invoke function with null argument at this version : " + argNodes[i].getText());
            }
            types[i] = args[i].getClass();
        }

        try {
            Method method = owner.getClass().getMethod(getText(), types);
            return handleResult(method.invoke(owner, args), context);
        } catch (NoSuchMethodException e) {
            for (Method method : owner.getClass().getMethods()) {
                if (!getText().equals(method.getName())) continue;

                Class[] argTypes = method.getParameterTypes();
                if (argTypes.length != types.length) continue;

                boolean match = true;
                for (int i = 0; i < types.length; i++) {
                    if (!argTypes[i].isAssignableFrom(types[i])) match = false;
                }
                if (match) try {
                    return handleResult(method.invoke(owner, args), context);
                } catch (Exception e1) {
                    throw new ExecutionException("Can not invoke function " + getText(), e1);
                }
            }
        } catch (Exception e) {
            throw new ExecutionException("Can not invoke function " + getText(), e);
        }
        throw new ExecutionException(
                "Can not invoke function " + getText() + " with args " + Arrays.asList(args) + " on " + owner);
    }

    private Object handleResult(Object result, Context context) {
        if (result instanceof List) {
            for (Object value : (List) result) {
                if (value instanceof Action) {
                    context.addAction((Action) value);
                }
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
