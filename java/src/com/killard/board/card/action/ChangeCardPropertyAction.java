package com.killard.board.card.action;

import com.killard.board.card.Card;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class ChangeCardPropertyAction extends CardAction<Card> {

    private final String name;

    private final Object value;

    public ChangeCardPropertyAction(Card source, Card target, String name, Object value) {
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
