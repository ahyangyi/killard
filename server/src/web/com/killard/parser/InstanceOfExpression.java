package com.killard.parser;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class InstanceOfExpression extends BinaryExpression {

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
     *             string
     * @throws IllegalArgumentException
     *             if lhs or rhs are <code>null</code>
     */
    public InstanceOfExpression(Expression lhs, Expression rhs) {
        super(lhs, rhs);
    }

    public Object execute(Context context) throws ExecutionException {
        Object left = getLhs().execute(context);
        Class right = (Class) getRhs().execute(context);
        return right.isAssignableFrom(left.getClass());
    }
}
