package com.killard.board.web.game;

import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.game.BoardManagerDO;
import com.killard.board.jdo.game.BoardPackageDO;
import com.killard.board.jdo.game.player.PlayerRecordDO;
import com.killard.board.jdo.board.PackageDO;
import com.killard.board.web.BasicController;
import com.killard.board.environment.BoardException;
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
        String playerId = getPlayerId();
        getLog().fine("Get game game list: " + playerId);

        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        List<PackageDO> packages = new LinkedList<PackageDO>();
        Extent<PackageDO> packageExtent = pm.getExtent(PackageDO.class);
        for (PackageDO pack : packageExtent) {
            if (pack.isOpen()) packages.add(pack);
        }
        packageExtent.closeAll();

//        List<BoardManagerDO> boards = new LinkedList<BoardManagerDO>();
//        Extent<BoardManagerDO> boardExtent = pm.getExtent(BoardManagerDO.class);
//        for (BoardManagerDO game : boardExtent) {
//            if (game.getPlayers().size() < game.getMaxPlayerNumber()) boards.add(game);
//        }
//        boardExtent.closeAll();

        modelMap.put("packages", packages);
//        modelMap.put("boards", boards);
        return "game/list";
    }

    @RequestMapping(value = "/game/board.*", method = {RequestMethod.GET, RequestMethod.POST})
    public String board(ModelMap modelMap) throws Exception {
        String playerId = getPlayerId();
        getLog().fine("Get game game information: " + playerId);
        BoardManagerDO boardManager = getBoardManager();
        if (boardManager == null) {
            return list(modelMap);
        }

        modelMap.put("game", boardManager);
        modelMap.put("playerId", playerId);
        modelMap.put("players", boardManager.getPlayers(playerId));
        modelMap.put("actions", boardManager.getActions());
        return "game/game";
    }

    @RequestMapping(value = "/game/new.*", method = {RequestMethod.GET, RequestMethod.POST})
    public void newGame(@RequestParam("packageId") long packageId,
                        @RequestParam(value = "playerNumber", required = false, defaultValue = "2") int playerNumber,
                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        PlayerRecordDO player = getPlayer();
        if (player != null) {
            quit();
        }
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        BoardManagerDO board = new BoardManagerDO();
        Key boardManagerKey = pm.makePersistent(board).getKey();
        PersistenceHelper.doTransaction();

        BoardPackageDO boardPackage = new BoardPackageDO(boardManagerKey, getPackage(packageId), playerNumber);
        PersistenceHelper.doTransaction();

        board.init(boardPackage);
        pm.makePersistent(board);

        join(board);
        redirect("game", request, response);
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

        redirect("game", request, response);
    }

    @RequestMapping(value = "/game/quit.*", method = {RequestMethod.GET, RequestMethod.POST})
    public void quit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        getLog().fine("Quit game for " + getPlayerId());

        quit();

        redirect("list", request, response);
    }

    protected void join(BoardManagerDO boardManager) throws BoardException {
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

        if (boardManager != null && boardManager.getPlayers().length < 2) {
            pm.deletePersistent(boardManager);
        }
        PersistenceHelper.doTransaction();
    }

}
