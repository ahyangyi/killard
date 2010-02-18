package com.killard.board.web;

import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.record.ActionLogDO;
import com.killard.board.web.cache.CacheInstance;
import com.killard.board.web.cache.PlayerCache;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Date;

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

    protected void actions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
        response.setHeader("Pragma", "no-cache"); //HTTP 1.0
        response.setDateHeader("Expires", 0); //prevents caching at the proxy server

        PrintWriter out = response.getWriter();

        long lastUpdatedTime = 0l;
        try {
            lastUpdatedTime = Long.parseLong(request.getParameter("lastUpdatedTime"));
        } catch (Exception ignored) {
        }

        CacheInstance instance = CacheInstance.getInstance();
        PlayerCache playerCache = instance.getPlayerCache();
        if (playerCache == null) {
            out.println("[]");
            return;
        }

        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Query query = pm.newQuery(ActionLogDO.class);
        query.setFilter("boardKey == bKey && time > lastUpdatedTime");
        query.setOrdering("time ascending");
        query.declareParameters("com.google.appengine.api.datastore.Key bKey, java.util.Date lastUpdatedTime");
        Collection actions = (Collection) query.execute(playerCache.getBoardKey(), new Date(lastUpdatedTime));

        out.println("[");
        boolean first = true;
        for (Object action : actions) {
            ActionLogDO log = (ActionLogDO) action;
            if (first) out.println("{");
            else out.println(",{");
            first = false;
            out.println("\"action\":\"" + log.getAction() + "\"");
            out.println(",\"time\":" + log.getTime().getTime());
            out.println("," + log.getLog());
            out.println("}");
        }
        out.println("]");
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