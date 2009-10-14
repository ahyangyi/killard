package com.killard.jdo.board.player;

import com.killard.card.Role;
import com.killard.card.Action;
import com.killard.card.Player;
import com.killard.environment.Record;
import com.killard.environment.ActionValidator;
import com.killard.environment.BeforeAction;
import com.killard.environment.AfterAction;
import com.killard.jdo.board.BoardRoleDO;
import com.killard.jdo.board.BoardManagerDO;
import com.killard.jdo.FunctionHelper;
import com.killard.jdo.AttributeHandler;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.IdGeneratorStrategy;
import java.util.List;
import java.util.ArrayList;
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
public class RoleRecordDO implements Role, Record {

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

    @NotPersistent
    private BoardManagerDO boardManager;

    public RoleRecordDO(PlayerRecordDO player, BoardRoleDO role, BoardManagerDO boardManager) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(player.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), role.getName());
        this.key = keyBuilder.getKey();

        this.visible = role.isVisible();

        validators = new ArrayList<AttributeHandler>(role.getValidators());
        before = new ArrayList<AttributeHandler>(role.getBefore());
        after = new ArrayList<AttributeHandler>(role.getAfter());

        this.boardManager = boardManager;
    }

    public Key getKey() {
        return key;
    }

    public boolean isVisible() {
        return visible;
    }

    public void restore(BoardManagerDO boardManager) {
        this.boardManager = boardManager;
    }

    protected void setVisible(Boolean visible) {
        this.visible = visible;
    }

    @ActionValidator(actionClass = Action.class, selfTargeted = false)
    public List<Action> validateAction(Player owner, Action action) {
        List<Action> result = FunctionHelper.handler(boardManager, owner, action, validators);
        getLog().fine("validate " + action.getClass().getSimpleName() + " : " + result);
        return result;
    }

    @BeforeAction(actionClass = Action.class, selfTargeted = false)
    public List<Action> beforeAction(Player owner, Action action) {
        return FunctionHelper.handler(boardManager, owner, action, before);
    }

    @AfterAction(actionClass = Action.class, selfTargeted = false)
    public List<Action> afterAction(Player owner, Action action) {
        return FunctionHelper.handler(boardManager, owner, action, after);
    }

    public Logger getLog() {
        return Logger.getLogger(getClass().getName());
    }
}
