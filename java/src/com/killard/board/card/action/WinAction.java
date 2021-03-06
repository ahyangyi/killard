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
public class WinAction extends PlayerAction<Board> {

    public WinAction(Board source, Player target) {
        super(source, target);
    }
}
