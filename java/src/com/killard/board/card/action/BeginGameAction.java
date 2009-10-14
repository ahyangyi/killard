package com.killard.board.card.action;

import com.killard.board.card.Board;

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
