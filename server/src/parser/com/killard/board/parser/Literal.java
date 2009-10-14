package com.killard.board.parser;

public abstract class Literal extends Expression {

    private final String text;

    /**
     * <p>
     * Creates a new literal node, having value as the value, and no children.
     * Value is allowed to be <code>null</code>.
     * </p>
     *
     * @param text
     *            the value of this literal node
     */
    protected Literal(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
