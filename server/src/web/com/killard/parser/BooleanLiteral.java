package com.killard.parser;

public class BooleanLiteral extends Literal {
    /**
     * <p>
     * Creates a {@link BooleanLiteral} instance, wrapping the given value.
     * </p>
     *
     * @param text
     *            the value this literal represents
     */
    public BooleanLiteral(String text) {
        super(text);
    }

    public Object execute(Context context) throws ExecutionException {
        return Boolean.parseBoolean(getText());
    }
}
