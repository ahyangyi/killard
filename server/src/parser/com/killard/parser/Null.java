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
public class Null extends Expression {

    public Null() {
    }

    public String getText() {
        return "null";
    }

    public Object execute(Context context) throws ExecutionException {
        return null;
    }
}
