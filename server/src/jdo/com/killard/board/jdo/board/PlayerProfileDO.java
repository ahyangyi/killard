package com.killard.board.jdo.board;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Rating;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.HashSet;
import java.util.Set;

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
    private Key bundleKey;

    @Persistent
    private String name;

    @Persistent
    private Set<String> identities;

    @Persistent
    private boolean creator;

    @Persistent
    private boolean editor;

    @Persistent
    private Rating rating;

    public PlayerProfileDO(PackageBundleDO bundle, String id, String name) {
        this.bundleKey = bundle.getKey();
        this.name = name;
        this.identities = new HashSet<String>();
        this.identities.add(id);
        this.rating = new Rating(0);
    }

    public Key getKey() {
        return key;
    }

    public Key getBundleKey() {
        return bundleKey;
    }

    public String getName() {
        return name;
    }

    public String[] getIdentities() {
        return identities.toArray(new String[identities.size()]);
    }

    public boolean isCreator() {
        return creator;
    }

    public boolean isEditor() {
        return editor;
    }

    public Rating getRating() {
        return rating;
    }

    public boolean addIdentity(String id) {
        return identities.add(id);
    }

    public boolean removeIdentity(String id) {
        return identities.remove(id);
    }

    public void setCreator(boolean creator) {
        this.creator = creator;
    }

    public void setEditor(boolean editor) {
        this.editor = editor;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }
}
