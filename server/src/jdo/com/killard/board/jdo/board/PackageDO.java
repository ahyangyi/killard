package com.killard.board.jdo.board;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Rating;
import com.google.appengine.api.users.User;
import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.board.descriptor.PackageDescriptorDO;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.Collections;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Set;
import java.util.HashSet;
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
public class PackageDO extends DescriptableDO<PackageDO, PackageDescriptorDO> {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private String name;

    @Persistent
    private Set<User> managers;

    @Persistent
    private Set<User> players;

    @Persistent
    private Date createDate;

    @Persistent
    private Date modifiedDate;

    @Persistent
    private RuleDO rule;

    @Persistent
    private SortedSet<RoleDO> roles;

    @Persistent
    private SortedSet<RoleGroupDO> roleGroups;

    @Persistent
    private SortedSet<ElementSchoolDO> elementSchools;

    @Persistent
    private Boolean clonable;

    @Persistent
    private Boolean published;

    @Persistent
    private Boolean open;

    @Persistent
    private Rating rating;

    @Persistent
    private SortedSet<PackageDescriptorDO> descriptors;

    public PackageDO(String name, User creator) {
        this.name = name;
        this.managers = new HashSet<User>();
        this.players = new HashSet<User>();
        this.roles = new TreeSet<RoleDO>();
        this.roleGroups = new TreeSet<RoleGroupDO>();
        this.elementSchools = new TreeSet<ElementSchoolDO>();
        this.clonable = true;
        this.published = false;
        this.open = false;
        this.createDate = new Date();
        this.modifiedDate = createDate;
        this.rating = new Rating(0);
        this.descriptors = new TreeSet<PackageDescriptorDO>();

        addManager(creator);
        addPlayer(creator);
    }

    public Key getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getManagers() {
        return Collections.unmodifiableSet(managers);
    }

    public boolean addManager(User manager) {
        return managers.add(manager);
    }

    public boolean removeManager(User manager) {
        return managers.remove(manager);
    }

    public Set<User> getPlayers() {
        return Collections.unmodifiableSet(players);
    }

    public boolean addPlayer(User player) {
        return players.add(player);
    }

    public boolean removePlayer(User player) {
        return players.remove(player);
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public RuleDO getRule() {
        return rule;
    }

    public void setRule(RuleDO rule) {
        this.rule = rule;
    }

    public RoleDO[] getRoles() {
        return roles.toArray(new RoleDO[roles.size()]);
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

    public ElementSchoolDO[] getElementSchools() {
        return elementSchools.toArray(new ElementSchoolDO[elementSchools.size()]);
    }

    public boolean isClonable() {
        return clonable;
    }

    public void setClonable(boolean clonable) {
        this.clonable = clonable;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public int getRating() {
        return rating.getRating();
    }

    public void setRating(int rating) {
        this.rating = new Rating(rating);
    }

    public SortedSet<PackageDescriptorDO> getDescriptors() {
        return descriptors;
    }

    public int compareTo(PackageDO compare) {
        if (getRating() == compare.getRating()) {
            return (int) (compare.getKey().getId() - getKey().getId());
        }
        return getRating() - compare.getRating();
    }

    public PackageDO clone(String id, User creator) {
        PackageDO pack = new PackageDO(id, creator);
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
