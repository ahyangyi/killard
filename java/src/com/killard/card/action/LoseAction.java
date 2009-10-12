package com.killard.card.action;

import com.killard.card.Role;
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
public class LoseAction extends PlayerAction<Board> {

    public LoseAction(Board source, Player player) {
        super(source, player);
    }
}
