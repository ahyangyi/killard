package com.killard.board.jdo;

import com.google.appengine.api.datastore.Key;
import com.killard.board.jdo.context.BoardContext;

import java.util.Locale;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public abstract class DescriptableDO<S extends DescriptableDO, P extends PropertyDO, T extends DescriptorDO> {

    protected DescriptableDO() {
    }

    public abstract Key getKey();

    public abstract String getName();

    public abstract P[] getProperties();

    protected abstract boolean addProperty(String name, String data);

    protected abstract boolean removeProperty(P property);

    public abstract T[] getDescriptors();

    public Object getProperty(String name) {
        for (P property : getProperties()) {
            if (property.getName().equals(name)) return property.getData();
        }
        return null;
    }

    public void setProperty(String name, String data) {
        for (P property : getProperties()) {
            if (property.getName().equals(name)) {
                property.setData(data);
                return;
            }
        }
        addProperty(name, data);
    }

    public boolean removeProperty(String name) {
        for (P property : getProperties()) {
            if (property.getName().equals(name)) return removeProperty(property);
        }
        return false;
    }

    public T getDescriptor() {
        return getDescriptor(BoardContext.getLocale());
    }

    public T getDescriptor(Locale locale) {
        return getDescriptor(locale.toString());
    }

    public T getDescriptor(String locale) {
        T[] descriptors = getDescriptors();
        if (descriptors.length == 0) return null;
        for (T descriptor : descriptors) {
            if (descriptor.getLocale().equals(locale)) return descriptor;
        }
        return descriptors[0];
    }

    public int compareTo(S descriptableDO) {
        return getKey().compareTo(descriptableDO.getKey());
    }
}
