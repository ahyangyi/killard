package com.killard.board.card.action;

import com.killard.board.card.Record;
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
public class ChangePlayerPropertyAction<S extends Record> extends PlayerAction<S> {

    private final String name;

    private final Object value;

    public ChangePlayerPropertyAction(S source, Player target, String name, Object value) {
        super(source, target);
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
