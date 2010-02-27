package com.killard.board.web.util;

import com.killard.board.jdo.board.record.ActionLogDO;
import com.killard.board.web.cache.CacheInstance;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public final class ResponseUtils {

    public static void outputActions(HttpServletResponse response, List<ActionLogDO> actions, int start)
            throws IOException {
        if (start == actions.size()) outputActions(response, Collections.<ActionLogDO>emptyList());
        else outputActions(response, actions.subList(start, actions.size()));
    }

    public static void outputActions(HttpServletResponse response, Collection<ActionLogDO> actions) throws IOException {
        response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
        response.setHeader("Pragma", "no-cache"); //HTTP 1.0
        response.setDateHeader("Expires", 0); //prevents caching at the proxy server

        PrintWriter out = response.getWriter();

        out.println("[");
        boolean first = true;
        for (ActionLogDO log : actions) {
            boolean self = CacheInstance.getInstance().getPlayerId().equals(log.getTargetPlayerId());
            if (first) out.println("{");
            else out.println(",{");
            first = false;
            out.println("\"action\":\"" + log.getAction() + "\"");
            out.println(",\"id\":" + log.getKey().getId());
            out.println(",\"self\":" + self);
            if (self || !log.getAction().equals("DealCardAction")) {
                if (!log.getLog().isEmpty()) out.println("," + log.getLog());
            }
            out.println("}");
        }
        out.println("]");
    }
}
