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
public class AssignmentExpression extends BinaryExpression {

    private static final long serialVersionUID = -98840895344761021L;

    /**
     * <p>
     * Creates a new {@link BinaryExpression} instance.
     * </p>
     *
     * @param lhs
     *            the left operand of this expression
     * @param rhs
     *            the right operand of this expression
     * @throws IllegalArgumentException
     *             if operator is <code>null</code> or trimmed the empty
     *             string, or lhs or rhs are <code>null</code>
     */
    public AssignmentExpression(Expression lhs, Expression rhs) {
        super(lhs, rhs, "=");
    }

    public Object execute(Context context) throws ExecutionException {
        Object value = getRhs().execute(context);
        context.setVariable(getLhs().getText(), value);
        return value;
    }

}
