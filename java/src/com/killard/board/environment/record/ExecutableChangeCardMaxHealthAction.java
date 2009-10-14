package com.killard.board.environment.record;

import com.killard.board.card.action.ChangeCardMaxHealthAction;

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

    public void execute(AbstractMetaCardRecord record, ChangeCardMaxHealthAction action) {
        record.changeMaxHealth(action.getMaxHealthChange(), action);
    }
}
