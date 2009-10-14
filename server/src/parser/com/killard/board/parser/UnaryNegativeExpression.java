package com.killard.board.parser;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class UnaryNegativeExpression extends UnaryExpression {

    /**
     * <p>
     * Creates a new instance of an unary expression.
     * </p>
     *
     * @param operand
     *            the operand of this expression
     * @throws IllegalArgumentException
     *             if operator is <code>null</code> or trimmed the empty
     *             string
     */
    public UnaryNegativeExpression(Expression operand) {
        super(operand);
    }

    public Object execute(Context context) throws ExecutionException {
        Integer operand = (Integer) getOperand().execute(context);
        return - operand;
    }
}
