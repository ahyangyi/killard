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
public class ExecutionException extends Exception {

    public ExecutionException() {
    }

    public ExecutionException(String s) {
        super(s);
    }

    public ExecutionException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
