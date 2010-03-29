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
public class ForControl extends LoopControl {

    private final Node init;

    private final Expression condition;

    private final Node update;

    private static final long serialVersionUID = -3042372369131127882L;

    public ForControl(Node init, Expression condition, Node update) {
        this.init = init;
        this.condition = condition;
        this.update = update;
    }

    public Node getInit() {
        return init;
    }

    public Expression getCondition() {
        return condition;
    }

    public Node getUpdate() {
        return update;
    }

    public Object execute(Context context) throws ExecutionException {
        return getCondition().execute(context);
    }

    public void init(Context context) throws ExecutionException {
        Node init = getInit();
        if (init != null) init.execute(context);
    }

    public void update(Context context) throws ExecutionException {
        Node update = getUpdate();
        if (update != null) update.execute(context);
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("for (");
        buf.append(init.toString());
        buf.append("; ");
        buf.append(condition.toString());
        buf.append("; ");
        buf.append(update.toString());
        buf.append(")");
        return buf.toString();
    }
}
