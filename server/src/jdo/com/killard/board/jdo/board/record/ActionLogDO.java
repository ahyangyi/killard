package com.killard.board.jdo.board.record;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.card.Action;
import com.killard.board.card.Card;
import com.killard.board.card.Player;
import com.killard.board.jdo.board.ActionLoggerFactory;
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
public class ActionLogDO {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private Key boardKey;

    @Persistent
    private String actionClass;

    @Persistent
    private String log;

    @Persistent
    private String targetPlayerId;

    public ActionLogDO(BoardDO board, Action action) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(board.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), board.getActions().size() + 1);
        this.key = keyBuilder.getKey();
        this.boardKey = board.getKey();
        this.actionClass = action.getClass().getSimpleName();
        this.log = ActionLoggerFactory.getActionLogger(action).log(action);
        if (action.getTarget() instanceof Player) {
            targetPlayerId = ((Player)action.getTarget()).getId();
        } else if (action.getTarget() instanceof Card) {
            targetPlayerId = ((Card)action.getTarget()).getOwner().getId();
        } else {
            targetPlayerId = null;
        }
    }

    public Key getKey() {
        return key;
    }

    public Key getBoardKey() {
        return boardKey;
    }

    public String getAction() {
        return actionClass;
    }

    public String getLog() {
        return log;
    }

    public String getTargetPlayerId() {
        return targetPlayerId;
    }
}
