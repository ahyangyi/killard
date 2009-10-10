package com.killard.web.jdo;

import com.killard.parser.Function;

import java.io.Serializable;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class AttributeHandler implements Serializable {

    private final Class actionClass;

    private final boolean selfTargeted;

    private final Function function;

    public AttributeHandler(Class actionClass, boolean selfTargeted, Function function) {
        this.actionClass = actionClass;
        this.selfTargeted = selfTargeted;
        this.function = function;
    }

    public Class getActionClass() {
        return actionClass;
    }

    public boolean isSelfTargeted() {
        return selfTargeted;
    }

    public Function getFunction() {
        return function;
    }
}
