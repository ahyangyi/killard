package com.killard.environment.record;

import com.killard.card.action.EndTurnAction;
import com.killard.card.action.BeginTurnAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class ExecutableBeginTurnAction extends ExecutablePlayerAction<BeginTurnAction> {

    public void execute(AbstractPlayerRecord record, BeginTurnAction action) {
        record.beginTurn(action);
    }
}