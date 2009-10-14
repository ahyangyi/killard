package com.killard.board.card.action;

import com.killard.board.card.Player;
import com.killard.board.card.MetaCard;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class DealCardAction extends BasicAction<Player, MetaCard> {

    public DealCardAction(Player source, MetaCard target) {
        super(source, target);
    }
}
