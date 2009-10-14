package com.killard.board.card.record;

import com.killard.board.card.action.ChangeCardPropertyAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class ExecutableChangeCardPropertyAction extends ExecutableCardAction<ChangeCardPropertyAction> {

    public void execute(AbstractCardRecord record, ChangeCardPropertyAction action) {
        record.setProperty(action.getName(), action.getValue());
    }
}
