package com.killard.board.jdo.game;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.card.BoardPackage;
import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.PropertyDO;
import com.killard.board.jdo.board.ElementSchoolDO;
import com.killard.board.jdo.board.PackageDO;
import com.killard.board.jdo.board.RoleDO;
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
    private Key packageKey;

    @Persistent
    private String name;

    @Persistent
    private GameRuleDO rule;

    @Persistent
    private List<GameRoleDO> roles;

    @Persistent
    private SortedSet<GameElementSchoolDO> elementSchools;

    @Persistent
    private SortedSet<GamePackageDescriptorDO> descriptors;

    public GamePackageDO(Key boardManagerKey, PackageDO pack, int playerNumber) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(boardManagerKey);
        keyBuilder.addChild(getClass().getSimpleName(), pack.getKey().getId());
        this.key = keyBuilder.getKey();

        this.packageKey = pack.getKey();
        this.rule = new GameRuleDO(this, pack.getRule());

        this.roles = new ArrayList<GameRoleDO>();
        for (RoleDO role : pack.getRoleGroup(playerNumber).getRoles()) this.roles.add(new GameRoleDO(this, role));

        this.elementSchools = new TreeSet<GameElementSchoolDO>();
        for (ElementSchoolDO elementSchool : pack.getElementSchools()) {
            elementSchools.add(new GameElementSchoolDO(this, elementSchool));
        }
        this.name = pack.getName();

        this.descriptors = new TreeSet<GamePackageDescriptorDO>();
        for (PackageDescriptorDO descriptor : pack.getDescriptors()) {
            this.descriptors.add(new GamePackageDescriptorDO(this, descriptor));
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

    public GameRoleDO getRandomRole() {
        return null;
    }

    public GameElementSchoolDO[] getElementSchools() {
        return elementSchools.toArray(new GameElementSchoolDO[elementSchools.size()]);
    }

    protected GamePackageDescriptorDO[] getDescriptors() {
        return descriptors.toArray(new GamePackageDescriptorDO[descriptors.size()]);
    }
}
