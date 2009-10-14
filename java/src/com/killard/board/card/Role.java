package com.killard.board.card;

import com.killard.board.environment.event.ActionListener;

/**
 * <p>
 * This interface defines the contract for game role.
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * The implementations are not required to be thread safe.
 * </p>
 */
public interface Role extends ActionListener {

    public boolean isVisible();

}
