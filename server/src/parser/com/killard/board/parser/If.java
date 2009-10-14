package com.killard.board.parser;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class If implements Node {

    private final Expression condition;

    private final Node body;

    private final Node elseBody;

    public If(Expression condition, Node body, Node elseBody) {
        this.condition = condition;
        this.body = body;
        this.elseBody = elseBody;
    }

    public Object execute(Context context) throws ExecutionException {
        if ((Boolean) getCondition().execute(context)) {
            return getBody().execute(context);
        } else {
            if (getElseBody() != null) return getElseBody().execute(context);
        }
        return null;
    }

    public Expression getCondition() {
        return condition;
    }

    public Node getBody() {
        return body;
    }

    public Node getElseBody() {
        return elseBody;
    }
}
