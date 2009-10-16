package com.killard.board.web.action;

import com.killard.board.jdo.PersistenceHelper;
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
public class ActionController extends BasicController {

    @RequestMapping(value = "/board/playcard.*", method = RequestMethod.POST)
    public String playCard(@RequestParam("cardIndex") int cardIndex,
                           @RequestParam("cardPosition") int cardPosition,
                           @RequestParam("targetPosition") int targetPosition,
                           ModelMap modelMap) throws Exception {
        BoardDO board = getBoardManager();
        board.playCard(cardIndex, cardPosition, targetPosition);
        PersistenceHelper.getPersistenceManager().makePersistent(board);
        modelMap.put("actions", board.getActions());
        return "action/result";
    }

    @RequestMapping(value = "/board/cast.*", method = RequestMethod.POST)
    public String cast(@RequestParam("cardPosition") int cardPosition,
                       @RequestParam("skillIndex") int skillIndex, ModelMap modelMap) throws Exception {
        BoardDO board = getBoardManager();
        board.cast(cardPosition, skillIndex);
        PersistenceHelper.getPersistenceManager().makePersistent(board);
        modelMap.put("actions", board.getActions());
        return "action/result";
    }

    @RequestMapping(value = "/board/endturn.*", method = {RequestMethod.GET, RequestMethod.POST})
    public String endTurn(ModelMap modelMap) throws Exception {
        BoardDO board = getBoardManager();
        board.endTurn();
        PersistenceHelper.getPersistenceManager().makePersistent(board);
        modelMap.put("actions", board.getActions());
        return "action/result";
    }

    @RequestMapping(value = "/board/endcall.*", method = {RequestMethod.GET, RequestMethod.POST})
    public String endCall(ModelMap modelMap) throws Exception {
        BoardDO board = getBoardManager();
        board.endCall();
        PersistenceHelper.getPersistenceManager().makePersistent(board);
        modelMap.put("actions", board.getActions());
        return "action/result";
    }

    @RequestMapping(value = "/board/actions.*", method = RequestMethod.GET)
    public String actions(ModelMap modelMap) throws Exception {
        BoardDO board = getBoardManager();
        modelMap.put("actions", board.getActions());
        return "action/result";
    }

}
