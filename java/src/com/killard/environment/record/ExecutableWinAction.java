package com.killard.environment.record;

import com.killard.card.action.LoseAction;
import com.killard.card.action.WinAction;

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
