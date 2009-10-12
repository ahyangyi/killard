package com.killard.jdo.card;

import com.google.appengine.api.datastore.Key;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Extension;
import java.util.List;
import java.util.ArrayList;

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
public class RoleGroupDO implements Comparable<RoleGroupDO> {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    @Extension(vendorName="datanucleus", key="gae.parent-pk", value="true")
    private Key packageKey;

    @Persistent
    private List<Key> roleKeys;

    public RoleGroupDO(PackageDO pack, RoleDO role) {
        this.packageKey = pack.getKey();
        this.roleKeys = new ArrayList<Key>();
        this.roleKeys.add(role.getKey());
    }

    public Key getKey() {
        return key;
    }

    public Key getPackageKey() {
        return packageKey;
    }

    public RoleDO[] getRoles() {
        return roleKeys.toArray(new RoleDO[roleKeys.size()]);
    }

    public int getRoleAmount() {
        return roleKeys.size();
    }

    public boolean addRole(RoleDO role) {
        return roleKeys.add(role.getKey());
    }

    public boolean removeRole(RoleDO role) {
        return roleKeys.remove(role.getKey());
    }

    public int compareTo(RoleGroupDO group) {
        return getRoleAmount() - group.getRoleAmount();
    }
}
