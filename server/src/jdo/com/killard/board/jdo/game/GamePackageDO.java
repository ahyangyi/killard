package com.killard.board.jdo.game;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.card.BoardPackage;
import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.PropertyDO;
import com.killard.board.jdo.board.ElementSchoolDO;
import com.killard.board.jdo.board.PackageDO;
import com.killard.board.jdo.board.RoleDO;
import com.killard.board.jdo.board.RoleGroupDO;
import com.killard.board.jdo.board.descriptor.PackageDescriptorDO;
import com.killard.board.jdo.game.descriptor.GamePackageDescriptorDO;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
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
public class GamePackageDO extends DescriptableDO<GamePackageDO, PropertyDO, GamePackageDescriptorDO> implements BoardPackage {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private Long packageId;

    @Persistent
    private String name;

    @Persistent
    private GameRuleDO rule;

    @Persistent
    private List<GameRoleDO> roles;

    @Persistent
    private SortedSet<GameRoleGroupDO> roleGroups;

    @Persistent
    private SortedSet<GameElementSchoolDO> elementSchools;

    @Persistent
    private SortedSet<GamePackageDescriptorDO> descriptors;

    @Persistent(defaultFetchGroup = "false")
    private SortedSet<BoardDO> boards;

    public GamePackageDO(PackageDO pack, int version) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(pack.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), version);
        this.key = keyBuilder.getKey();

        this.roles = new ArrayList<GameRoleDO>();
        this.roleGroups = new TreeSet<GameRoleGroupDO>();
        this.elementSchools = new TreeSet<GameElementSchoolDO>();
        this.descriptors = new TreeSet<GamePackageDescriptorDO>();
        this.boards = new TreeSet<BoardDO>();

        this.packageId = pack.getKey().getId();
        this.rule = new GameRuleDO(this, pack.getRule());

        for (RoleDO role : pack.getRoles()) this.roles.add(new GameRoleDO(this, role));

        for (RoleGroupDO group : pack.getRoleGroups()) {
            this.roleGroups.add(new GameRoleGroupDO(this, group));
        }

        for (ElementSchoolDO elementSchool : pack.getElementSchools()) {
            this.elementSchools.add(new GameElementSchoolDO(this, elementSchool));
        }
        this.name = pack.getName();

        for (PackageDescriptorDO descriptor : pack.getDescriptors()) {
            this.descriptors.add(new GamePackageDescriptorDO(this, descriptor));
        }
    }

    public Key getKey() {
        return key;
    }

    public long getPackageId() {
        return packageId;
    }

    public String getName() {
        return name;
    }

    public PropertyDO[] getProperties() {
        return new PropertyDO[0];
    }

    protected boolean addProperty(String name, String data) {
        return false;
    }

    protected boolean removeProperty(PropertyDO property) {
        return false;
    }

    public GameRuleDO getRule() {
        return rule;
    }

    public SortedMap<String, GameRoleDO> getRoles() {
        SortedMap<String, GameRoleDO> map = new TreeMap<String, GameRoleDO>();
        for (GameRoleDO role : roles) {
            map.put(role.getName(), role);
        }
        return map;
    }

    public GameRoleGroupDO[] getRoleGroups() {
        return roleGroups.toArray(new GameRoleGroupDO[roleGroups.size()]);
    }

    public GameRoleGroupDO getRoleGroup(int playerNumber) {
        for (GameRoleGroupDO group : roleGroups) {
            if (group.getRoleAmount() == playerNumber) return group;
        }
        return roleGroups.iterator().next();
    }

    public GameElementSchoolDO[] getElementSchools() {
        return elementSchools.toArray(new GameElementSchoolDO[elementSchools.size()]);
    }

    public GamePackageDescriptorDO[] getDescriptors() {
        return descriptors.toArray(new GamePackageDescriptorDO[descriptors.size()]);
    }

    public BoardDO[] getBoards() {
        return boards.toArray(new BoardDO[boards.size()]);
    }
}
