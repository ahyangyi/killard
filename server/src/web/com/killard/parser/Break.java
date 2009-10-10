package com.killard.parser;

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
}
