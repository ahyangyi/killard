package com.killard.board.jdo.board.game.property;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.PropertyDO;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.IdGeneratorStrategy;

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
public class RecordPropertyDO implements Comparable<RecordPropertyDO> {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private String name;

    @Persistent
    private String data;

    protected RecordPropertyDO(Key ownerKey, String name, String data) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(ownerKey);
        keyBuilder.addChild(getClass().getSimpleName(), name);
        this.key = keyBuilder.getKey();
        this.name = name;
        this.data = data;
    }

    protected RecordPropertyDO(Key ownerKey, PropertyDO origin) {
        this(ownerKey, origin.getName(), origin.getData());
    }

    public Key getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data =  data;
    }

    public Object getValue() {
        String data = getData();
        if (data.equals("true")) return Boolean.TRUE;
        if (data.equals("false")) return Boolean.FALSE;
        if (data.matches("\\d+")) return Integer.parseInt(data);
        return data;
    }

    public int compareTo(RecordPropertyDO compare) {
        return getKey().compareTo(compare.getKey());
    }
}
