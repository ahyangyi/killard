package com.killard.board.jdo.board;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Rating;
import com.google.appengine.api.users.User;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.IdGeneratorStrategy;
import java.util.Date;
import java.util.SortedSet;
import java.util.Calendar;
import java.util.TreeSet;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

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
public class PackageBundleDO {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private String name;

    @Persistent
    private Set<PlayerProfileDO> players;

    @Persistent
    private Date createDate;

    @Persistent
    private Date modifiedDate;

    @Persistent
    private boolean clonable;

    @Persistent
    private int clonedCount;

    @Persistent
    private int playedCount;

    @Persistent
    private String status;

    @Persistent
    private SortedSet<PackageDO> packages;

    public PackageBundleDO(String name) {
        this.name = name;
        this.createDate = Calendar.getInstance().getTime();
        this.modifiedDate = Calendar.getInstance().getTime();
        this.players = new HashSet<PlayerProfileDO>();
        this.clonable = true;
        this.clonedCount = 0;
        this.status = PackageStatus.PRIVATE.name();
        this.playedCount = 0;
        this.packages = new TreeSet<PackageDO>();
    }

    public PackageBundleDO(PackageBundleDO source) {
        this.name = source.getName();
        this.createDate = Calendar.getInstance().getTime();
        this.modifiedDate = Calendar.getInstance().getTime();
        this.players = new HashSet<PlayerProfileDO>();
        this.clonable = source.isClonable();
        this.clonedCount = 0;
        this.status = source.getStatus();
        this.playedCount = 0;
        this.packages = new TreeSet<PackageDO>();
    }

    public Key getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public Set<PlayerProfileDO> getPlayers() {
        return Collections.unmodifiableSet(players);
    }

    public boolean isClonable() {
        return clonable;
    }

    public int getClonedCount() {
        return clonedCount;
    }

    public int getPlayedCount() {
        return playedCount;
    }

    public String getStatus() {
        return status;
    }

    public PackageDO[] getPackages() {
        return packages.toArray(new PackageDO[packages.size()]);
    }

    public PackageDO getDraft() {
        return packages.first();
    }

    public PackageDO getRelease() {
        return packages.last();
    }

    public PackageDO release() {
        PackageDO release = getDraft().clone(packages.size() + 1);
        packages.add(release);
        return release;
    }
}
