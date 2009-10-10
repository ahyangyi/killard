package com.killard.card.action;

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
public final class KillCardAction extends CardAction<CardInstance> {

    public KillCardAction(CardInstance source, CardInstance target) {
        super(source, target);
    }
}
