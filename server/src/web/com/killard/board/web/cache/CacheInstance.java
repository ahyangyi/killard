package com.killard.board.web.cache;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.memcache.jsr107cache.GCacheFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.killard.board.card.Element;
import com.killard.board.card.Skill;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.AttributeDO;
import com.killard.board.jdo.board.BoardDO;
import com.killard.board.jdo.board.ElementDO;
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

    private final Cache packageCache;

    private final Cache boardCache;

    private final Cache messageCache;

    private CacheInstance() {
        Map<String, Object> props = new HashMap<String, Object>();
        props.put(GCacheFactory.EXPIRATION_DELTA, 24 * 60 * 60);
        packageCache = createCache(props);
        props.put(GCacheFactory.EXPIRATION_DELTA, 60 * 60);
        boardCache = createCache(props);
        props.put(GCacheFactory.EXPIRATION_DELTA, 20 * 60);
        messageCache = createCache(props);
    }

    private static Cache createCache(Map<String, Object> props) {
        try {
            return CacheManager.getInstance().getCacheFactory().createCache(props);
        } catch (CacheException ignored) {
            return null;
        }
    }

    public static CacheInstance getInstance() {
        return instance;
    }

    public Cache getPackageCache() {
        return packageCache;
    }

    public Cache getBoardCache() {
        return boardCache;
    }

    public Cache getMessageCache() {
        return messageCache;
    }

    public String getPlayerId() {
        UserService userService = UserServiceFactory.getUserService();
        return userService.getCurrentUser().getUserId();
    }

    public PlayerCache getPlayerCache() {
        String id = getPlayerId();
        if (boardCache.containsKey(id)) return (PlayerCache) boardCache.get(id);

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
        boardCache.put(id, playerCache);
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
        return pm.getObjectById(BoardDO.class, cache.getBoardKey());
    }

    public PackageDO getPackage(Key key) {
        if (boardCache.containsKey(key)) return (PackageDO) boardCache.get(key);
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        PackageDO pack = pm.getObjectById(PackageDO.class, key);

        // load data
        pack.getRule();
        pack.getRoles();
        for (Element e : pack.getElements()) {
            ElementDO element = (ElementDO) e;
            for (AttributeDO attribute : element.getAttributes()) {
                attribute.getProperties();
            }
            for (MetaCardDO card : element.getCards()) {
                for (Skill s : card.getSkills()) {
                    SkillDO skill = (SkillDO) s;
                    skill.getProperties();
                }
                card.getAttributes();
                card.getProperties();
            }
            element.getProperties();
        }

        // make transient so that we can put it into memcache.
        pm.makeTransient(pack);
        boardCache.put(key, pack);
        return pack;
    }
}
