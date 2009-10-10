package com.killard.web.game;

import com.killard.web.BasicController;
import com.killard.web.jdo.board.BoardManagerDO;
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
        BoardManagerDO boardManager = getBoardManager();
        modelMap.put("messages", boardManager.getMessages());
        return "action/messages";
    }

    @RequestMapping(value = "/message.*", method = RequestMethod.POST)
    public String message(@RequestParam(value = "to", required = false) String to,
                          @RequestParam("message") String message,
                          ModelMap modelMap) throws Exception {
        BoardManagerDO boardManager = getBoardManager();
        boardManager.postMessage(getPlayerName(), to, message);
        modelMap.put("messages", boardManager.getMessages());
        return "action/messages";
    }

}
