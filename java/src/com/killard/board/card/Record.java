package com.killard.board.card;

/**
 * <p>
 * This interface defines the contract for .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * The implementations are not required to be thread safe.
 * </p>
 */
public interface Record<T extends Record> extends Comparable<T> {

    public Object getProperty(String name);

}
