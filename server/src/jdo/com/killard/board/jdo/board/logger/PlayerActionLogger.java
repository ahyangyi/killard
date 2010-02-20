package com.killard.board.jdo.board.logger;

import com.killard.board.card.Player;
import com.killard.board.card.action.PlayerAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class PlayerActionLogger<A extends PlayerAction> extends BasicActionLogger<A> {

    @Override
    public String log(A action) {
        return "\"target\":" + logPlayer((Player) action.getTarget());
    }
}
