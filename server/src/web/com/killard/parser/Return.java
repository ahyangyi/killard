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
public class Return implements Node {

    private final Expression expression;

    public Return(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    public Object execute(Context context) throws ExecutionException {
        context.getFlowControl().setReturn();
        if (getExpression() != null) return getExpression().execute(context);
        else return null;
    }
}
