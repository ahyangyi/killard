package com.killard.board.jdo;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;

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
@Inheritance(strategy= InheritanceStrategy.SUBCLASS_TABLE)
public abstract class DescriptorDO implements Comparable<DescriptorDO> {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private String locale;

    @Persistent
    private String name;

    @Persistent
    private Text description;

    protected DescriptorDO(DescriptableDO owner, String locale, String name, String description) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(owner.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), locale);
        this.key = keyBuilder.getKey();

        this.locale = locale;
        this.name = name;
        this.description = new Text(description);
    }

    protected DescriptorDO(DescriptableDO owner, Locale locale, String name, String description) {
        this(owner, locale.toString(), name, description);
    }

    protected DescriptorDO(DescriptableDO owner, DescriptorDO origin) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(owner.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), origin.getLocale());
        this.key = keyBuilder.getKey();

        this.locale = origin.getLocale();
        this.name = origin.getName();
        this.description = new Text(origin.getDescription());
    }

    public Key getKey() {
        return key;
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
        return description.getValue();
    }

    public void setDescription(String description) {
        this.description = new Text(description);
    }

    public int compareTo(DescriptorDO compare) {
        return key.compareTo(compare.key);
    }
}
