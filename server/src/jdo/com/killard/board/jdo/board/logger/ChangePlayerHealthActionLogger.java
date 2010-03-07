package com.killard.board.jdo.board.logger;

import com.killard.board.card.action.ChangePlayerHealthAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class ChangePlayerHealthActionLogger extends PlayerActionLogger<ChangePlayerHealthAction> {

    @Override
    public String log(ChangePlayerHealthAction action) {
        return super.log(action) + "\",\"value\":" + action.getHealthChange().getValue();
    }

}
