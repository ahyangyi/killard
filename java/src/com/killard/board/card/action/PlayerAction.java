package com.killard.board.card.action;

import com.killard.board.card.Player;
import com.killard.board.card.Record;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public abstract class PlayerAction<S extends Record> extends BasicAction<S, Player> {

    protected PlayerAction(S source, Player target) {
        super(source, target);
    }
}
