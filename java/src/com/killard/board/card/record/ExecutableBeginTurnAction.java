package com.killard.board.card.record;

import com.killard.board.card.action.BeginTurnAction;

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
