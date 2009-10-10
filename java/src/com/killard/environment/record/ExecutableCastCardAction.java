package com.killard.environment.record;

import com.killard.card.action.CastCardAction;

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

    public void execute(CardRecord record, CastCardAction action) {
        // cast magic card
        if (record.getMaxHealth() <= 0) {
            PlayerRecord player = (PlayerRecord) record.getOwner();
            player.setCardPlayed(true);
        }
        record.changeCasted(true, action);
    }
}
