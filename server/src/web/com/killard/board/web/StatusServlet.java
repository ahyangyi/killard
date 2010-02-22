package com.killard.board.web;

import com.killard.board.jdo.board.record.ActionLogDO;
import com.killard.board.web.cache.CacheInstance;
import com.killard.board.web.cache.PlayerCache;

import javax.cache.Cache;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class StatusServlet extends HttpServlet {

    protected void status(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
        response.setHeader("Pragma", "no-cache"); //HTTP 1.0
        response.setDateHeader("Expires", 0); //prevents caching at the proxy server

        PrintWriter out = response.getWriter();

        CacheInstance instance = CacheInstance.getInstance();
        PlayerCache playerCache = instance.getPlayerCache();
        if (playerCache == null) {
            out.println("{\"time\":0}");
        }
        else {
            Cache cache = instance.getCache();
            if (!cache.containsKey(playerCache.getBoardKey())) {
                ActionLogDO[] actions = instance.getBoard().getActions();
                cache.put(playerCache.getBoardKey(), actions[actions.length - 1].getTime().getTime());
            }
            out.println("{\"time\":" + cache.get(playerCache.getBoardKey()) + "}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        status(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        status(request, response);
    }
}
