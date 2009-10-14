package com.killard.board.environment.record;

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

    public void execute(AbstractMetaCardRecord record, CastCardAction action) {
        // cast magic board
        if (record.getMaxHealth() <= 0) {
            PlayerRecord player = (PlayerRecord) record.getOwner();
            player.setCardPlayed(true);
        }
        record.changeCasted(true, action);
    }
}
