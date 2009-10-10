package com.killard.environment.record;

import com.killard.card.action.ChangeCardHealthAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class ExecutableChangeCardHealthAction extends ExecutableCardAction<ChangeCardHealthAction> {

    public void execute(CardRecord record, ChangeCardHealthAction action) {
        record.changeHealth(action.getHealthChange(), action);
    }
}
