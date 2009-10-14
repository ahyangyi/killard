package com.killard.board.environment.record;

import com.killard.board.card.action.EndPlayerCallAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class ExecutableEndPlayerCallAction extends ExecutablePlayerAction<EndPlayerCallAction> {

    public void execute(AbstractPlayerRecord record, EndPlayerCallAction action) {
        record.setCalled(false, action);
    }
}
