package com.killard.board.jdo.board.logger;

import com.killard.board.card.action.DiscardCardAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class DiscardCardActionLogger extends BasicActionLogger<DiscardCardAction> {

    @Override
    public String log(DiscardCardAction action) {
        return "\"source\":" + logPlayer(action.getSource()) + ",\"target\":" + logMetaCard(action.getTarget());
    }
}
