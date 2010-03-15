package com.killard.board.environment.event;

import com.killard.board.card.Action;

import java.util.EventObject;

/**
 * <p>
 * This class defines action event on games board.
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class ActionEvent extends EventObject {

    private final Action action;

    public ActionEvent(Object source, Action action) {
        super(source);
        this.action = action;
    }

    public Action getAction() {
        return action;
    }
}
