package com.killard.board.jdo.board.property;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.jdo.board.BoardDO;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

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
public class BoardPropertyDO implements Comparable<BoardPropertyDO> {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private String name;

    @Persistent
    private String data;

    public BoardPropertyDO(BoardDO owner, String name, String data) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(owner.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), name);
        this.key = keyBuilder.getKey();
        this.name = name;
        this.data = data;
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

    public int compareTo(BoardPropertyDO compare) {
        return key.compareTo(compare.key);
    }

}
