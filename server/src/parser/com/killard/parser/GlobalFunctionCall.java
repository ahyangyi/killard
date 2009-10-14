package com.killard.parser;

import com.killard.card.Action;

import java.lang.reflect.Constructor;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class GlobalFunctionCall extends Call {

    private final Sequence arguments;

    public GlobalFunctionCall(String text, Sequence arguments) {
        super(text);
        this.arguments = arguments;
    }

    /**
     * <p>
     * Returns the arguments of the function call.
     * </p>
     *
     * @return the arguments of the function call
     */
    public Expression[] getArguments() {
        if (arguments != null) {
            Node[] args = arguments.getChildren();
            Expression[] result = new Expression[args.length];
            System.arraycopy(arguments.getChildren(), 0, result, 0, args.length);
            return result;
        }
        return new Expression[0];
    }

    public Object execute(Context context) throws ExecutionException {

        if (getText().equals("RejectAction")) {
            context.setRejected(true);
            return false;
        }

        if (getText().equals("random")) {
            return (int) (101 * Math.random());
        }

        Expression[] argNodes = getArguments();
        Class[] argTypes = new Class[argNodes.length];
        Object[] args = new Object[argNodes.length];
        for (int i = 0; i < argNodes.length; i++) {
            args[i] = argNodes[i].execute(context);
            if (args[i] == null) {
                throw new ExecutionException(
                        "Can not invoke function with null argument at this version : " + argNodes[i].getText()
                                + " " + argNodes.getClass().getSimpleName());
            }
            argTypes[i] = args[i].getClass();
        }
        try {
            Constructor ctor = context.getActionConstructor(getText(), argTypes);
            Action action = (Action) ctor.newInstance(args);
            context.addAction(action);
            return action;
        } catch (Exception e) {
            throw new ExecutionException("Can not invoke method " + getText(), e);
        }
    }

}
