package com.killard.parser;

public class UnaryPositiveExpression extends UnaryExpression {

    /**
     * <p>
     * Creates a new instance of this class.
     * </p>
     *
     * @param operand
     *            the operand of this expression
     * @throws IllegalArgumentException
     *             if operand is <code>null</code>
     */
    public UnaryPositiveExpression(Expression operand) {
        super(operand);
    }

    public Object execute(Context context) throws ExecutionException {
        return getOperand().execute(context);
    }
}
