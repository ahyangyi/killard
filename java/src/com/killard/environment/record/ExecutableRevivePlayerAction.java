package com.killard.environment.record;

import com.killard.card.action.KillPlayerAction;
import com.killard.card.action.RevivePlayerAction;

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
