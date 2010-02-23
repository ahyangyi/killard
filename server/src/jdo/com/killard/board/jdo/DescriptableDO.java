package com.killard.board.jdo;

import com.google.appengine.api.datastore.Key;
import com.killard.board.jdo.context.BoardContext;

import java.io.Serializable;
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
public abstract class DescriptableDO<S extends DescriptableDO, P extends PropertyDO, T extends DescriptorDO>
        implements Comparable<S>, Serializable {

    protected DescriptableDO() {
    }

    public abstract Key getKey();

    public abstract String getName();

    protected abstract boolean addProperty(String name, String data);

    public abstract P[] getProperties();

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

    protected abstract boolean addDescriptor(String locale, String name, String description);

    public abstract T[] getDescriptors();

    public T newDescriptor(String locale, String name, String description) {
        for (T descriptor : getDescriptors()) if (descriptor.getLocale().equals(locale)) return descriptor;
        addDescriptor(locale, name, description);
        return getDescriptor(locale);
    }
    
    public T newDescriptor(Locale locale, String name, String description) {
        return newDescriptor(locale.toString(), name, description);
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

    public abstract boolean isRenderable();

    public abstract byte[] getImageData();

    public abstract void setImageData(byte[] data);

    public int compareTo(S compare) {
        return getKey().compareTo(compare.getKey());
    }
}
