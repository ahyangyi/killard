package com.killard.board.parser;

public abstract class UnaryExpression extends Expression {

    private final Expression operand;

    private final String operator;

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
    protected UnaryExpression(Expression operand, String operator) {
        this.operand = operand;
        this.operator = operator;
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

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(operator);
        buf.append(getOperand().toString());
        return buf.toString();
    }
}
