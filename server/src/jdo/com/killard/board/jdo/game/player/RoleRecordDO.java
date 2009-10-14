package com.killard.board.jdo.game.player;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.card.Action;
import com.killard.board.card.Player;
import com.killard.board.card.Role;
import com.killard.board.environment.ActionValidator;
import com.killard.board.environment.AfterAction;
import com.killard.board.environment.BeforeAction;
import com.killard.board.jdo.AttributeHandler;
import com.killard.board.jdo.FunctionHelper;
import com.killard.board.jdo.game.BoardManagerDO;
import com.killard.board.jdo.game.GameRoleDO;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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
public class RoleRecordDO implements Role<RoleRecordDO> {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private Boolean visible;

    @Persistent(serialized = "true")
    private List<AttributeHandler> validators;

    @Persistent(serialized = "true")
    private List<AttributeHandler> before;

    @Persistent(serialized = "true")
    private List<AttributeHandler> after;

    public RoleRecordDO(PlayerRecordDO player, GameRoleDO role, BoardManagerDO boardManager) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(player.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), role.getName());
        this.key = keyBuilder.getKey();

        this.visible = role.isVisible();

        validators = new ArrayList<AttributeHandler>(role.getValidators());
        before = new ArrayList<AttributeHandler>(role.getBefore());
        after = new ArrayList<AttributeHandler>(role.getAfter());
    }

    public Key getKey() {
        return key;
    }

    public boolean isVisible() {
        return visible;
    }

    protected void setVisible(Boolean visible) {
        this.visible = visible;
    }

    @ActionValidator(actionClass = Action.class, selfTargeted = false)
    public List<Action> validateAction(BoardManagerDO board, Player owner, Action action) {
        List<Action> result = FunctionHelper.handler(board, owner, action, validators);
        getLog().fine("validate " + action.getClass().getSimpleName() + " : " + result);
        return result;
    }

    @BeforeAction(actionClass = Action.class, selfTargeted = false)
    public List<Action> beforeAction(BoardManagerDO board, Player owner, Action action) {
        return FunctionHelper.handler(board, owner, action, before);
    }

    @AfterAction(actionClass = Action.class, selfTargeted = false)
    public List<Action> afterAction(BoardManagerDO board, Player owner, Action action) {
        return FunctionHelper.handler(board, owner, action, after);
    }

    public Logger getLog() {
        return Logger.getLogger(getClass().getName());
    }

    public int compareTo(RoleRecordDO roleRecordDO) {
        return (int) (getKey().getId() - roleRecordDO.getKey().getId());
    }
}
