package com.killard.board.parser;

public class GreaterThanOrEqualExpression extends BinaryExpression {

    private static final long serialVersionUID = -4067720989765343702L;

    /**
     * <p>
     * Creates a new instance of this class.
     * </p>
     *
     * @param lhs
     *            the left operand of the expression
     * @param rhs
     *            the right operand of the expression
     * @throws IllegalArgumentException
     *             if lhs or rhs is <code>null</code>
     */
    public GreaterThanOrEqualExpression(Expression lhs, Expression rhs) {
        super(lhs, rhs, ">=");
    }

    public Object execute(Context context) throws ExecutionException {
        Integer left = toInteger(getLhs().execute(context));
        Integer right = toInteger(getRhs().execute(context));
        return left >= right;
    }
}
