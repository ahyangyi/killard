package com.killard.board.jdo.board;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Rating;
import com.google.appengine.api.datastore.KeyFactory;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.IdentityType;
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
public class PlayerProfileDO {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private String name;

    @Persistent
    private Rating rating;

    public PlayerProfileDO(PackageBundleDO packageBundle, String id, String name) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(packageBundle.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), id);
        this.key = keyBuilder.getKey();
        this.name = name;
        this.rating = new Rating(0);
    }

    public Key getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public Rating getRating() {
        return rating;
    }
}
