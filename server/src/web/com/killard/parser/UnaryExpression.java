package com.killard.parser;

public abstract class UnaryExpression extends Expression {

    private final Expression operand;

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
    protected UnaryExpression(Expression operand) {
        this.operand = operand;
    }

    /**
     * <p>
     * Returns the operand of this expression.
     * </p>
     *
     * @return the operand of this expression
     */
    public Expression getOperand() {
        return operand;
    }

    public String getText() {
        return getOperand().getText();
    }
}
