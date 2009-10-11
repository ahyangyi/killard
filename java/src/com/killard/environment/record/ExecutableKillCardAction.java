package com.killard.environment.record;

import com.killard.card.action.KillCardAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class ExecutableKillCardAction extends ExecutableCardAction<KillCardAction> {

    public void execute(AbstractCardRecord record, KillCardAction action) {
        AbstractPlayerRecord player = (AbstractPlayerRecord) record.getOwner();
        player.removeLivingCard(record, action);
    }
}
