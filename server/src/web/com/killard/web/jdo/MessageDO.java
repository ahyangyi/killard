package com.killard.web.jdo;

import com.google.appengine.api.datastore.Key;

import javax.jdo.annotations.Extension;
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
public class MessageDO {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    @Extension(vendorName="datanucleus", key="gae.parent-pk", value="true")
    private Key boardManagerKey;

    @Persistent
    private String from;

    @Persistent
    private String to;

    @Persistent
    private String message;

    public MessageDO(BoardManagerDO boardManager, String from, String to, String message) {
        this.boardManagerKey = boardManager.getKey();
        this.from = from;
        this.to = to;
        this.message = message;
    }

    public Key getBoardManagerKey() {
        return boardManagerKey;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getMessage() {
        return message;
    }
}
