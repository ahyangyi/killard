package com.killard.board.card.action;

import com.killard.board.card.Attribute;
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
public final class AddCardAttributeAction extends CardAction<Card> {

    private final Attribute attribute;

    public AddCardAttributeAction(Card source, Card target, Attribute attribute) {
        super(source, target);
        this.attribute = attribute;
    }

    public Attribute getAttribute() {
        return attribute;
    }
}
