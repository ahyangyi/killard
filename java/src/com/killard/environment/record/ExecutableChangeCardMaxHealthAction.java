package com.killard.environment.record;

import com.killard.card.action.ChangeCardMaxHealthAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class ExecutableChangeCardMaxHealthAction extends ExecutableCardAction<ChangeCardMaxHealthAction> {

    public void execute(CardRecord record, ChangeCardMaxHealthAction action) {
        record.changeMaxHealth(action.getMaxHealthChange(), action);
    }
}
