package com.killard.board.card.record;

import com.killard.board.card.action.ChangePlayerPropertyAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class ExecutableChangePlayerPropertyAction extends ExecutableCardAction<ChangePlayerPropertyAction> {

    public void execute(AbstractCardRecord record, ChangePlayerPropertyAction action) {
        record.setProperty(action.getName(), action.getValue(), action);
    }
}
