package com.killard.web.jdo;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;

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
public class ProfileDO {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private User user;

    @Persistent
    private Long facebookUID;

    @Persistent
    private Key boardManagerKey;

    @Persistent
    private Key playerRecordKey;

    public ProfileDO(User user) {
        this.user = user;
    }

    public Key getKey() {
        return key;
    }

    public User getUser() {
        return user;
    }

    public Long getFacebookUID() {
        return facebookUID;
    }

    public void setFacebookUID(Long facebookUID) {
        this.facebookUID = facebookUID;
    }

    public Key getBoardManagerKey() {
        return boardManagerKey;
    }

    public void setBoardManagerKey(Key boardManagerKey) {
        this.boardManagerKey = boardManagerKey;
    }

    public Key getPlayerRecordKey() {
        return playerRecordKey;
    }

    public void setPlayerRecordKey(Key playerRecordKey) {
        this.playerRecordKey = playerRecordKey;
    }
}
