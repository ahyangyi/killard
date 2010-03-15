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
public class VariableCall extends Call {

    public VariableCall(String text) {
        super(text);
    }

    public Object execute(Context context) throws ExecutionException {
        return context.getVariable(getText());
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("[Not implemented yet!]");
        return buf.toString();
    }
}
