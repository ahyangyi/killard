package com.killard.board.web.cache;

import com.google.appengine.api.datastore.Key;

import java.io.Serializable;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class PlayerCache implements Serializable {

    private final Key playerKey;

    private final Key boardKey;

    private final Key packageKey;

    private final Key packageBundleKey;

    private long lastActionId;

    public PlayerCache(Key playerKey, Key boardKey, Key packageKey, Key packageBundleKey) {
        this.playerKey = playerKey;
        this.boardKey = boardKey;
        this.packageKey = packageKey;
        this.packageBundleKey = packageBundleKey;
    }

    public Key getPlayerKey() {
        return playerKey;
    }

    public Key getBoardKey() {
        return boardKey;
    }

    public Key getPackageKey() {
        return packageKey;
    }

    public Key getPackageBundleKey() {
        return packageBundleKey;
    }

    public long getLastActionId() {
        return lastActionId;
    }

    public void setLastActionId(long lastActionId) {
        this.lastActionId = lastActionId;
    }
}
