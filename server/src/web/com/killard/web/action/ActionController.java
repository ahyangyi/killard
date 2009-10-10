package com.killard.web.action;

import com.killard.web.BasicController;
import com.killard.web.PersistenceHelper;
import com.killard.web.jdo.BoardManagerDO;
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

    @RequestMapping(value = "/game/playcard.*", method = RequestMethod.POST)
    public String playCard(@RequestParam("cardIndex") int cardIndex,
                           @RequestParam("cardPosition") int cardPosition,
                           @RequestParam("targetPosition") int targetPosition,
                           ModelMap modelMap) throws Exception {
        BoardManagerDO boardManager = getBoardManager();
        boardManager.playCard(cardIndex, cardPosition, targetPosition);
        PersistenceHelper.getPersistenceManager().makePersistent(boardManager);
        modelMap.put("actions", boardManager.getActions());
        return "action/result";
    }

    @RequestMapping(value = "/game/cast.*", method = RequestMethod.POST)
    public String cast(@RequestParam("cardPosition") int cardPosition,
                       @RequestParam("skillIndex") int skillIndex, ModelMap modelMap) throws Exception {
        BoardManagerDO boardManager = getBoardManager();
        boardManager.cast(cardPosition, skillIndex);
        PersistenceHelper.getPersistenceManager().makePersistent(boardManager);
        modelMap.put("actions", boardManager.getActions());
        return "action/result";
    }

    @RequestMapping(value = "/game/endturn.*", method = RequestMethod.GET)
    public String endTurn(ModelMap modelMap) throws Exception {
        BoardManagerDO boardManager = getBoardManager();
        boardManager.endTurn();
        PersistenceHelper.getPersistenceManager().makePersistent(boardManager);
        modelMap.put("actions", boardManager.getActions());
        return "action/result";
    }

    @RequestMapping(value = "/game/endturn.*", method = RequestMethod.POST)
    public String postEndTurn(ModelMap modelMap) throws Exception {
        return endTurn(modelMap);
    }

    @RequestMapping(value = "/game/actions.*", method = RequestMethod.GET)
    public String actions(ModelMap modelMap) throws Exception {
        BoardManagerDO boardManager = getBoardManager();
        modelMap.put("actions", boardManager.getActions());
        return "action/result";
    }

}
