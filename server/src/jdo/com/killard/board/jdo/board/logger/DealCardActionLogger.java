package com.killard.board.jdo.board.logger;

import com.killard.board.card.action.DealCardAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class DealCardActionLogger extends BasicActionLogger<DealCardAction> {

    @Override
    public String log(DealCardAction action) {
        return "\"source\":" + logMetaCard(action.getSource()) + ",\"target\":" + logPlayer(action.getTarget());
    }
}
