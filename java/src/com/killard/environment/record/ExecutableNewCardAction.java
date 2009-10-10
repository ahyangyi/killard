package com.killard.environment.record;

import com.killard.card.action.NewCardAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class ExecutableNewCardAction extends ExecutableCardAction<NewCardAction> {

    public void execute(CardRecord record, NewCardAction action) {
        PlayerRecord player = (PlayerRecord) record.getOwner();
        player.addLivingCard(record, action);
    }
}
