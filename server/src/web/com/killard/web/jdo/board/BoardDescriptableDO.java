package com.killard.web.jdo.board;

import com.google.appengine.api.datastore.Key;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public abstract class BoardDescriptableDO implements Comparable<BoardDescriptableDO> {

    public abstract Key getKey();

    public int compareTo(BoardDescriptableDO descriptableDO) {
        return getKey().compareTo(descriptableDO.getKey());
    }
}
