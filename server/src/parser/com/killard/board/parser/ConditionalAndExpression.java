package com.killard.board.parser;

public class ConditionalAndExpression extends BinaryExpression {

    public ConditionalAndExpression(Expression lhs, Expression rhs) {
        super(lhs, rhs);
    }

    public Object execute(Context context) throws ExecutionException {
        return (Boolean) getLhs().execute(context) && (Boolean) getRhs().execute(context);
    }
}
