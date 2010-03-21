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
public class Null extends Expression {

    private static final long serialVersionUID = 1306978927411553184L;

    public Null() {
    }

    public String getText() {
        return "null";
    }

    public Object execute(Context context) throws ExecutionException {
        return null;
    }

    @Override
    public String toString() {
        return "null";
    }
}
