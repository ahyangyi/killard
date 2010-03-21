package com.killard.board.parser;

public class NotEqualExpression extends BinaryExpression {

    private static final long serialVersionUID = -2109087284762209388L;

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
    public NotEqualExpression(Expression lhs, Expression rhs) {
        super(lhs, rhs, "!=");
    }

    public Object execute(Context context) throws ExecutionException {
        Object left = getLhs().execute(context);
        Object right = getRhs().execute(context);
        return left == null ? right != null : !left.equals(right);
    }
}
