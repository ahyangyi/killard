package com.killard.board.card.record;

import com.killard.board.card.action.RevivePlayerAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class ExecutableRevivePlayerAction extends ExecutablePlayerAction<RevivePlayerAction> {

    public void execute(AbstractPlayerRecord record, RevivePlayerAction action) {
        record.setAlive(true, action);
    }
}
