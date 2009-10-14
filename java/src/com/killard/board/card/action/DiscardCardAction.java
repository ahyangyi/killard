package com.killard.board.card.action;

import com.killard.board.card.Player;
import com.killard.board.card.Card;

/**
 * <p>
 * This class defines discard card action.
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class DiscardCardAction extends BasicAction<Player, Card> {

    public DiscardCardAction(Player source, Card target) {
        super(source, target);
    }
}
