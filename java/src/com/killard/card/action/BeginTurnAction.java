package com.killard.card.action;

import com.killard.card.Player;
import com.killard.card.Board;

/**
 * <p>
 * This class defines begin turn action.
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class BeginTurnAction extends PlayerAction<Board> {

    public BeginTurnAction(Board source, Player target) {
        super(source, target);
    }
}
