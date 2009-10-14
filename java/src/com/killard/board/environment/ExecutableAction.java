package com.killard.board.environment;

import com.killard.board.card.Action;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public interface ExecutableAction<R, A extends Action> {

    public void execute(R record, A action);
}
