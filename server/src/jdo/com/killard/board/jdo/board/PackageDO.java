package com.killard.board.jdo.board;

import com.google.appengine.api.datastore.Key;
import com.killard.board.card.BoardPackage;
import com.killard.board.card.ElementSchool;
import com.killard.board.jdo.AttributeHandler;
import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.PropertyDO;
import com.killard.board.jdo.board.descriptor.PackageDescriptorDO;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class PackageDO extends DescriptableDO<PackageDO, PropertyDO, PackageDescriptorDO> implements BoardPackage {

    @Persistent
    private Key bundleKey;

    @Persistent(dependent = "true", defaultFetchGroup = "true")
    private RuleDO rule;

    @Persistent(defaultFetchGroup = "true")
    @Element(dependent = "true")
    private List<RoleDO> roles;

    @Persistent(defaultFetchGroup = "true")
    @Element(dependent = "true")
    private List<RoleGroupDO> roleGroups;

    @Persistent(defaultFetchGroup = "true")
    @Element(dependent = "true")
    private List<ElementSchoolDO> elementSchools;

    @Persistent
    @Element(dependent = "true")
    private transient List<PackageDescriptorDO> descriptors;

    @Persistent(defaultFetchGroup = "false")
    @Element(dependent = "true")
    private transient List<BoardDO> boards;

    protected PackageDO(PackageBundleDO bundle, long version) {
        super(bundle.getKey(), version);
        this.bundleKey = bundle.getKey();
        this.roles = new ArrayList<RoleDO>();
        this.roleGroups = new ArrayList<RoleGroupDO>();
        this.elementSchools = new ArrayList<ElementSchoolDO>();
        this.descriptors = new ArrayList<PackageDescriptorDO>();
    }

    protected PackageDO(PackageBundleDO bundle, PackageDO source) {
        this(bundle, bundle.getPlayedCount() + 1);
        for (ElementSchool elementSchool : source.getElementSchools()) {
            elementSchools.add(new ElementSchoolDO(this, (ElementSchoolDO) elementSchool));
        }
        for (RoleDO role : source.getRoles().values()) {
            roles.add(new RoleDO(this, role));
        }
    }

    public Key getBundleKey() {
        return bundleKey;
    }

    protected boolean addProperty(String name, String data) {
        return false;
    }

    public PropertyDO[] getProperties() {
        return new PropertyDO[0];
    }

    public RuleDO getRule() {
        return rule;
    }

    public RuleDO rule(List<AttributeHandler> validators,
                       List<AttributeHandler> before,
                       List<AttributeHandler> after) {
        if (rule == null) rule = new RuleDO(this, validators, before, after);
        return rule;
    }

    public RoleDO newRole(String name,
                          List<AttributeHandler> validators,
                          List<AttributeHandler> before,
                          List<AttributeHandler> after) {
        RoleDO role = new RoleDO(this, name, validators, before, after);
        roles.add(role);
        return role;
    }

    public RoleDO[] getAllRoles() {
        return roles.toArray(new RoleDO[roles.size()]);
    }

    public Map<String, RoleDO> getRoles() {
        Map<String, RoleDO> map = new HashMap<String, RoleDO>();
        for (RoleDO role : roles) map.put(role.getName(), role);
        return map;
    }

    public RoleGroupDO newRoleGroup() {
        RoleGroupDO group = new RoleGroupDO(this);
        roleGroups.add(group);
        return group;
    }

    public RoleGroupDO getRoleGroup(int playerNumber) {
        List<RoleGroupDO> candidates = new ArrayList<RoleGroupDO>();
        for (RoleGroupDO group : getRoleGroups()) {
            group.restore(roles);
            if (group.getRoles().length == playerNumber) candidates.add(group);
        }
        return candidates.get((int) (candidates.size() * Math.random()));
    }

    public List<RoleGroupDO> getRoleGroups() {
        for (RoleGroupDO group : roleGroups) group.restore(this.roles);
        return Collections.unmodifiableList(roleGroups);
    }

    public ElementSchoolDO newElementSchool(String name) {
        for (ElementSchoolDO elementSchool : elementSchools) {
            if (elementSchool.getName().equals(name)) return elementSchool;
        }
        ElementSchoolDO elementSchool = new ElementSchoolDO(this, name);
        elementSchools.add(elementSchool);
        return elementSchool;
    }

    public ElementSchool[] getElementSchools() {
        return elementSchools.toArray(new ElementSchool[elementSchools.size()]);
    }

    public PackageDescriptorDO[] getDescriptors() {
        return descriptors.toArray(new PackageDescriptorDO[descriptors.size()]);
    }

    protected boolean addDescriptor(String locale, String name, String description) {
        return descriptors.add(new PackageDescriptorDO(this, locale, name, description));
    }

    public List<BoardDO> getBoards() {
        return boards;
    }
}
