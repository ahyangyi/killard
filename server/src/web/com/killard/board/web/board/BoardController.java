package com.killard.board.web.board;

import com.killard.board.environment.BoardException;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.BoardDO;
import com.killard.board.jdo.board.PackageBundleDO;
import com.killard.board.jdo.board.PackageDO;
import com.killard.board.jdo.board.record.PlayerRecordDO;
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
public class BoardController extends BasicController {

    public static final int INIT_HEALTH = 50;

    @RequestMapping(value = "/packages.*", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        List<PackageDO> packages = new LinkedList<PackageDO>();
        Extent<PackageBundleDO> packageExtent = pm.getExtent(PackageBundleDO.class);
        for (PackageBundleDO bundle : packageExtent) {
            packages.add(bundle.getRelease());
        }
        packageExtent.closeAll();

        modelMap.put("packages", packages);
        return "record/list";
    }

    @RequestMapping(value = "/board.*", method = {RequestMethod.GET, RequestMethod.POST})
    public String board(ModelMap modelMap) throws Exception {
        String playerId = getPlayerId();
        getLog().fine("Get record record information: " + playerId);
        BoardDO board = getBoardManager();
        if (board == null) {
            return list(modelMap);
        }

        modelMap.put("board", board);
        modelMap.put("playerId", playerId);
        modelMap.put("players", board.getPlayers(playerId));
        modelMap.put("actions", board.getActions());
        return "record/board";
    }

    @RequestMapping(value = "/board/add.*", method = {RequestMethod.GET, RequestMethod.POST})
    public void addGame(@RequestParam("packageId") long packageId,
                        @RequestParam(value = "playerNumber", required = false, defaultValue = "2") int playerNumber,
                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        PlayerRecordDO player = getPlayer();
        if (player != null) {
            quit();
        }
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        PackageDO gamePackage = getPackage(packageId);

        pm.makePersistent(gamePackage);
        BoardDO board = new BoardDO(gamePackage, playerNumber);
        pm.makePersistent(board);

        join(board);
        redirect("/board", request, response);
    }

    @RequestMapping(value = "/board/join.*", method = {RequestMethod.GET, RequestMethod.POST})
    public void join(@RequestParam("packageBundleId") long packageBundleId,
                     @RequestParam("packageId") long packageId,
                     @RequestParam("boardId") long boardId,
                     HttpServletRequest request, HttpServletResponse response) throws Exception {
        getLog().fine("Join record for " + getPlayerId());

        PlayerRecordDO player = getPlayer();
        if (player == null || player.getBoardManagerKey().getId() != boardId) {
            quit();
            join(getBoardManager(packageBundleId, packageId, boardId));
        }

        redirect("/board", request, response);
    }

    @RequestMapping(value = "/board/quit.*", method = {RequestMethod.GET, RequestMethod.POST})
    public void quit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        getLog().fine("Quit record for " + getPlayerId());

        quit();

        redirect("/record/list", request, response);
    }

    @RequestMapping(value = "/board/playcard.*", method = {RequestMethod.GET, RequestMethod.POST})
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

    @RequestMapping(value = "/board/actions.*", method = {RequestMethod.GET, RequestMethod.POST})
    public String actions(ModelMap modelMap) throws Exception {
        BoardDO board = getBoardManager();
        modelMap.put("actions", board.getActions());
        return "action/result";
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