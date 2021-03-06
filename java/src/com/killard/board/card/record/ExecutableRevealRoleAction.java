package com.killard.board.card.record;

import com.killard.board.card.action.RevealRoleAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class ExecutableRevealRoleAction extends ExecutablePlayerAction<RevealRoleAction> {

    public void execute(AbstractPlayerRecord record, RevealRoleAction action) {
        record.setRoleVisible(true, action);
    }
}
