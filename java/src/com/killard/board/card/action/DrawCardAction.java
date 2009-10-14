package com.killard.board.card.action;

import com.killard.board.card.Board;
import com.killard.board.card.Player;

/**
 * <p>
 * This class defines draw card action.
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class DrawCardAction extends BasicAction<Board, Player> {

    public DrawCardAction(Board source, Player target) {
        super(source, target);
    }
}
