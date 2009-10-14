package com.killard.board.parser;

public abstract class Expression implements Node {

    /**
     * <p>
     * Creates a new expression node, having value as the value, and no
     * children. Value is allowed to be <code>null</code>.
     * </p>
     */
    protected Expression() {
    }

    public abstract String getText();
}
