package com.killard.card.action;

import com.killard.card.Action;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public abstract class BasicAction<S, T> implements Action<S, T> {

    private final S source;

    private final T target;

    protected BasicAction(S source, T target) {
        this.source = source;
        this.target = target;
    }

    public S getSource() {
        return source;
    }

    public T getTarget() {
        return target;
    }
}
