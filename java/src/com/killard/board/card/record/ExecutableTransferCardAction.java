package com.killard.board.card.record;

import com.killard.board.card.action.TransferCardAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class ExecutableTransferCardAction extends ExecutablePlayerAction<TransferCardAction> {

    public void execute(AbstractPlayerRecord record, TransferCardAction action) {
        AbstractPlayerRecord owner = (AbstractPlayerRecord) action.getSource().getOwner();
        owner.removeEquippedCard(action.getSource());
        record.addEquippedCard(action.getSource());
    }
}
