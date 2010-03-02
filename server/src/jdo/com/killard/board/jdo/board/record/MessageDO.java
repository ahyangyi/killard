package com.killard.board.jdo.board.record;

import com.google.appengine.api.datastore.Key;
import com.killard.board.jdo.board.BoardDO;

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
    private Key boardKey;

    @Persistent
    private String from;

    @Persistent
    private String to;

    @Persistent
    private String message;

    public MessageDO(BoardDO board, String from, String to, String message) {
        this.boardKey = board.getKey();
        this.from = from;
        this.to = to;
        this.message = message;
    }

    public Key getBoardKey() {
        return boardKey;
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
