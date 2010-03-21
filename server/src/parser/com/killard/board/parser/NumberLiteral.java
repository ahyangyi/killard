package com.killard.board.parser;

public class NumberLiteral extends Literal {

    private static final long serialVersionUID = 2789200945772857707L;

    /**
     * <p>
     * Creates a new instance of this node, wrapping the given value.
     * </p>
     *
     * @param text
     *            the value this literal represents
     */
    public NumberLiteral(String text) {
        super(text);
    }

    public Object execute(Context context) throws ExecutionException {
        return Integer.parseInt(getText());
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(getText());
        return buf.toString();
    }
}
