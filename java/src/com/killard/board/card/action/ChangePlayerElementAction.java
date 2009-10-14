package com.killard.board.card.action;

import com.killard.board.card.ElementSchool;
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

    private final ElementSchool elementSchool;

    private final Integer value;

    public ChangePlayerElementAction(Record source, Player target, ElementSchool elementSchool, Integer value) {
        super(source, target);
        this.elementSchool = elementSchool;
        this.value = value;
    }

    public ElementSchool getElementSchool() {
        return elementSchool;
    }

    public Integer getValue() {
        return value;
    }
}
