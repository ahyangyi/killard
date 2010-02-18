package com.killard.board.jdo.board.logger;

import com.killard.board.card.action.BeginGameAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class BeginGameActionLogger extends BasicActionLogger<BeginGameAction> {

    @Override
    public String log(BeginGameAction action) {
        return "";
    }
}
