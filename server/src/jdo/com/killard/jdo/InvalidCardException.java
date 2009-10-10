package com.killard.jdo;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class InvalidCardException extends Exception {

    public InvalidCardException() {
    }

    public InvalidCardException(String s) {
        super(s);
    }

    public InvalidCardException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public InvalidCardException(Throwable throwable) {
        super(throwable);
    }
}
