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
public class Continue implements Node {

    private final String label;

    private static final long serialVersionUID = 7202410825658926925L;

    public Continue(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public Object execute(Context context) throws ExecutionException {
        context.getFlowControl().setContinue(getLabel());
        return null;
    }

    @Override
    public String toString() {
        return "continue" + (label == null ? ";" : " " + label + ";");
    }
}
