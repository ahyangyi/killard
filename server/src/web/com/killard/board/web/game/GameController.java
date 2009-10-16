package com.killard.board.web.game;

import com.killard.board.environment.BoardException;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.BoardDO;
import com.killard.board.jdo.board.game.PlayerRecordDO;
import com.killard.board.jdo.game.GamePackageDO;
import com.killard.board.web.BasicController;
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
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        List<GamePackageDO> packages = new LinkedList<GamePackageDO>();
        Extent<GamePackageDO> packageExtent = pm.getExtent(GamePackageDO.class);
        for (GamePackageDO pack : packageExtent) {
            packages.add(pack);
        }
        packageExtent.closeAll();

        modelMap.put("packages", packages);
        return "game/list";
    }

    @RequestMapping(value = "/board.*", method = {RequestMethod.GET, RequestMethod.POST})
    public String board(ModelMap modelMap) throws Exception {
        String playerId = getPlayerId();
        getLog().fine("Get game game information: " + playerId);
        BoardDO board = getBoardManager();
        if (board == null) {
            return list(modelMap);
        }

        modelMap.put("board", board);
        modelMap.put("playerId", playerId);
        modelMap.put("players", board.getPlayers(playerId));
        modelMap.put("actions", board.getActions());
        return "game/board";
    }

    @RequestMapping(value = "/board/add.*", method = {RequestMethod.GET, RequestMethod.POST})
    public void addGame(@RequestParam("packageId") long packageId,
                        @RequestParam("gamePackageId") long gamePackageId,
                        @RequestParam(value = "playerNumber", required = false, defaultValue = "2") int playerNumber,
                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        PlayerRecordDO player = getPlayer();
        if (player != null) {
            quit();
        }
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        GamePackageDO gamePackage = getGamePackage(packageId, gamePackageId);

        pm.makePersistent(gamePackage);
        BoardDO board = new BoardDO(gamePackage, playerNumber);
        pm.makePersistent(board);

        join(board);
        redirect("/board", request, response);
    }

    @RequestMapping(value = "/board/join.*", method = {RequestMethod.GET, RequestMethod.POST})
    public void join(@RequestParam("packageId") long packageId,
                     @RequestParam("boardId") long boardId,
                     HttpServletRequest request, HttpServletResponse response) throws Exception {
        getLog().fine("Join game for " + getPlayerId());

        PlayerRecordDO player = getPlayer();
        if (player == null || player.getBoardManagerKey().getId() != boardId) {
            quit();
            join(getBoardManager(boardId));
        }

        redirect("/board", request, response);
    }

    @RequestMapping(value = "/board/quit.*", method = {RequestMethod.GET, RequestMethod.POST})
    public void quit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        getLog().fine("Quit game for " + getPlayerId());

        quit();

        redirect("/game/list", request, response);
    }

    protected void join(BoardDO board) throws BoardException {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        PlayerRecordDO player = (PlayerRecordDO) board.addPlayer(getPlayerId(), INIT_HEALTH);
        pm.makePersistent(board);
    }

    protected void quit() {
        BoardDO board = getBoardManager();

        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Query query = pm.newQuery(PlayerRecordDO.class);
        query.setFilter("uid == playerId");
        query.declareParameters("String playerId");
        query.deletePersistentAll(getPlayerId());

        if (board != null && board.getPlayers().length < 2) {
            pm.deletePersistent(board);
        }
        PersistenceHelper.doTransaction();
    }

}
