package com.killard.board.jdo.game;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.jdo.board.ElementSchoolDO;
import com.killard.board.jdo.board.PackageDO;
import com.killard.board.jdo.board.RoleDO;
import com.killard.board.jdo.board.descriptor.PackageDescriptorDO;
import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.game.descriptor.BoardPackageDescriptorDO;
import com.killard.board.card.BoardPackage;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

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
public class BoardPackageDO extends DescriptableDO<BoardPackageDescriptorDO> implements BoardPackage {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private Key packageKey;

    @Persistent
    private String name;

    @Persistent
    private BoardRuleDO rule;

    @Persistent
    private List<BoardRoleDO> roles;

    @Persistent
    private SortedSet<BoardElementSchoolDO> elementSchools;

    @Persistent
    private SortedSet<BoardPackageDescriptorDO> descriptors;

    public BoardPackageDO(Key boardManagerKey, PackageDO pack, int playerNumber) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(boardManagerKey);
        keyBuilder.addChild(getClass().getSimpleName(), pack.getKey().getId());
        this.key = keyBuilder.getKey();

        this.packageKey = pack.getKey();
        this.rule = new BoardRuleDO(this, pack.getRule());

        this.roles = new ArrayList<BoardRoleDO>();
        for (RoleDO role : pack.getRoleGroup(playerNumber).getRoles()) this.roles.add(new BoardRoleDO(this, role));

        this.elementSchools = new TreeSet<BoardElementSchoolDO>();
        for (ElementSchoolDO elementSchool : pack.getElementSchools()) {
            elementSchools.add(new BoardElementSchoolDO(this, elementSchool));
        }
        this.name = pack.getName();

        this.descriptors = new TreeSet<BoardPackageDescriptorDO>();
        for (PackageDescriptorDO descriptor : pack.getAllDescriptors()) {
            this.descriptors.add(new BoardPackageDescriptorDO(this, descriptor));
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

    public BoardRuleDO getRule() {
        return rule;
    }

    public SortedMap<String, BoardRoleDO> getRoles() {
        SortedMap<String, BoardRoleDO> map = new TreeMap<String, BoardRoleDO>();
        for (BoardRoleDO role : roles) {
            map.put(role.getName(), role);
        }
        return map;
    }

    public BoardRoleDO getRandomRole() {
        return null;
    }

    public BoardElementSchoolDO[] getElementSchools() {
        return elementSchools.toArray(new BoardElementSchoolDO[elementSchools.size()]);
    }

    protected SortedSet<BoardPackageDescriptorDO> getDescriptors() {
        return descriptors;
    }
}
