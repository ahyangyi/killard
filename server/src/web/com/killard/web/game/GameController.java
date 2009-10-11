package com.killard.web.game;

import com.killard.jdo.PersistenceHelper;
import com.killard.jdo.board.BoardManagerDO;
import com.killard.jdo.board.BoardPackageDO;
import com.killard.jdo.board.player.PlayerRecordDO;
import com.killard.jdo.card.PackageDO;
import com.killard.web.BasicController;
import com.google.appengine.api.datastore.Key;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
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
@Controller
public class GameController extends BasicController {

    public static final int INIT_HEALTH = 50;

    @RequestMapping(value = "/game/list.*", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(ModelMap modelMap) throws Exception {
        String playerName = getPlayerId();
        getLog().fine("Get game board list: " + playerName);

        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        List<PackageDO> packages = new LinkedList<PackageDO>();
        Extent<PackageDO> packageExtent = pm.getExtent(PackageDO.class);
        for (PackageDO pack : packageExtent) {
            if (pack.isOpen()) packages.add(pack);
        }
        packageExtent.closeAll();

//        List<BoardManagerDO> boards = new LinkedList<BoardManagerDO>();
//        Extent<BoardManagerDO> boardExtent = pm.getExtent(BoardManagerDO.class);
//        for (BoardManagerDO board : boardExtent) {
//            if (board.getPlayers().size() < board.getMaxPlayerNumber()) boards.add(board);
//        }
//        boardExtent.closeAll();

        modelMap.put("packages", packages);
//        modelMap.put("boards", boards);
        return "game/list";
    }

    @RequestMapping(value = "/game/board.*", method = {RequestMethod.GET, RequestMethod.POST})
    public String board(ModelMap modelMap) throws Exception {
        String playerName = getPlayerId();
        getLog().fine("Get game board information: " + playerName);
        BoardManagerDO boardManager = getBoardManager();
        if (boardManager == null) {
            return list(modelMap);
        }
        modelMap.put("board", boardManager);
        modelMap.put("playerName", playerName);
        modelMap.put("players", boardManager.getPlayers(playerName));
        modelMap.put("actions", boardManager.getActions());
        return "game/board";
    }

    @RequestMapping(value = "/game/new.*", method = {RequestMethod.GET, RequestMethod.POST})
    public void newGame(@RequestParam("packageId") long packageId,
                        @RequestParam(value = "maxPlayerNumber", required = false, defaultValue = "2")
                        int maxPlayerNumber,
                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        PlayerRecordDO player = getPlayer();
        if (player != null) {
            quit();
        }
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        BoardManagerDO board = new BoardManagerDO(maxPlayerNumber);
        Key boardManagerKey = pm.makePersistent(board).getKey();
        PersistenceHelper.doTransaction();

        BoardPackageDO boardPackage = new BoardPackageDO(boardManagerKey, getPackage(packageId));
        PersistenceHelper.doTransaction();

        board.init(boardPackage);
        pm.makePersistent(board);

        join(board);
        redirect("board", request, response);
    }

    @RequestMapping(value = "/game/join.*", method = {RequestMethod.GET, RequestMethod.POST})
    public void join(@RequestParam("packageId") long packageId,
                     @RequestParam("boardId") long boardId,
                     HttpServletRequest request, HttpServletResponse response) throws Exception {
        getLog().fine("Join game for " + getPlayerId());

        PlayerRecordDO player = getPlayer();
        if (player == null || player.getBoardManagerKey().getId() != boardId) {
            quit();
            join(getBoardManager(boardId));
        }

        redirect("board", request, response);
    }

    @RequestMapping(value = "/game/quit.*", method = {RequestMethod.GET, RequestMethod.POST})
    public void quit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        getLog().fine("Quit game for " + getPlayerId());

        quit();

        redirect("list", request, response);
    }

    protected void join(BoardManagerDO boardManager) {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        PlayerRecordDO player = (PlayerRecordDO) boardManager.addPlayer(getPlayerId(), INIT_HEALTH);
        pm.makePersistent(boardManager);
    }

    protected void quit() {
        BoardManagerDO boardManager = getBoardManager();

        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Query query = pm.newQuery(PlayerRecordDO.class);
        query.setFilter("uid == playerId");
        query.declareParameters("String playerId");
        query.deletePersistentAll(getPlayerId());

        if (boardManager != null && boardManager.getPlayers().size() < 2) {
            pm.deletePersistent(boardManager);
        }
        PersistenceHelper.doTransaction();
    }

}
