package com.killard.board.card.action;

import com.killard.board.card.Board;
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
public class LoseAction extends PlayerAction<Board> {

    public LoseAction(Board source, Player player) {
        super(source, player);
    }
}
