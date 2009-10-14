package com.killard.board.web.game;

import com.killard.board.jdo.game.BoardDO;
import com.killard.board.web.BasicController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

    @RequestMapping(value = "/messages.*", method = RequestMethod.GET)
    public String messages(ModelMap modelMap) throws Exception {
        BoardDO board = getBoardManager();
        modelMap.put("messages", board.getMessages());
        return "action/messages";
    }

    @RequestMapping(value = "/message.*", method = RequestMethod.POST)
    public String message(@RequestParam(value = "to", required = false) String to,
                          @RequestParam("message") String message,
                          ModelMap modelMap) throws Exception {
        BoardDO board = getBoardManager();
        board.postMessage(getPlayerId(), to, message);
        modelMap.put("messages", board.getMessages());
        return "action/messages";
    }

}
