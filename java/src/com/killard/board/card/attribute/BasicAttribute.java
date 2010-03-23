package com.killard.board.card.attribute;

import com.killard.board.card.Attribute;
import com.killard.board.card.Element;

import java.util.HashMap;
import java.util.Map;

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

    private final Element element;
    
    private final boolean visible;

    private final Map<String, Object> properties = new HashMap<String, Object>();

    protected BasicAttribute(Element element) {
        this.element = element;
        visible = false;
    }

    protected BasicAttribute(Element element, boolean visible) {
        this.element = element;
        this.visible = visible;
    }

    protected BasicAttribute(Element element, boolean visible, boolean useful) {
        this.element = element;
        this.visible = visible;
    }

    protected BasicAttribute(Element element, boolean visible, boolean useful, boolean harmful) {
        this.element = element;
        this.visible = visible;
    }

    public String getName() {
        return getClass().getSimpleName();
    }

    public Element getElement() {
        return element;
    }

    public boolean isVisible() {
        return visible;
    }

    public Object getProperty(String name) {
        return properties.get(name);
    }
}
