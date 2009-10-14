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
public class BeginGameAction extends BasicAction<Board, Board> {

    public BeginGameAction(Board board) {
        super(board, board);
    }
}
