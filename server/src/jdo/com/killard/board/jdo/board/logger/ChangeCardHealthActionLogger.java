package com.killard.board.jdo.board.logger;

import com.killard.board.card.action.ChangeCardHealthAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class ChangeCardHealthActionLogger extends CardActionLogger<ChangeCardHealthAction> {

    @Override
    public String log(ChangeCardHealthAction action) {
        return super.log(action) + ",\"healthChange\":" + action.getHealthChange().getValue();
    }
}
