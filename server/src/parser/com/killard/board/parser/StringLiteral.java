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
public class StringLiteral extends Literal{

    private static final long serialVersionUID = 1961171118734451864L;

    public StringLiteral(String text) {
        super(text);
    }

    public Object execute(Context context) throws ExecutionException {
        return getText();
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("\"");
        buf.append(getText());
        buf.append("\"");
        return buf.toString();
    }
}
