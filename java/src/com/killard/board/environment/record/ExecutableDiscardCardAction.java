package com.killard.board.environment.record;

import com.killard.board.card.action.DiscardCardAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class ExecutableDiscardCardAction extends ExecutablePlayerAction<DiscardCardAction> {

    public void execute(AbstractPlayerRecord record, DiscardCardAction action) {
        record.removeDealtCard(action.getTarget(), action);
    }
}
