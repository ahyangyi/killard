package com.killard.environment.record;

import com.killard.card.action.ChangeCardAttackAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class ExecutableChangeCardAttackAction extends ExecutableCardAction<ChangeCardAttackAction> {

    public void execute(AbstractCardRecord record, ChangeCardAttackAction action) {
        record.changeAttack(action.getAttack(), action);
    }
}
