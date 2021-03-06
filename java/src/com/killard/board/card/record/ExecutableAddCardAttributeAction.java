package com.killard.board.card.record;

import com.killard.board.card.action.AddCardAttributeAction;

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

    public void execute(AbstractCardRecord record, AddCardAttributeAction action) {
        record.addAttribute(action.getAttribute(), action);
    }
}
