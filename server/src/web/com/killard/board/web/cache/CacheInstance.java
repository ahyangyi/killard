package com.killard.board.web.cache;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.memcache.jsr107cache.GCacheFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.killard.board.card.ElementSchool;
import com.killard.board.card.Skill;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.AttributeDO;
import com.killard.board.jdo.board.BoardDO;
import com.killard.board.jdo.board.ElementSchoolDO;
import com.killard.board.jdo.board.MetaCardDO;
import com.killard.board.jdo.board.PackageDO;
import com.killard.board.jdo.board.SkillDO;
import com.killard.board.jdo.board.record.PlayerRecordDO;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheManager;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.Collection;
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

    public String getPlayerId() {
        UserService userService = UserServiceFactory.getUserService();
        return userService.getCurrentUser().getUserId();
    }

    public PlayerCache getPlayerCache() {
        String id = getPlayerId();
        if (cache.containsKey(id)) return (PlayerCache) cache.get(id);

        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Query query = pm.newQuery(PlayerRecordDO.class);
        query.setFilter("uid == playerId");
        query.declareParameters("String playerId");
        Collection collection = (Collection) query.execute(id);
        if (collection.isEmpty()) return null;

        PlayerRecordDO player = (PlayerRecordDO) collection.iterator().next();
        BoardDO board = pm.getObjectById(BoardDO.class, player.getBoardKey());
        PlayerCache playerCache = new PlayerCache(player.getKey(), board.getKey(),
                board.getPackageKey(), getPackage(board.getPackageKey()).getBundleKey());
        cache.put(id, playerCache);
        return playerCache;
    }

    public PlayerRecordDO getPlayer() {
        PlayerCache playerCache = getPlayerCache();
        if (playerCache == null) return null;
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        return pm.getObjectById(PlayerRecordDO.class, playerCache.getPlayerKey());
    }

    public BoardDO getBoard() {
        PlayerCache cache = getPlayerCache();
        if (cache == null) return null;
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        BoardDO board = pm.getObjectById(BoardDO.class, cache.getBoardKey());
        board.restore(getPackage(board.getPackageKey()));
        return board;
    }

    public PackageDO getPackage(Key key) {
        if (cache.containsKey(key)) return (PackageDO) cache.get(key);
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        PackageDO pack = pm.getObjectById(PackageDO.class, key);
        pack.getRule();
        pack.getRoleGroups();
        pack.getRoles();
        pack.getProperties();
        for (ElementSchool e : pack.getElementSchools()) {
            ElementSchoolDO ed = (ElementSchoolDO) e;
            for (AttributeDO a : ed.getAttributes()) a.getProperties();
            for (MetaCardDO c : ed.getCards()) {
                for (Skill s : c.getSkills()) {
                    SkillDO sd = (SkillDO) s;
                    sd.getProperties();
                }
                c.getProperties();
            }
            ed.getProperties();
        }
        pm.makeTransient(pack);
        cache.put(key, pack);
        return pack;
    }
}
