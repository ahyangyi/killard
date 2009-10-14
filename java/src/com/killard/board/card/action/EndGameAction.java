package com.killard.board.card.action;

import com.killard.board.card.Board;
import com.killard.board.card.Player;

/**
 * <p>
 * This class defines end game action.
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class EndGameAction extends PlayerAction<Board> {

    public EndGameAction(Board source, Player target) {
        super(source, target);
    }
}
