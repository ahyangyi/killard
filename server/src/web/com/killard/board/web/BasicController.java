package com.killard.board.web;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.BoardDO;
import com.killard.board.jdo.board.PackageBundleDO;
import com.killard.board.jdo.board.PackageDO;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
