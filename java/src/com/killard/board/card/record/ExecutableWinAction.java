package com.killard.board.card.record;

import com.killard.board.card.action.WinAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class ExecutableWinAction extends ExecutablePlayerAction<WinAction> {

    public void execute(AbstractPlayerRecord record, WinAction action) {
        record.win(action);
    }
}
