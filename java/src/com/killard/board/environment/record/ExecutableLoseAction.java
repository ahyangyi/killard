package com.killard.board.environment.record;

import com.killard.board.card.action.LoseAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class ExecutableLoseAction extends ExecutablePlayerAction<LoseAction> {

    public void execute(AbstractPlayerRecord record, LoseAction action) {
        record.lose(action);
    }
}
