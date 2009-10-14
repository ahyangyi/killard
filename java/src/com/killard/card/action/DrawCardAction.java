package com.killard.card.action;

import com.killard.card.Player;
import com.killard.card.Card;
import com.killard.card.Board;

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
