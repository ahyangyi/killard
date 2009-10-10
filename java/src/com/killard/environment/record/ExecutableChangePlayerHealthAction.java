package com.killard.environment.record;

import com.killard.card.action.ChangePlayerHealthAction;

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

    public void execute(PlayerRecord record, ChangePlayerHealthAction action) {
        record.changeHealth(action.getHealthChange(), action);
    }
}
