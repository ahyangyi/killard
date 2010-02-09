package com.killard.board.jdo.board.record;

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
public class AudienceRecordDO {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private Key boardManagerKey;

    @Persistent
    private String uid;

    @Persistent
    private String nickname;

    public AudienceRecordDO(BoardDO board, String uid, String nickname) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(board.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), uid);
        this.key = keyBuilder.getKey();
        this.boardManagerKey = board.getKey();
        this.uid = uid;
        this.nickname = nickname;
    }

    public Key getKey() {
        return key;
    }

    public Key getBoardManagerKey() {
        return boardManagerKey;
    }

    public String getUid() {
        return uid;
    }

    public String getNickname() {
        return nickname;
    }
}
