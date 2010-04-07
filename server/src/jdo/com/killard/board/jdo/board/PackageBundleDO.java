package com.killard.board.jdo.board;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
public class PackageBundleDO {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private String name;

    @Persistent
    @Element(dependent = "true")
    private Set<PlayerProfileDO> players;

    @Persistent
    private Date createdDate;

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
    @Element(dependent = "true")
    private List<PackageDO> packages;

    public PackageBundleDO(String name) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(getClass().getSimpleName(), name);
        this.key = keyBuilder.getKey();
        this.name = name;
        this.createdDate = Calendar.getInstance().getTime();
        this.modifiedDate = Calendar.getInstance().getTime();
        this.players = new HashSet<PlayerProfileDO>();
        this.clonable = true;
        this.clonedCount = 0;
        this.status = PackageStatus.PRIVATE.name();
        this.playedCount = 0;
        this.packages = new ArrayList<PackageDO>();
    }

    public PackageBundleDO(PackageBundleDO source) {
        this(source.name + "_clone");
        for (PackageDO pack : source.packages) {
            packages.add(new PackageDO(this, pack));
        }
    }

    public Key getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public Date getCreatedDate() {
        return createdDate;
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
        return packages.get(0);
    }

    public PackageDO getRelease() {
        return packages.get(packages.size() - 1);
    }

    public PackageDO draft() {
        if (packages.isEmpty()) packages.add(new PackageDO(this, 1));
        return getDraft();
    }

    public PackageDO release() {
//        packages.add(new PackageDO(this, getDraft()));
        return getRelease();
    }

    public void setStatus(PackageStatus status) {
        this.status = status.name();
    }

    public void setClonable(boolean clonable) {
        this.clonable = clonable;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlayedCount(int playedCount) {
        this.playedCount = playedCount;
    }

    public void setClonedCount(int clonedCount) {
        this.clonedCount = clonedCount;
    }

    public boolean addPlayerProfile(PlayerProfileDO player) {
        return this.players.add(player);
    }
}
