package com.killard.board.card.action;

import com.killard.board.card.CardInstance;
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
public final class EquipCardAction extends CardAction<Player> {

    public EquipCardAction(Player source, CardInstance target) {
        super(source, target);
    }
}
