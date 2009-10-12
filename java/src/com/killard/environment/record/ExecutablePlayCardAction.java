package com.killard.environment.record;

import com.killard.card.action.PlayCardAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class ExecutablePlayCardAction extends ExecutableCardAction<PlayCardAction> {

    public void execute(AbstractCardRecord record, PlayCardAction action) {
        AbstractPlayerRecord player = (AbstractPlayerRecord) record.getOwner();
        player.addEquippedCard(record, action);
    }
}
