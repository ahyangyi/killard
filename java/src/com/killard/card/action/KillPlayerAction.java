package com.killard.card.action;

import com.killard.card.CardInstance;
import com.killard.card.Player;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public final class KillPlayerAction extends PlayerAction<CardInstance> {

    public KillPlayerAction(CardInstance source, Player target) {
        super(source, target);
    }
}
