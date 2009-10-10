package com.killard.environment.record;

import com.killard.card.action.AddCardAttributeAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class ExecutableAddCardAttributeAction extends ExecutableCardAction<AddCardAttributeAction> {

    public void execute(CardRecord record, AddCardAttributeAction action) {
        record.addAttribute(action.getAttribute(), action);
    }
}
