package com.killard.board.environment.record;

import com.killard.board.card.action.DealCardAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class ExecutableDealCardAction extends ExecutablePlayerAction<DealCardAction> {

    public void execute(AbstractPlayerRecord record, DealCardAction action) {
        record.addDealtCard(action.getTarget(), action);
    }
}
