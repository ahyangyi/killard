package com.killard.board.jdo.game;

import com.google.appengine.api.datastore.Key;
import com.killard.board.jdo.AttributeHandler;
import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.game.descriptor.GameRoleDescriptorDO;
import com.killard.board.jdo.board.RoleDO;
import com.killard.board.jdo.board.descriptor.RoleDescriptorDO;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Extension;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Collections;

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
public class GameRoleDO extends DescriptableDO<GameRoleDescriptorDO> {

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
    private SortedSet<GameRoleDescriptorDO> descriptors;

    public GameRoleDO(GamePackageDO pack, RoleDO role) {
        this.packageKey = pack.getKey();
        this.name = role.getName();
        this.visible = role.isVisible();

        validators = new ArrayList<AttributeHandler>(Arrays.asList(role.getValidators()));
        before = new ArrayList<AttributeHandler>(Arrays.asList(role.getBefore()));
        after = new ArrayList<AttributeHandler>(Arrays.asList(role.getAfter()));

        this.descriptors = new TreeSet<GameRoleDescriptorDO>();
        for (RoleDescriptorDO descriptor : role.getAllDescriptors()) {
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

    protected SortedSet<GameRoleDescriptorDO> getDescriptors() {
        return descriptors;
    }
}
