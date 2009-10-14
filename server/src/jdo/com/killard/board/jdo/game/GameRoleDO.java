package com.killard.board.jdo.game;

import com.google.appengine.api.datastore.Key;
import com.killard.board.jdo.AttributeHandler;
import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.board.RoleDO;
import com.killard.board.jdo.board.descriptor.RoleDescriptorDO;
import com.killard.board.jdo.board.property.RolePropertyDO;
import com.killard.board.jdo.game.descriptor.GameRoleDescriptorDO;
import com.killard.board.jdo.game.property.GameRolePropertyDO;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

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
public class GameRoleDO extends DescriptableDO<GameRoleDO, GameRolePropertyDO, GameRoleDescriptorDO> {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    @Extension(vendorName="datanucleus", key="gae.parent-pk", value="true")
    private Key packageKey;

    @Persistent
    private String name;

    @Persistent
    private Boolean visible;

    @Persistent(serialized = "true")
    private List<AttributeHandler> validators;

    @Persistent(serialized = "true")
    private List<AttributeHandler> before;

    @Persistent(serialized = "true")
    private List<AttributeHandler> after;

    @Persistent
    private SortedSet<GameRolePropertyDO> properties;

    @Persistent
    private SortedSet<GameRoleDescriptorDO> descriptors;

    public GameRoleDO(GamePackageDO pack, RoleDO role) {
        this.packageKey = pack.getKey();
        this.name = role.getName();
        this.visible = role.isVisible();

        validators = new ArrayList<AttributeHandler>(Arrays.asList(role.getValidators()));
        before = new ArrayList<AttributeHandler>(Arrays.asList(role.getBefore()));
        after = new ArrayList<AttributeHandler>(Arrays.asList(role.getAfter()));

        this.properties = new TreeSet<GameRolePropertyDO>();
        for (RolePropertyDO property : role.getProperties()) {
            this.properties.add(new GameRolePropertyDO(this, property));
        }

        this.descriptors = new TreeSet<GameRoleDescriptorDO>();
        for (RoleDescriptorDO descriptor : role.getDescriptors()) {
            this.descriptors.add(new GameRoleDescriptorDO(this, descriptor));
        }
    }

    public Key getKey() {
        return key;
    }

    public Key getPackageKey() {
        return packageKey;
    }

    public String getName() {
        return name;
    }

    public GameRolePropertyDO[] getProperties() {
        return properties.toArray(new GameRolePropertyDO[properties.size()]);
    }

    protected boolean addProperty(String name, String data) {
        return false;
    }

    protected boolean removeProperty(GameRolePropertyDO property) {
        return false;
    }

    public boolean isVisible() {
        return visible;
    }

    public List<AttributeHandler> getValidators() {
        return Collections.unmodifiableList(validators);
    }

    public List<AttributeHandler> getBefore() {
        return Collections.unmodifiableList(before);
    }

    public List<AttributeHandler> getAfter() {
        return Collections.unmodifiableList(after);
    }

    public GameRoleDescriptorDO[] getDescriptors() {
        return descriptors.toArray(new GameRoleDescriptorDO[descriptors.size()]);
    }
}
