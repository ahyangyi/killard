package com.killard.board.web.servlet;

import com.killard.board.web.cache.CacheInstance;
import com.killard.board.web.cache.PlayerCache;

import javax.cache.Cache;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        CacheInstance instance = CacheInstance.getInstance();
        PlayerCache playerCache = instance.getPlayerCache();
        if (playerCache == null) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
        else {
            Cache cache = instance.getBoardCache();
            if (!cache.containsKey(playerCache.getBoardKey())) {
                cache.put(playerCache.getBoardKey(), instance.getBoard().getLastActionLog().getKey().getId());
            }
            Long lastActionId = (Long) cache.get(playerCache.getBoardKey());
            if (lastActionId > playerCache.getLastActionId()) {
                // update player cache with id of the newest action.
                playerCache.setLastActionId(lastActionId);
                cache.put(instance.getPlayerId(), playerCache);
                
                response.setContentType("text/plain");
                response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
                response.setHeader("Pragma", "no-cache"); //HTTP 1.0
                response.setDateHeader("Expires", 0); //prevents caching at the proxy server
                response.getWriter().println(lastActionId);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            }
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
