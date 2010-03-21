package com.killard.board.parser;

public class ConditionalOrExpression extends BinaryExpression {

    private static final long serialVersionUID = 7891559706616416751L;

    public ConditionalOrExpression(Expression lhs, Expression rhs) {
        super(lhs, rhs, "||");
    }

    public Object execute(Context context) throws ExecutionException {
        return (Boolean) getLhs().execute(context) || (Boolean) getRhs().execute(context);
    }
}
