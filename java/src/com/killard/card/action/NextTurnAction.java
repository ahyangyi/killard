package com.killard.card.action;

import com.killard.card.Player;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public final class NextTurnAction extends PlayerAction<Player> {

    public NextTurnAction(Player player) {
        super(player, player);
    }
}
