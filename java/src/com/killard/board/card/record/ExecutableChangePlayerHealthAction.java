package com.killard.board.card.record;

import com.killard.board.card.action.ChangePlayerHealthAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class ExecutableChangePlayerHealthAction extends ExecutablePlayerAction<ChangePlayerHealthAction> {

    public void execute(AbstractPlayerRecord record, ChangePlayerHealthAction action) {
        record.changeHealth(action.getHealthChange(), action);
    }
}
