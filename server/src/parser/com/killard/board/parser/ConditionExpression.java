package com.killard.board.parser;

public class ConditionExpression extends Expression {

    private final Expression test;

    private final Expression trueExpression;

    private final Expression falseExpression;

    private static final long serialVersionUID = 4488709754268276767L;

    /**
     * <p>
     * Creates a new {@link ConditionExpression} instance wrapping the given expression.
     * </p>
     *
     * @param test the expression to wrap
     * @param trueExpression the true expression to wrap
     * @param falseExpression the false expression to wrap
     * @throws IllegalArgumentException
     *             if expression is <code>null</code>
     */
    public ConditionExpression(Expression test, Expression trueExpression, Expression falseExpression) {
        this.test = test;
        this.trueExpression = trueExpression;
        this.falseExpression = falseExpression;
    }

    /**
     * <p>
     * Returns the wrapped expression.
     * </p>
     *
     * @return the wrapped expression
     */
    public Expression getTest() {
        return test;
    }

    public Expression getTrueExpression() {
        return trueExpression;
    }

    public Expression getFalseExpression() {
        return falseExpression;
    }

    public Object execute(Context context) throws ExecutionException {
        Boolean test = (Boolean) getTest().execute(context);
        if (test) {
            return getTrueExpression().execute(context);
        } else {
            return getFalseExpression().execute(context);
        }
    }

    public String getText() {
        return test.getText();
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(test.toString());
        buf.append(" ? ");
        buf.append(trueExpression.toString());
        buf.append(" : ");
        buf.append(falseExpression.toString());
        return buf.toString();
    }
}
