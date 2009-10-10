package com.killard.web.jdo;

import com.google.appengine.api.datastore.Key;
import com.killard.web.context.BoardContext;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
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
@PersistenceCapable(identityType = IdentityType.APPLICATION)
@Inheritance(strategy= InheritanceStrategy.NEW_TABLE)
public abstract class DescriptableDO<T extends DescriptorDO> implements Comparable<DescriptableDO> {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private String id;

    protected DescriptableDO(String id) {
        this.id = id;
    }

    public Key getKey() {
        return key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int compareTo(DescriptableDO descriptableDO) {
        return getKey().compareTo(descriptableDO.getKey());
    }

    protected abstract SortedSet<T> getDescriptors();
}
