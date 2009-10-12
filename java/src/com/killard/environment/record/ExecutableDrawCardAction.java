package com.killard.environment.record;

import com.killard.card.action.PlayCardAction;
import com.killard.card.action.DrawCardAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class ExecutableDrawCardAction extends ExecutablePlayerAction<DrawCardAction> {

    public void execute(AbstractPlayerRecord record, DrawCardAction action) {
        record.addDealtCard(action.getTarget(), action);
    }
}
