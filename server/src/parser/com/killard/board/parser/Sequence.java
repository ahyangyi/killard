package com.killard.board.parser;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class Sequence implements Node {

    private final List<Node> children = new LinkedList<Node>();

    public Sequence() {
    }

    public Node[] getChildren() {
        return children.toArray(new Node[children.size()]);
    }

    public void addChild(Node child) {
        if (child == null) throw new IllegalArgumentException("Argument child can not be null.");
        children.add(child);
    }

    public Object execute(Context context) throws ExecutionException {
        Object result = null;
        for (Node child : getChildren()) {
            result = child.execute(context);
            FlowControl ctrl = context.getFlowControl();
            if (ctrl.isBreak() || ctrl.isContinue() || ctrl.isReturn()) break;
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("[Not implemented yet!]");
        return buf.toString();
    }
}
