package com.killard.board.jdo.board;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Rating;
import com.google.appengine.api.users.User;
import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.PropertyDO;
import com.killard.board.jdo.board.descriptor.PackageDescriptorDO;
import com.killard.board.card.BoardPackage;
import com.killard.board.card.ElementSchool;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
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
public class PackageDO extends DescriptableDO<PackageDO, PropertyDO, PackageDescriptorDO> implements BoardPackage {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private String name;

    @Persistent
    private RuleDO rule;

    @Persistent
    private SortedSet<RoleDO> roles;

    @Persistent
    private SortedSet<RoleGroupDO> roleGroups;

    @Persistent
    private SortedSet<ElementSchoolDO> elementSchools;

    @Persistent
    private SortedSet<PackageDescriptorDO> descriptors;

    public PackageDO(String name) {
        this.name = name;
        this.roles = new TreeSet<RoleDO>();
        this.roleGroups = new TreeSet<RoleGroupDO>();
        this.elementSchools = new TreeSet<ElementSchoolDO>();
        this.descriptors = new TreeSet<PackageDescriptorDO>();
    }

    public Key getKey() {
        return key;
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

    public void setName(String name) {
        this.name = name;
    }

    public RuleDO getRule() {
        return rule;
    }

    public void setRule(RuleDO rule) {
        this.rule = rule;
    }

    public Map<String, RoleDO> getRoles() {
        Map<String, RoleDO> map = new HashMap<String, RoleDO>();
        for (RoleDO role : roles) map.put(role.getName(), role);
        return map;
    }

    public RoleGroupDO getRoleGroup(int playerNumber) {
        List<RoleGroupDO> candidates = new ArrayList<RoleGroupDO>();
        for (RoleGroupDO group : getRoleGroups()) {
            if (group.getRoles().length == playerNumber) candidates.add(group);
        }
        return candidates.get((int) (candidates.size() * Math.random()));
    }

    public SortedSet<RoleGroupDO> getRoleGroups() {
        for (RoleGroupDO group : roleGroups) group.restore(this.roles);
        return Collections.unmodifiableSortedSet(roleGroups);
    }

    public ElementSchool[] getElementSchools() {
        return elementSchools.toArray(new ElementSchool[elementSchools.size()]);
    }

    public PackageDescriptorDO[] getDescriptors() {
        return descriptors.toArray(new PackageDescriptorDO[descriptors.size()]);
    }

    public boolean addDescriptor(PackageDescriptorDO descriptor) {
        return descriptors.add(descriptor);
    }

    public boolean removeDescriptor(PackageDescriptorDO descriptor) {
        return descriptors.remove(descriptor);
    }

    public int compareTo(PackageDO compare) {
        return getKey().compareTo(compare.getKey());
    }

    public PackageDO clone(String id) {
        PackageDO pack = new PackageDO(id);
        pack.rule = rule.clone(pack);
        for (ElementSchoolDO elementSchool : elementSchools) {
            pack.elementSchools.add(elementSchool.clone(pack));
        }
        for (PackageDescriptorDO descriptor : descriptors) {
            PackageDescriptorDO cloneDescriptor = new PackageDescriptorDO(pack, descriptor.getLocale());
            pack.addDescriptor(cloneDescriptor);
        }
        return pack;
    }
}
