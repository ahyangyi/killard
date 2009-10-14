package com.killard.board.card.action;

import com.killard.board.card.Attribute;
import com.killard.board.card.CardInstance;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public final class AddCardAttributeAction extends CardAction<CardInstance> {

    private final Attribute attribute;

    public AddCardAttributeAction(CardInstance source, CardInstance target, Attribute attribute) {
        super(source, target);
        this.attribute = attribute;
    }

    public Attribute getAttribute() {
        return attribute;
    }
}
