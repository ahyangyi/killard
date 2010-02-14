package com.killard.board.web.cache;

import com.google.appengine.api.memcache.jsr107cache.GCacheFactory;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheManager;
import java.util.HashMap;
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
public enum CacheInstance {

    instance;

    private final Cache cache;

    private CacheInstance() {
        Map<String, Object> props = new HashMap<String, Object>();
        props.put(GCacheFactory.EXPIRATION_DELTA, 3600);
        Cache c = null;
        try {
            c = CacheManager.getInstance().getCacheFactory().createCache(props);
        } catch (CacheException ignored) {
        }
        cache = c;
    }

    public static CacheInstance getInstance() {
        return instance;
    }

    public Cache getCache() {
        return cache;
    }
}
