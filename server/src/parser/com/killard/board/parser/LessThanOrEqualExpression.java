package com.killard.board.parser;

public class LessThanOrEqualExpression extends BinaryExpression {

    /**
     * <p>
     * Creates a new instance of this class.
     *
     * @param lhs
     *            the left operand of the expression
     * @param rhs
     *            the right operand of the expression
     * @throws IllegalArgumentException
     *             if lhs or rhs is <code>null</code>
     */
    public LessThanOrEqualExpression(Expression lhs, Expression rhs) {
        super(lhs, rhs);
    }

    public Object execute(Context context) throws ExecutionException {
        Integer left = (Integer) getLhs().execute(context);
        Integer right = (Integer) getRhs().execute(context);
        return left <= right;
    }
}
