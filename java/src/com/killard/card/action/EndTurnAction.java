package com.killard.card.action;

import com.killard.card.Player;
import com.killard.card.Board;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public final class EndTurnAction extends PlayerAction<Board> {

    public EndTurnAction(Board source, Player player) {
        super(source, player);
    }
}
