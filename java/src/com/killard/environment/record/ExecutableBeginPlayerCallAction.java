package com.killard.environment.record;

import com.killard.card.action.BeginPlayerCallAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class ExecutableBeginPlayerCallAction extends ExecutablePlayerAction<BeginPlayerCallAction> {

    public void execute(AbstractPlayerRecord record, BeginPlayerCallAction action) {
        record.setCalled(true, action);
    }
}
