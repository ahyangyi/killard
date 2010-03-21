package com.killard.board.parser;

public class ConditionalAndExpression extends BinaryExpression {

    private static final long serialVersionUID = 1169242093359557055L;

    public ConditionalAndExpression(Expression lhs, Expression rhs) {
        super(lhs, rhs, "&&");
    }

    public Object execute(Context context) throws ExecutionException {
        return (Boolean) getLhs().execute(context) && (Boolean) getRhs().execute(context);
    }
}
