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
public class Loop implements Node {

    private final String label;

    private final LoopControl ctrl;

    private final Node body;

    public Loop(String label, LoopControl ctrl, Node body) {
        this.label = label;
        this.ctrl = ctrl;
        this.body = body;
    }

    public String getLabel() {
        return label;
    }

    public LoopControl getControl() {
        return ctrl;
    }

    public Node getBody() {
        return body;
    }

    @Override
    public Object execute(Context context) throws ExecutionException {
        Object result = null;

        Context local = new LocalContext(context);
        getControl().init(local);

        while ((Boolean) getControl().execute(local)) {
            result = getBody().execute(local);
            if (context.getFlowControl().checkBreak(getLabel())) break;
            getControl().update(local);
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        return buf.toString();
    }
}
