package com.killard.board.jdo.board;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.card.BoardPackage;
import com.killard.board.card.ElementSchool;
import com.killard.board.jdo.AttributeHandler;
import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.PropertyDO;
import com.killard.board.jdo.board.descriptor.PackageDescriptorDO;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
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
public class PackageDO extends DescriptableDO<PackageDO, PropertyDO, PackageDescriptorDO> implements BoardPackage {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private String name;

    @Persistent
    private Key bundleKey;

    @Persistent(dependent = "true", defaultFetchGroup = "true")
    private RuleDO rule;

    @Persistent(defaultFetchGroup = "true")
    @Element(dependent = "true")
    private Set<RoleDO> roles;

    @Persistent(defaultFetchGroup = "true")
    @Element(dependent = "true")
    private Set<RoleGroupDO> roleGroups;

    @Persistent(defaultFetchGroup = "true")
    @Element(dependent = "true")
    private Set<ElementSchoolDO> elementSchools;

    @Persistent
    @Element(dependent = "true")
    private transient Set<PackageDescriptorDO> descriptors;

    @Persistent(defaultFetchGroup = "false")
    private transient Blob image;

    @Persistent(defaultFetchGroup = "false")
    @Element(dependent = "true")
    private transient Set<BoardDO> boards;

    protected PackageDO(PackageBundleDO bundle, long version) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(bundle.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), version);
        this.key = keyBuilder.getKey();
        this.bundleKey = bundle.getKey();
        this.name = bundle.getName();
        this.roles = new HashSet<RoleDO>();
        this.roleGroups = new HashSet<RoleGroupDO>();
        this.elementSchools = new HashSet<ElementSchoolDO>();
        this.descriptors = new HashSet<PackageDescriptorDO>();
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

    public Key getKey() {
        return key;
    }

    public Key getBundleKey() {
        return bundleKey;
    }

    public String getName() {
        return name;
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

    public Set<RoleGroupDO> getRoleGroups() {
        for (RoleGroupDO group : roleGroups) group.restore(this.roles);
        return Collections.unmodifiableSet(roleGroups);
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

    public boolean isRenderable() {
        return image != null;
    }

    public byte[] getImageData() {
        return image.getBytes();
    }

    public void setImageData(byte[] data) {
        image = new Blob(data);
    }

    public Set<BoardDO> getBoards() {
        return boards;
    }

    public int compareTo(PackageDO compare) {
        return key.compareTo(compare.key);
    }
}
