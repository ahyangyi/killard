package com.killard.board.card.action;

import com.killard.board.card.Card;
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
public abstract class CardAction<S extends Record> extends BasicAction<S, Card> {

    protected CardAction(S source, Card target) {
        super(source, target);
    }
}
