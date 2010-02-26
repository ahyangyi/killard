package com.killard.board.jdo;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.jdo.context.BoardContext;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.io.Serializable;
import java.util.Date;
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
@PersistenceCapable(identityType = IdentityType.APPLICATION)
@Inheritance(strategy= InheritanceStrategy.SUBCLASS_TABLE)
public abstract class DescriptableDO<S extends DescriptableDO, P extends PropertyDO, T extends DescriptorDO>
        implements Comparable<S>, Serializable {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private String name;

    @Persistent
    private Date createdDate;

    @Persistent
    private Date modifiedDate;

    @Persistent(defaultFetchGroup = "false")
    private transient Blob image;

    protected DescriptableDO(String name) {
        this.name = name;
        this.createdDate = new Date();
        this.modifiedDate = this.createdDate;
    }

    protected DescriptableDO(DescriptableDO parent, String name) {
        this(name);
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(parent.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), name);
        this.key = keyBuilder.getKey();
    }

    protected DescriptableDO(Key parentKey, long id) {
        this(parentKey.getName());
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(parentKey);
        keyBuilder.addChild(getClass().getSimpleName(), id);
        this.key = keyBuilder.getKey();
    }

    public Key getKey() {
        return this.key;
    }

    public String getName() {
        return this.name;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    protected void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

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

    public boolean isRenderable() {
        return image != null;
    }

    public byte[] getImageData() {
        return image.getBytes();
    }

    public void setImageData(byte[] data) {
        image = new Blob(data);
        setModifiedDate(new Date());
    }

    public int compareTo(S compare) {
        return key.compareTo(compare.key);
    }
}
