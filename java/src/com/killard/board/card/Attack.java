package com.killard.board.card;

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
public class Attack {

    private final Element element;

    private final AttackType type;

    private final int value;

    private final int range;

    public Attack(Element element, AttackType type, int value) {
        this.element = element;
        this.type = type;
        this.value = value;
        this.range = 1;
    }

    public Attack(Element element, AttackType type, int value, int range) {
        this.element = element;
        this.type = type;
        this.value = value;
        this.range = range;
    }

    public Element getElement() {
        return element;
    }

    public AttackType getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public int getRange() {
        return range;
    }
}
