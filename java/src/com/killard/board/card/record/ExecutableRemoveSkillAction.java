package com.killard.board.card.record;

import com.killard.board.card.action.RemoveSkillAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class ExecutableRemoveSkillAction extends ExecutableCardAction<RemoveSkillAction> {

    public void execute(AbstractCardRecord record, RemoveSkillAction action) {
        record.changeSkill(null, action);
    }
}
