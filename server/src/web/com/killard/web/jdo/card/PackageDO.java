package com.killard.web.jdo.card;

import com.google.appengine.api.users.User;
import com.killard.web.jdo.DescriptableDO;
import com.killard.web.jdo.board.BoardManagerDO;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import java.util.Collections;
import java.util.Date;
import java.util.SortedSet;
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
public class PackageDO extends DescriptableDO<PackageDescriptorDO> {

    @Persistent(defaultFetchGroup = "false")
    private SortedSet<User> managers = new TreeSet<User>();

    @Persistent(defaultFetchGroup = "false")
    private SortedSet<User> players = new TreeSet<User>();

    @Persistent
    private Date createDate;

    @Persistent
    private Date modifiedDate;

    @Persistent
    private RuleDO rule;

    @Persistent(defaultFetchGroup = "false")
    private SortedSet<ElementSchoolDO> elementSchools = new TreeSet<ElementSchoolDO>();

    @Persistent(defaultFetchGroup = "false")
    private SortedSet<BoardManagerDO> boards = new TreeSet<BoardManagerDO>();

    @Persistent(defaultFetchGroup = "false")
    private SortedSet<PackageDescriptorDO> descriptors = new TreeSet<PackageDescriptorDO>();

    @Persistent
    private Boolean clonable = true;

    @Persistent
    private Boolean published = false;

    @Persistent
    private Boolean open = false;

    public PackageDO(String id, User creator) {
        super(id);
        addManager(creator);
        this.createDate = new Date();
        this.modifiedDate = createDate;
    }

    public SortedSet<User> getManagers() {
        return Collections.unmodifiableSortedSet(managers);
    }

    public boolean addManager(User manager) {
        return managers.add(manager);
    }

    public boolean removeManager(User manager) {
        return managers.remove(manager);
    }

    public SortedSet<User> getPlayers() {
        return Collections.unmodifiableSortedSet(players);
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

    public ElementSchoolDO[] getElementSchools() {
        return elementSchools.toArray(new ElementSchoolDO[elementSchools.size()]);
    }

    public BoardManagerDO[] getBoards() {
        return boards.toArray(new BoardManagerDO[boards.size()]);
    }

    public SortedSet<PackageDescriptorDO> getDescriptors() {
        return descriptors;
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
