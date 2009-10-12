package com.killard.card.action;

import com.killard.card.Player;
import com.killard.card.Board;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class BeginGameAction extends PlayerAction<Board> {

    public BeginGameAction(Board source, Player target) {
        super(source, target);
    }
}
