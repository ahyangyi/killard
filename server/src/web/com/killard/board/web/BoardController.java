package com.killard.board.web;

import com.killard.board.environment.BoardException;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.BoardDO;
import com.killard.board.jdo.board.PackageDO;
import com.killard.board.jdo.board.record.PlayerRecordDO;
import com.killard.board.card.ElementSchool;
import com.killard.board.card.Player;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServletRequest;
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
public class BoardController extends BasicController {

    @RequestMapping(value = "/board/deal.*", method = {RequestMethod.GET, RequestMethod.POST})
    public void deal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        BoardDO board = getBoard();
        PlayerRecordDO player = (PlayerRecordDO) board.getCurrentPlayer();
    }

    @RequestMapping(value = "/board.*", method = {RequestMethod.GET, RequestMethod.POST})
    public String board(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String playerId = getPlayerId();
        getLog().fine("Get record record information: " + playerId);
        BoardDO board = getBoard();

        if (board == null) {
            redirect("/packages", request, response);
            return null;
        }

        modelMap.put("board", board);
        modelMap.put("playerId", playerId);
        modelMap.put("players", board.getPlayers(playerId));
        modelMap.put("actions", board.getActions());
        return "board";
    }

    @RequestMapping(value = "/board/new.*", method = {RequestMethod.GET, RequestMethod.POST})
    public void newGame(@RequestParam("packageBundleId") long packageBundleId,
                        @RequestParam(value = "playerNumber", required = false, defaultValue = "2") int playerNumber,
                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        PlayerRecordDO player = getPlayer();
        if (player != null) {
            quit();
        }
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        PackageDO gamePackage = getPackage(packageBundleId);
        BoardDO board = new BoardDO(gamePackage, playerNumber);
        pm.makePersistent(board);

        join(board);
        redirect("/board", request, response);
    }

    @RequestMapping(value = "/board/join.*", method = {RequestMethod.GET, RequestMethod.POST})
    public void join(@RequestParam("packageBundleId") long packageBundleId,
                     @RequestParam("boardId") long boardId,
                     HttpServletRequest request, HttpServletResponse response) throws Exception {
        getLog().fine("Join record for " + getPlayerId());

        PlayerRecordDO player = getPlayer();
        if (player == null || player.getBoardManagerKey().getId() != boardId) {
            quit();
            join(getBoardManager(packageBundleId, boardId));
        }

        redirect("/board", request, response);
    }

    @RequestMapping(value = "/board/quit.*", method = {RequestMethod.GET, RequestMethod.POST})
    public void quit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        getLog().fine("Quit record for " + getPlayerId());

        quit();

        redirect("/packages", request, response);
    }

    @RequestMapping(value = "/board/playcard.*", method = {RequestMethod.GET, RequestMethod.POST})
    public String playCard(@RequestParam("cardIndex") int cardIndex,
                           @RequestParam("cardPosition") int cardPosition,
                           @RequestParam("targetPosition") int targetPosition,
                           ModelMap modelMap) throws Exception {
        BoardDO board = getBoard();
        board.playCard(cardIndex, cardPosition, targetPosition);
        PersistenceHelper.getPersistenceManager().makePersistent(board);
        modelMap.put("actions", board.getActions());
        return "board/actions";
    }

    @RequestMapping(value = "/board/cast.*", method = RequestMethod.POST)
    public String cast(@RequestParam("cardPosition") int cardPosition,
                       @RequestParam("skillIndex") int skillIndex, ModelMap modelMap) throws Exception {
        BoardDO board = getBoard();
        board.cast(cardPosition, skillIndex);
        PersistenceHelper.getPersistenceManager().makePersistent(board);
        modelMap.put("actions", board.getActions());
        return "board/actions";
    }

    @RequestMapping(value = "/board/endturn.*", method = {RequestMethod.GET, RequestMethod.POST})
    public String endTurn(ModelMap modelMap) throws Exception {
        BoardDO board = getBoard();
        board.endTurn();
        PersistenceHelper.getPersistenceManager().makePersistent(board);
        modelMap.put("actions", board.getActions());
        return "board/actions";
    }

    @RequestMapping(value = "/board/endcall.*", method = {RequestMethod.GET, RequestMethod.POST})
    public String endCall(ModelMap modelMap) throws Exception {
        BoardDO board = getBoard();
        board.endCall();
        PersistenceHelper.getPersistenceManager().makePersistent(board);
        modelMap.put("actions", board.getActions());
        return "board/actions";
    }

    @RequestMapping(value = "/board/actions.*", method = {RequestMethod.GET, RequestMethod.POST})
    public String actions(ModelMap modelMap) throws Exception {
        BoardDO board = getBoard();
        modelMap.put("actions", board.getActions());
        return "board/actions";
    }

    protected void join(BoardDO board) throws BoardException {
        Player player = board.addPlayer(getPlayerId());
        PersistenceHelper.getPersistenceManager().makePersistent(board);
        PersistenceHelper.doTransaction();

        //TODO
        board.test();
        PersistenceHelper.getPersistenceManager().makePersistent(board);
    }

    protected void quit() {
        BoardDO board = getBoard();

        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Query query = pm.newQuery(PlayerRecordDO.class);
        query.setFilter("uid == playerId");
        query.declareParameters("String playerId");
        query.deletePersistentAll(getPlayerId());

        if (board != null && board.getPlayers().length < 2) {
            pm.deletePersistent(board);
        }
    }

}
