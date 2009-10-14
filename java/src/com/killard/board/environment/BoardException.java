package com.killard.board.environment;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class BoardException extends Exception {

    public BoardException() {
    }

    public BoardException(String s) {
        super(s);
    }

    public BoardException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
