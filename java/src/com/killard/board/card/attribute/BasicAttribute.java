package com.killard.board.card.attribute;

import com.killard.board.card.Action;
import com.killard.board.card.Attribute;
import com.killard.board.card.Card;
import com.killard.board.card.ElementSchool;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * <p>
 * This class defines basic base for all card attributes.
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public abstract class BasicAttribute<T extends BasicAttribute> implements Attribute<T> {

    private final ElementSchool elementSchool;
    
    private final boolean visible;

    private final Map<String, Object> properties = new HashMap<String, Object>();

    protected BasicAttribute(ElementSchool elementSchool) {
        this.elementSchool = elementSchool;
        visible = false;
    }

    protected BasicAttribute(ElementSchool elementSchool, boolean visible) {
        this.elementSchool = elementSchool;
        this.visible = visible;
    }

    protected BasicAttribute(ElementSchool elementSchool, boolean visible, boolean useful) {
        this.elementSchool = elementSchool;
        this.visible = visible;
    }

    protected BasicAttribute(ElementSchool elementSchool, boolean visible, boolean useful, boolean harmful) {
        this.elementSchool = elementSchool;
        this.visible = visible;
    }

    public String getName() {
        return getClass().getSimpleName();
    }

    public ElementSchool getElementSchool() {
        return elementSchool;
    }

    public boolean isVisible() {
        return visible;
    }

    public Object getProperty(String name) {
        return properties.get(name);
    }
}
