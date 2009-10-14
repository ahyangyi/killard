package com.killard.board.jdo;

import com.google.appengine.api.datastore.Key;
import com.killard.board.jdo.context.BoardContext;
import com.killard.board.jdo.PropertyDO;

import java.util.Collections;
import java.util.Locale;
import java.util.SortedSet;

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

    protected abstract SortedSet<T> getDescriptors();

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

    public boolean addDescriptor(T descriptor) {
        return getDescriptors().add(descriptor);
    }

    public boolean removeDescriptor(T descriptor) {
        return getDescriptors().remove(descriptor);
    }

    public T getDescriptor(Locale locale) {
        return getDescriptor(locale.toString());
    }

    public T getDescriptor(String locale) {
        SortedSet<T> descriptors = getDescriptors();
        if (descriptors.isEmpty()) return null;
        for (T descriptor : descriptors) {
            if (descriptor.getLocale().equals(locale)) return descriptor;
        }
        return descriptors.iterator().next();
    }

    public int compareTo(S descriptableDO) {
        return getKey().compareTo(descriptableDO.getKey());
    }

    public SortedSet<T> getAllDescriptors() {
        return Collections.unmodifiableSortedSet(getDescriptors());
    }
}
