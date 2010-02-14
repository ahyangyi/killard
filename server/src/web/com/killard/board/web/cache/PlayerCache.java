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

    private Key playerKey;

    private Key boardKey;

    private Key packageKey;

    private Key packageBundleKey;

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
}
