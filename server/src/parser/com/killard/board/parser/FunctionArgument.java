package com.killard.board.parser;

public class FunctionArgument implements Node {

    private static final long serialVersionUID = -5852378245941660238L;

    public FunctionArgument(String text) {
    }

    /**
     * <p>
     * Creates a new function argument, wrapping the given value.
     * </p>
     *
     * @param value
     *            the expression that is the value of this argument
     * @throws IllegalArgumentException
     *             if value is <code>null</code>
     */
    public FunctionArgument(Expression value) {
    }

    /**
     * <p>
     * Returns the expression that is the value of this argument.
     * </p>
     *
     * @return the expression that is the value of this argument
     */
    public Expression getExpression() {
        return null;
    }

    public Object execute(Context context) throws ExecutionException {
        return getExpression().execute(context);
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("[Not implemented yet!]");
        return buf.toString();
    }
}
