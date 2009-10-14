package com.killard.board.card.action;

import com.killard.board.card.Card;
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
public class TransferCardAction extends PlayerAction<Card> {

    public TransferCardAction(Card source, Player target) {
        super(source, target);
    }
}
