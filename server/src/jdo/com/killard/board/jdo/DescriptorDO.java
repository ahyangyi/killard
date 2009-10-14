package com.killard.board.jdo;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
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
@Inheritance(strategy= InheritanceStrategy.NEW_TABLE)
public abstract class DescriptorDO implements Comparable<DescriptorDO> {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    @Extension(vendorName="datanucleus", key="gae.parent-pk", value="true")
    private Key ownerKey;

    @Persistent
    private String locale;

    @Persistent
    private String name;

    @Persistent(defaultFetchGroup = "false")
    private Text description;

    @Persistent(defaultFetchGroup = "false")
    private Blob image;

    protected DescriptorDO(DescriptableDO owner, String locale) {
        this.locale = locale;
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(owner.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), this.locale);
        this.key = keyBuilder.getKey();
    }

    protected DescriptorDO(DescriptableDO owner, Locale locale) {
        this.locale = locale.toString();
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(owner.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), this.locale);
        this.key = keyBuilder.getKey();
    }

    protected DescriptorDO(DescriptableDO owner, DescriptorDO origin) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(owner.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), origin.getLocale());
        this.key = keyBuilder.getKey();

        this.locale = origin.getLocale();
        this.name = origin.getName();
        this.description = new Text(origin.getDescription());
        this.image = origin.getImageData() == null ? null : new Blob(origin.getImageData());
    }

    public Key getKey() {
        return key;
    }

    public Key getOwnerKey() {
        return ownerKey;
    }

    public String getLocale() {
        return locale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        if (description == null) return "";
        return description.getValue();
    }

    public void setDescription(String description) {
        this.description = new Text(description);
    }

    public byte[] getImageData() {
        return image == null ? null : image.getBytes();
    }

    public void setImageData(byte[] image) {
        this.image = new Blob(image);
    }

    public int compareTo(DescriptorDO descriptorDO) {
        return getKey().compareTo(descriptorDO.getKey());
    }
}