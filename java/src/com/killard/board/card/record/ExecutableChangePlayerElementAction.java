package com.killard.board.card.record;

import com.killard.board.card.action.ChangePlayerElementAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class ExecutableChangePlayerElementAction extends ExecutablePlayerAction<ChangePlayerElementAction> {

    public void execute(AbstractPlayerRecord record, ChangePlayerElementAction action) {
        record.changeElementForSchool(action.getElementSchool(), action.getValue(), action);
    }
}
