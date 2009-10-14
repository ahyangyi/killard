package com.killard.board.environment.event;

import com.killard.board.card.Action;
import com.killard.board.card.Record;

import java.util.EventObject;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class StateEvent extends EventObject {

    private final Action action;

    public StateEvent(Record source, Action action) {
        super(source);
        this.action = action;
    }

    public Action getAction() {
        return action;
    }
}
