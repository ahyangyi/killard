package com.killard.board.web;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.memcache.jsr107cache.GCacheFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.BoardDO;
import com.killard.board.jdo.board.PackageBundleDO;
import com.killard.board.jdo.board.PackageDO;
import com.killard.board.jdo.board.record.PlayerRecordDO;
import com.killard.board.web.cache.PlayerCache;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheManager;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class BasicController {

    private final Logger log = Logger.getLogger(BasicController.class.getName());

    private static final Cache cache = createCache();

    public BasicController() {
    }

    private static Cache createCache() {
        try {
            Map<String, Object> props = new HashMap<String, Object>();
            props.put(GCacheFactory.EXPIRATION_DELTA, 3600);
            return CacheManager.getInstance().getCacheFactory().createCache(props);
        } catch (CacheException e) {
            return null;
        }
    }

    protected Cache getCache() {
        return cache;
    }

    protected PlayerCache getPlayerCache() {
        String id = getPlayerId();
        if (cache.containsKey(id)) return (PlayerCache) cache.get(id);

        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Query query = pm.newQuery(PlayerRecordDO.class);
        query.setFilter("uid == playerId");
        query.declareParameters("String playerId");
        Collection collection = (Collection) query.execute(id);
        if (collection.isEmpty()) {
            System.out.println("?? null player: " + id);
            return null;
        }

        PlayerRecordDO player = (PlayerRecordDO) collection.iterator().next();
        BoardDO board = pm.getObjectById(BoardDO.class, player.getBoardKey());
        board.restore();
        PlayerCache playerCache = new PlayerCache(player.getKey(), board.getKey(), board.getPackageKey(), board.getBoardPackage().getBundleKey());
        cache.put(id, playerCache);
        return playerCache;
    }

    protected Logger getLog() {
        return log;
    }

    protected void redirect(String method, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        String resource = uri.substring(uri.indexOf("."));
        response.sendRedirect(method + resource);
    }

    protected User getUser() {
        UserService userService = UserServiceFactory.getUserService();
        return userService.getCurrentUser();
    }

    protected String getPlayerId() {
        UserService userService = UserServiceFactory.getUserService();
        return userService.getCurrentUser().getUserId();
    }

    protected PlayerRecordDO getPlayer() {
        PlayerCache playerCache = getPlayerCache();
        if (playerCache == null) return null;
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        return pm.getObjectById(PlayerRecordDO.class, playerCache.getPlayerKey());
    }

    protected BoardDO getBoard() {
        PlayerCache cache = getPlayerCache();
        if (cache == null) return null;
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        BoardDO board = pm.getObjectById(BoardDO.class, cache.getBoardKey());
        board.restore();
        return board;
    }

    protected BoardDO getBoardManager(long packageBundleId, long boardId) {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), packageBundleId);
        Key packageKey = pm.getObjectById(PackageBundleDO.class, bundleKey).getRelease().getKey();
        Key boardKey = KeyFactory.createKey(packageKey, BoardDO.class.getSimpleName(), boardId);

        BoardDO board = pm.getObjectById(BoardDO.class, boardKey);
        board.restore();
        return board;
    }

    protected PackageDO getPackage(long packageBundleId) {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key key = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), packageBundleId);
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, key);
        return bundle.getRelease();
    }
    
    protected Long getPackageBundleId(String uri) {
        return Long.parseLong(uri.split("/")[2]);
    }

}
