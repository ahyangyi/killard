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
public class EndPlayerCallAction extends PlayerAction<Player> {

    public EndPlayerCallAction(Player source, Player target) {
        super(source, target);
    }
}
