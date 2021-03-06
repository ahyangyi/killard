package com.killard.board.card.record;

import com.killard.board.card.action.CastCardAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class ExecutableCastCardAction extends ExecutableCardAction<CastCardAction> {

    public void execute(AbstractCardRecord record, CastCardAction action) {
        if (record.getMaxHealth() <= 0) {
            AbstractPlayerRecord player = (AbstractPlayerRecord) record.getOwner();
            player.setCardPlayed(true);
        }
        record.changeCasted(true, action);
    }
}
