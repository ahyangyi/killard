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
public abstract class Call extends Expression {

    private final String text;

    protected Call(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
