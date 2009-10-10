package com.killard.card;

import java.io.Serializable;

/**
 * <p>
 * This class defines card attack.
 * One Attack belongs an element school. It could be either normal or magic.
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class Attack implements Serializable {

    private final ElementSchool elementSchool;

    private final AttackType type;

    private final int value;

    public Attack(ElementSchool elementSchool, AttackType type, int value) {
        this.elementSchool = elementSchool;
        this.type = type;
        this.value = value;
    }

    public ElementSchool getElementSchool() {
        return elementSchool;
    }

    public AttackType getType() {
        return type;
    }

    public int getValue() {
        return value;
    }
}
