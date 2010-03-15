package com.killard.board.web.controller.arena;

import com.killard.board.jdo.board.BoardDO;
import com.killard.board.web.cache.CacheInstance;
import com.killard.board.web.controller.BasicController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
@Controller
public class MessageController extends BasicController {

    @RequestMapping(value = "/arena/messages.*", method = RequestMethod.GET)
    public String messages(ModelMap modelMap) throws Exception {
        BoardDO board = CacheInstance.getInstance().getBoard();
        modelMap.put("messages", board.getMessages());
        return "action/messages";
    }

    @RequestMapping(value = {"/arena/message.json", "/arena/message.xml", "/arena/message.html"},
            method = {RequestMethod.GET, RequestMethod.POST})
    public void message(@RequestParam(value = "to", required = false) String to,
                        @RequestParam("message") String message,
                        HttpServletResponse response) throws Exception {
        CacheInstance instance = CacheInstance.getInstance();
        BoardDO board = instance.getBoard();
        board.postMessage(CacheInstance.getInstance().getPlayerId(), to, message);
        response.setContentType("application/json");
        response.getWriter().println("[{\"player\":\"" + instance.getPlayer().getNickname() + "\",\"content\":\"" + message + "\"}]");
    }

}
