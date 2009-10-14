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
import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.FunctionHelper;
import com.killard.board.jdo.game.BoardManagerDO;
import com.killard.board.jdo.game.GameRoleDO;
import com.killard.board.jdo.game.descriptor.GameRoleDescriptorDO;
import com.killard.board.jdo.game.player.descriptor.RoleRecordDescriptorDO;
import com.killard.board.jdo.game.player.property.RoleRecordPropertyDO;
import com.killard.board.jdo.game.property.GameRolePropertyDO;

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
public class RoleRecordDO extends DescriptableDO<RoleRecordDO, RoleRecordPropertyDO, RoleRecordDescriptorDO> implements Role<RoleRecordDO> {

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

    @Persistent
    private SortedSet<RoleRecordDescriptorDO> descriptors;

    public RoleRecordDO(PlayerRecordDO player, GameRoleDO role) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(player.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), role.getName());
        this.key = keyBuilder.getKey();

        this.name = role.getName();
        this.visible = role.isVisible();

        this.properties = new TreeSet<RoleRecordPropertyDO>();
        for (GameRolePropertyDO property : role.getProperties()) {
            this.properties.add(new RoleRecordPropertyDO(this, property));
        }

        validators = new ArrayList<AttributeHandler>(role.getValidators());
        before = new ArrayList<AttributeHandler>(role.getBefore());
        after = new ArrayList<AttributeHandler>(role.getAfter());

        this.descriptors = new TreeSet<RoleRecordDescriptorDO>();
        for (GameRoleDescriptorDO descriptor : role.getDescriptors()) {
            this.descriptors.add(new RoleRecordDescriptorDO(this, descriptor));
        }
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

    protected RoleRecordDescriptorDO[] getDescriptors() {
        return descriptors.toArray(new RoleRecordDescriptorDO[descriptors.size()]);
    }

    public boolean addDescriptor(RoleRecordDescriptorDO descriptor) {
        return false;
    }

    public boolean removeDescriptor(RoleRecordDescriptorDO descriptor) {
        return false;
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
