package com.killard.board.card.action;

import com.killard.board.card.MetaCard;
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
public class DealCardAction extends BasicAction<MetaCard, Player> {

    public DealCardAction(MetaCard source, Player target) {
        super(source, target);
    }
}
