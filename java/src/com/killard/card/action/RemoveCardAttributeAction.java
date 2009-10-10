package com.killard.card.action;

import com.killard.card.Attribute;
import com.killard.card.CardInstance;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class RemoveCardAttributeAction extends CardAction<CardInstance> {

    private final Attribute attribute;

    public RemoveCardAttributeAction(CardInstance source, CardInstance target, Attribute attribute) {
        super(source, target);
        this.attribute = attribute;
    }

    public Attribute getAttribute() {
        return attribute;
    }
}
