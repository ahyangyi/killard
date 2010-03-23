package com.killard.board.card.action;

import com.killard.board.card.Element;
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
public final class ChangePlayerElementAction extends PlayerAction<Record> {

    private final Element element;

    private final Integer value;

    public ChangePlayerElementAction(Record source, Player target, Element element, Integer value) {
        super(source, target);
        this.element = element;
        this.value = value == null ? 0 : value;
    }

    public Element getElement() {
        return element;
    }

    public Integer getValue() {
        return value;
    }
}
