package com.killard.board.card.record;

import com.killard.board.card.action.KillPlayerAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class ExecutableKillPlayerAction extends ExecutablePlayerAction<KillPlayerAction> {

    public void execute(AbstractPlayerRecord record, KillPlayerAction action) {
        record.setAlive(false, action);
    }
}
