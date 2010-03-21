package com.killard.board.parser;

public class UnaryNotExpression extends UnaryExpression {

    private static final long serialVersionUID = 6875856613194222391L;

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
    public UnaryNotExpression(Expression operand) {
        super(operand, "!");
    }

    public Object execute(Context context) throws ExecutionException {
        Boolean result = (Boolean) getOperand().execute(context);
        return !result;
    }
}
