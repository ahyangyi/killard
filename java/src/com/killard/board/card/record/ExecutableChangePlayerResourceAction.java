package com.killard.board.card.record;

import com.killard.board.card.action.ChangePlayerResourceAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class ExecutableChangePlayerResourceAction extends ExecutablePlayerAction<ChangePlayerResourceAction> {

    public void execute(AbstractPlayerRecord record, ChangePlayerResourceAction action) {
        record.changeElementForSchool(action.getElement(), action.getValue(), action);
    }
}
