package com.killard.jdo;

import com.google.appengine.api.datastore.Key;
import com.killard.web.context.BoardContext;

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
public abstract class DescriptableDO<T extends DescriptorDO> implements Comparable<DescriptableDO> {

    protected DescriptableDO() {
    }

    public abstract Key getKey();

    public abstract String getId();

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

    public int compareTo(DescriptableDO descriptableDO) {
        return getKey().compareTo(descriptableDO.getKey());
    }

    protected abstract SortedSet<T> getDescriptors();
}
