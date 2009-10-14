package com.killard.board.jdo.card;

import com.google.appengine.api.datastore.Key;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.NotPersistent;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

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
    private List<String> roleNames;

    @NotPersistent
    private List<RoleDO> roles;

    public RoleGroupDO(PackageDO pack, RoleDO role) {
        this.packageKey = pack.getKey();
        this.roleNames = new ArrayList<String>();
        this.roleNames.add(role.getName());
        this.roles = new ArrayList<RoleDO>();
        this.roles.add(role);
    }

    public Key getKey() {
        return key;
    }

    public Key getPackageKey() {
        return packageKey;
    }

    public RoleDO[] getRoles() {
        return roles.toArray(new RoleDO[roles.size()]);
    }

    public int getRoleAmount() {
        return roleNames.size();
    }

    public boolean addRole(RoleDO role) {
        roles.add(role);
        return roleNames.add(role.getName());
    }

    public boolean removeRole(RoleDO role) {
        roles.remove(role);
        return roleNames.remove(role.getName());
    }

    public int compareTo(RoleGroupDO group) {
        return getRoleAmount() - group.getRoleAmount();
    }

    protected void restore(Set<RoleDO> roles) {
        List<String> invalid = new ArrayList<String>();
        Map<String, RoleDO> rolesMap = new HashMap<String, RoleDO>(roles.size());
        for (RoleDO role : roles) {
            rolesMap.put(role.getName(), role);
        }
        for (String name : roleNames) {
            if (!rolesMap.containsKey(name)) invalid.add(name);
        }
        roleNames.removeAll(invalid);
        for (String name : roleNames) {
            this.roles.add(rolesMap.get(name));
        }
    }
}
