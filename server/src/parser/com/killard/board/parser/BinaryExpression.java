package com.killard.board.parser;

public abstract class BinaryExpression extends Expression {

    private final Expression lhs;

    private final Expression rhs;

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
    protected BinaryExpression(Expression lhs, Expression rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    /**
     * <p>
     * Returns the left-hand-side operand of this expression.
     * </p>
     *
     * @return the left operand of this expression
     */
    public Expression getLhs() {
        return lhs;
    }

    /**
     * <p>
     * Returns the right-hand-side operand of this expression.
     * </p>
     *
     * @return the right operand of this expression
     */
    public Expression getRhs() {
        return rhs;
    }

    public String getText() {
        return lhs.getText();
    }
}
