package com.killard.board.jdo.board;

import com.google.appengine.api.datastore.Key;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
public class RoleGroupDO implements Comparable<RoleGroupDO>, Serializable {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    @Extension(vendorName="datanucleus", key="gae.parent-pk", value="true")
    private Key packageKey;

    @Persistent
    private List<Key> roleKeys;

    @NotPersistent
    private List<RoleDO> roles;

    protected RoleGroupDO(PackageDO pack) {
        this.packageKey = pack.getKey();
        this.roleKeys = new LinkedList<Key>();
        this.roles = new LinkedList<RoleDO>();
    }

    protected RoleGroupDO(PackageDO pack, RoleGroupDO source) {
        this(pack);
    }

    public Key getKey() {
        return key;
    }

    public Key getPackageKey() {
        return packageKey;
    }

    public Key[] getRoleKeys() {
        return roleKeys.toArray(new Key[roleKeys.size()]);
    }

    public RoleDO[] getRoles() {
        return roles.toArray(new RoleDO[roles.size()]);
    }

    public int getRoleAmount() {
        return roleKeys.size();
    }

    public boolean addRole(RoleDO role) {
        roles.add(role);
        if (roleKeys == null) roleKeys = new LinkedList<Key>();
        return roleKeys.add(role.getKey());
    }

    public boolean removeRole(RoleDO role) {
        roles.remove(role);
        return roleKeys.remove(role.getKey());
    }

    public int compareTo(RoleGroupDO group) {
        return getRoleAmount() - group.getRoleAmount();
    }

    protected void restore(Set<RoleDO> roles) {
        List<Key> invalid = new ArrayList<Key>();
        Map<Key, RoleDO> rolesMap = new HashMap<Key, RoleDO>(roles.size());
        for (RoleDO role : roles) {
            rolesMap.put(role.getKey(), role);
        }
        for (Key key : roleKeys) {
            if (!rolesMap.containsKey(key)) invalid.add(key);
        }
        roleKeys.removeAll(invalid);
        this.roles = new LinkedList<RoleDO>();
        for (Key key : roleKeys) {
            this.roles.add(rolesMap.get(key));
        }
    }
}
