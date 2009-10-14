package com.killard.board.card.action;

import com.killard.board.card.CardInstance;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public abstract class CardAction<S> extends BasicAction<S, CardInstance> {

    protected CardAction(S source, CardInstance target) {
        super(source, target);
    }
}
