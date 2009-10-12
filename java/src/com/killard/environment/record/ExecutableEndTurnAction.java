package com.killard.environment.record;

import com.killard.card.action.EndTurnAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class ExecutableEndTurnAction extends ExecutablePlayerAction<EndTurnAction> {

    public void execute(AbstractPlayerRecord record, EndTurnAction action) {
        record.nextTurn(action);
    }
}
