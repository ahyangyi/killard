package com.killard.card.action;

import com.killard.card.Board;
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
public class BeginPlayerCallAction extends PlayerAction<Player> {

    public BeginPlayerCallAction(Player source, Player target) {
        super(source, target);
    }
}
