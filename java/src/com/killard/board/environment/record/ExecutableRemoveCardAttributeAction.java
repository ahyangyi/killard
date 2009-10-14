package com.killard.board.environment.record;

import com.killard.board.card.action.RemoveCardAttributeAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class ExecutableRemoveCardAttributeAction extends ExecutableCardAction<RemoveCardAttributeAction> {

    public void execute(AbstractCardRecord record, RemoveCardAttributeAction action) {
        record.removeAttribute(action.getAttribute(), action);
    }
}
