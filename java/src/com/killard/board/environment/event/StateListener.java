package com.killard.board.environment.event;

import java.io.Serializable;

/**
 * <p>
 * This interface defines the contract for .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * The implementations are not required to be thread safe.
 * </p>
 */
public interface StateListener extends Serializable {

    public void stateChanged(StateEvent event);

}
