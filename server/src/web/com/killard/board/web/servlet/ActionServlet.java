package com.killard.board.web.servlet;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.record.ActionLogDO;
import com.killard.board.web.cache.CacheInstance;
import com.killard.board.web.cache.PlayerCache;
import com.killard.board.web.util.ResponseUtils;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
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
public class ActionServlet extends HttpServlet {

    private final Logger log = Logger.getLogger("Killard");

    protected void actions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long since;
        try {
            since = Long.parseLong(request.getParameter("since"));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        CacheInstance instance = CacheInstance.getInstance();
        Collection actions;
        try {
            PersistenceHelper.open();
            PlayerCache playerCache = instance.getPlayerCache();
            if (playerCache == null) {
                response.setStatus(HttpServletResponse.SC_NON_AUTHORITATIVE_INFORMATION);
                return;
            }
            Key boardKey = playerCache.getBoardKey();
            KeyFactory.Builder keyBuilder = new KeyFactory.Builder(boardKey);
            keyBuilder.addChild(ActionLogDO.class.getSimpleName(), since);
            Key actionLogKey = keyBuilder.getKey();

            PersistenceManager pm = PersistenceHelper.getPersistenceManager();
            Query query = pm.newQuery(ActionLogDO.class);
            query.setFilter("boardKey == b && key > s");
            query.setOrdering("key ascending");
            query.declareParameters(
                    "com.google.appengine.api.datastore.Key b, com.google.appengine.api.datastore.Key s");
            actions = (Collection) query.execute(boardKey, actionLogKey);
            PersistenceHelper.getPersistenceManager().makeTransientAll(actions);
        } catch (Throwable e) {
            PersistenceHelper.rollback();
            log.log(Level.SEVERE, e.getMessage(), e);
            throw new ServletException(e.getMessage(), e);
        } finally {
            PersistenceHelper.close();
        }
        ResponseUtils.outputActions(response, actions);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        actions(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        actions(request, response);
    }

}
