package com.killard.environment.record;

import com.killard.card.action.NextTurnAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class ExecutableNextTurnAction extends ExecutablePlayerAction<NextTurnAction> {

    public void execute(PlayerRecord record, NextTurnAction action) {
        record.nextTurn(action);
    }
}
