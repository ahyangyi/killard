package com.killard.board.parser;

public class NumberLiteral extends Literal {
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
}
