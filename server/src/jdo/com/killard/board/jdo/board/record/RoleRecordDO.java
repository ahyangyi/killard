package com.killard.board.jdo.board.record;

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
import com.killard.board.jdo.board.RoleDO;
import com.killard.board.jdo.board.BoardDO;
import com.killard.board.jdo.board.record.property.RoleRecordPropertyDO;
import com.killard.board.jdo.board.property.RolePropertyDO;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
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
    private String name;

    @Persistent
    private Boolean visible;

    @Persistent
    private SortedSet<RoleRecordPropertyDO> properties;

    @Persistent(serialized = "true")
    private List<AttributeHandler> validators;

    @Persistent(serialized = "true")
    private List<AttributeHandler> before;

    @Persistent(serialized = "true")
    private List<AttributeHandler> after;

    public RoleRecordDO(PlayerRecordDO player, RoleDO role) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(player.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), role.getName());
        this.key = keyBuilder.getKey();

        this.name = role.getName();
        this.visible = role.isVisible();

        this.properties = new TreeSet<RoleRecordPropertyDO>();
        for (RolePropertyDO property : role.getProperties()) {
            this.properties.add(new RoleRecordPropertyDO(this, property));
        }

        validators = new ArrayList<AttributeHandler>(role.getValidators());
        before = new ArrayList<AttributeHandler>(role.getBefore());
        after = new ArrayList<AttributeHandler>(role.getAfter());
    }

    public Key getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public RoleRecordPropertyDO[] getProperties() {
        return properties.toArray(new RoleRecordPropertyDO[properties.size()]);
    }

    protected boolean addProperty(String name, String data) {
        return false;
    }

    protected boolean removeProperty(RoleRecordPropertyDO property) {
        return false;
    }

    public boolean isVisible() {
        return visible;
    }

    protected void setVisible(Boolean visible) {
        this.visible = visible;
    }

    @ActionValidator(actionClass = Action.class, selfTargeted = false)
    public List<Action> validateAction(BoardDO board, Player owner, Action action) {
        List<Action> result = FunctionHelper.handler(board, owner, action, validators);
        getLog().fine("validate " + action.getClass().getSimpleName() + " : " + result);
        return result;
    }

    @BeforeAction(actionClass = Action.class, selfTargeted = false)
    public List<Action> beforeAction(BoardDO board, Player owner, Action action) {
        return FunctionHelper.handler(board, owner, action, before);
    }

    @AfterAction(actionClass = Action.class, selfTargeted = false)
    public List<Action> afterAction(BoardDO board, Player owner, Action action) {
        return FunctionHelper.handler(board, owner, action, after);
    }

    public Logger getLog() {
        return Logger.getLogger(getClass().getName());
    }

    public int compareTo(RoleRecordDO compare) {
        return getKey().compareTo(compare.getKey());
    }

    public Object getProperty(String name) {
        return null;
    }
}
