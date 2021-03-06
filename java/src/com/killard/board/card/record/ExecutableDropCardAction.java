package com.killard.board.card.record;

import com.killard.board.card.action.DropCardAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class ExecutableDropCardAction extends ExecutableCardAction<DropCardAction> {

    public void execute(AbstractCardRecord record, DropCardAction action) {
        AbstractPlayerRecord player = (AbstractPlayerRecord) action.getSource();
        player.removeEquippedCard(record, action);
    }
}
