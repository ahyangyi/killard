package com.killard.card.action;

import com.killard.card.Player;
import com.killard.card.Card;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class DealCardAction extends BasicAction<Player, Card> {

    public DealCardAction(Player source, Card target) {
        super(source, target);
    }
}
