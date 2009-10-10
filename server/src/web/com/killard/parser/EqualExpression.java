package com.killard.parser;

public class EqualExpression extends BinaryExpression {

    public EqualExpression(Expression lhs, Expression rhs) {
        super(lhs, rhs);
    }

    public Object execute(Context context) throws ExecutionException {
        Object left = getLhs().execute(context);
        Object right = getRhs().execute(context);
        return left == null ? right == null : left.equals(right);
    }
}
