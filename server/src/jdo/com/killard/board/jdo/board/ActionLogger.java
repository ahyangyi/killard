package com.killard.board.jdo.board;

import com.killard.board.card.Action;

/**
 * <p>
 * This interface defines the contract for .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * The implementations are not required to be thread safe.
 * </p>
 */
public interface ActionLogger {
    public String log(Action action);
}
