package com.killard.board.web;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.game.BoardManagerDO;
import com.killard.board.jdo.game.player.PlayerRecordDO;
import com.killard.board.jdo.board.PackageDO;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
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

    public BasicController() {
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
        String playerId = getPlayerId();
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Query query = pm.newQuery(PlayerRecordDO.class);
        query.setFilter("uid == playerId");
        query.declareParameters("String playerId");
        Collection collection = (Collection) query.execute(playerId);
        if (collection.isEmpty()) return null;
        return (PlayerRecordDO) collection.iterator().next();
    }

    protected PlayerRecordDO getPlayer(String playerName) {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Query query = pm.newQuery(PlayerRecordDO.class);
        query.setFilter("name == playerName");
        query.declareParameters("String playerName");
        Collection collection = (Collection) query.execute(playerName);
        if (collection.isEmpty()) return null;
        return (PlayerRecordDO) collection.iterator().next();
    }

    protected BoardManagerDO getBoardManager() {
        PlayerRecordDO player = getPlayer();
        if (player == null) return null;
        BoardManagerDO boardManager = PersistenceHelper.getPersistenceManager()
                .getObjectById(BoardManagerDO.class, player.getBoardManagerKey());
        boardManager.restore();
        return boardManager;
    }

    protected BoardManagerDO getBoardManager(String playerName) {
        PlayerRecordDO player = getPlayer(playerName);
        if (player != null) {
            BoardManagerDO boardManager = PersistenceHelper.getPersistenceManager()
                    .getObjectById(BoardManagerDO.class, player.getBoardManagerKey());
            boardManager.restore();
            return boardManager;
        } else {
            return null;
        }
    }

    protected BoardManagerDO getBoardManager(long boardId) {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key key = KeyFactory.createKey(BoardManagerDO.class.getSimpleName(), boardId);
        BoardManagerDO boardManager = pm.getObjectById(BoardManagerDO.class, key);
        boardManager.restore();
        return boardManager;
    }

    protected PackageDO getPackage(long packageId) {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key key = KeyFactory.createKey(PackageDO.class.getSimpleName(), packageId);
        return pm.getObjectById(PackageDO.class, key);
    }

}
