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
public class UnaryIncreaseExpression extends UnaryExpression {

    private static final long serialVersionUID = -3169479782570267391L;

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
    public UnaryIncreaseExpression(Expression operand) {
        super(operand, "++");
    }

    public Object execute(Context context) throws ExecutionException {
        Integer operand = toInteger(getOperand().execute(context));
        double result = operand + 1;
        context.setVariable(getOperand().getText(), result);
        return result;
    }
}
