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
public class Break implements Node {

    private final String label;

    private static final long serialVersionUID = 2182958197875795169L;

    public Break(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public Object execute(Context context) throws ExecutionException {
        context.getFlowControl().setBreak(getLabel());
        return null;
    }

    @Override
    public String toString() {
        return "break" + (label == null ? ";" : " " + label + ";");
    }
}
