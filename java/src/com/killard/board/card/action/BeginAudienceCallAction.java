package com.killard.board.card.action;

import com.killard.board.card.Player;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class BeginAudienceCallAction extends PlayerAction<Player> {

    protected BeginAudienceCallAction(Player source, Player target) {
        super(source, target);
    }
}
