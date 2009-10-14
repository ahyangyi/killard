package com.killard.board.jdo.game;

import com.google.appengine.api.datastore.Key;
import com.killard.board.jdo.board.RoleGroupDO;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Extension;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

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
public class GameRoleGroupDO implements Comparable<GameRoleGroupDO> {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    @Extension(vendorName="datanucleus", key="gae.parent-pk", value="true")
    private Key packageKey;

    @Persistent
    private List<String> roleNames;

    public GameRoleGroupDO(GamePackageDO gamePackage, RoleGroupDO group) {
        this.packageKey = gamePackage.getKey();
        this.roleNames = new ArrayList<String>(Arrays.asList(group.getRoleNames()));
    }

    public Key getKey() {
        return key;
    }

    public Key getPackageKey() {
        return packageKey;
    }

    public int getRoleAmount() {
        return roleNames.size();
    }

    public String[] getRoleNames() {
        return roleNames.toArray(new String[roleNames.size()]);
    }

    public int compareTo(GameRoleGroupDO compare) {
        return getRoleNames().length - compare.getRoleNames().length;
    }
}
