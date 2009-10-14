package com.killard.environment.record;

import com.killard.card.action.EndPlayerCallAction;

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
