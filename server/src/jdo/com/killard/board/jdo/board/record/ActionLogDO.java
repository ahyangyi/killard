package com.killard.board.jdo.board.record;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.card.Action;
import com.killard.board.jdo.board.ActionLoggerFactory;
import com.killard.board.jdo.board.BoardDO;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.Date;

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
public class ActionLogDO {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private Key boardKey;

    @Persistent
    private String action;

    @Persistent
    private String log;

    @Persistent
    private Date time;

    public ActionLogDO(BoardDO board, Action action) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(board.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), board.getActions().length + 1);
        this.key = keyBuilder.getKey();
        this.boardKey = board.getKey();
        this.action = action.getClass().getSimpleName();
        this.log = ActionLoggerFactory.getActionLogger(action).log(action);
        this.time = new Date();
    }

    public Key getKey() {
        return key;
    }

    public Key getBoardKey() {
        return boardKey;
    }

    public String getAction() {
        return action;
    }

    public String getLog() {
        return log;
    }

    public Date getTime() {
        return time;
    }

}
