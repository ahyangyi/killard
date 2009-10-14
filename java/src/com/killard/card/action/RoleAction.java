package com.killard.card.action;

import com.killard.card.Role;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public abstract class RoleAction <S> extends BasicAction<S, Role> {

    protected RoleAction(S source, Role target) {
        super(source, target);
    }
}
