package com.killard.card.action;

import com.killard.card.ElementSchool;
import com.killard.card.Player;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public final class ChangePlayerElementAction extends PlayerAction<Object> {

    private final ElementSchool elementSchool;

    private final Integer value;

    public ChangePlayerElementAction(Object source, Player target, ElementSchool elementSchool, Integer value) {
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
