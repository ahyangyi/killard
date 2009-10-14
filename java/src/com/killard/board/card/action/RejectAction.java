package com.killard.board.card.action;

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
public class RejectAction extends BasicAction<Record, Record> {

    public RejectAction(Record source, Record target) {
        super(source, target);
    }
}
