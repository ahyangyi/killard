package com.killard.environment.record;

import com.killard.card.action.RemoveCardAttributeAction;

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

    public void execute(CardRecord record, RemoveCardAttributeAction action) {
        record.removeAttribute(action.getAttribute(), action);
    }
}
