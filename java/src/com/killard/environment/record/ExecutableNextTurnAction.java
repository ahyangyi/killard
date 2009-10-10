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
public class ExecutableNextTurnAction extends ExecutablePlayerAction<EndTurnAction> {

    public void execute(PlayerRecord record, EndTurnAction action) {
        record.nextTurn(action);
    }
}
