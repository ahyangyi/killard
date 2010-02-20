package com.killard.board.web;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.environment.BoardException;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.BoardDO;
import com.killard.board.jdo.board.ElementSchoolDO;
import com.killard.board.jdo.board.MetaCardDO;
import com.killard.board.jdo.board.PackageDO;
import com.killard.board.jdo.board.record.ActionLogDO;
import com.killard.board.jdo.board.record.PlayerRecordDO;
import com.killard.board.web.cache.CacheInstance;
import com.killard.board.web.cache.PlayerCache;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
public class ArenaController extends BasicController {

    @RequestMapping(value = "/arena/*/*.png", method = RequestMethod.GET)
    public void cardImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = request.getRequestURI().split("/");
        String elementSchoolName = ids[2];
        String cardName = ids[3].substring(0, ids[3].indexOf("."));

        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Key packageKey = CacheInstance.getInstance().getBoard().getPackageKey();
        Key elementSchoolkey =
                KeyFactory.createKey(packageKey, ElementSchoolDO.class.getSimpleName(), elementSchoolName);
        Key cardKey = KeyFactory.createKey(elementSchoolkey, MetaCardDO.class.getSimpleName(), cardName);

        MetaCardDO card = pm.getObjectById(MetaCardDO.class, cardKey);
        response.setContentType("image/png");
        if (card.isRenderable()) {
            try {
                response.getOutputStream().write(card.getImageData());
            } catch (IOException ignored) {
            }
        } else {
            throw new IOException("This board has no image.");
        }
    }

    @RequestMapping(value = {"/arena/deal.html", "/arena/deal.xml"}, method = {RequestMethod.GET, RequestMethod.POST})
    public void deal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        BoardDO board = CacheInstance.getInstance().getBoard();
        PlayerRecordDO player = (PlayerRecordDO) board.getCurrentPlayer();
    }

    @RequestMapping(value = {"/arena.html", "/arena.xml"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String arena(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "arena";
    }

    @RequestMapping(value = {"/arena/new.html", "/arena/new.xml"}, method = {RequestMethod.GET, RequestMethod.POST})
    public void newGame(@RequestParam("packageBundleId") long packageBundleId,
                        @RequestParam(value = "playerNumber", required = false, defaultValue = "2") int playerNumber,
                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        getLog().fine("New game record for " + getUser().getNickname() + " package " + packageBundleId);
        PlayerRecordDO player = CacheInstance.getInstance().getPlayer();
        if (player != null) quit();
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        PackageDO gamePackage = getPackage(packageBundleId);
        BoardDO board = new BoardDO(gamePackage, getUser().getNickname(), playerNumber);
        pm.makePersistent(board);

        join(board, 1);
        redirect("/arena", request, response);
    }

    @RequestMapping(value = {"/arena/enter.html", "/arena/enter.xml"}, method = {RequestMethod.GET, RequestMethod.POST})
    public void enter(@RequestParam("packageBundleId") long packageBundleId,
                      @RequestParam("boardId") long boardId,
                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        getLog().fine("Enter record for " + getUser().getNickname());
        PlayerRecordDO player = CacheInstance.getInstance().getPlayer();
        if (player == null || player.getBoardKey().getId() != boardId) {
            quit();
            join(getBoardManager(packageBundleId, boardId), -1);
        }

        redirect("/arena", request, response);
    }

    @RequestMapping(value = {"/arena/join.html", "/arena/join.xml"}, method = {RequestMethod.GET, RequestMethod.POST})
    public void join(@RequestParam("number") int number) throws Exception {
        getLog().fine("Join record for " + getUser().getNickname() + " at " + number);

        BoardDO board = CacheInstance.getInstance().getBoard();
        quit();
        join(board, number);
    }

    @RequestMapping(value = {"/arena/quit.html"}, method = {RequestMethod.GET, RequestMethod.POST})
    public void quit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        getLog().fine("Quit record for " + getUser().getNickname());

        quit();

        redirect("/packages", request, response);
    }

    @RequestMapping(value = {"/arena/board.json"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String board(ModelMap modelMap) throws Exception {
        CacheInstance instance = CacheInstance.getInstance();
        String playerId = instance.getPlayerId();
        BoardDO board = instance.getBoard();
        modelMap.put("board", board);
        modelMap.put("players", board.getPlayers());
        modelMap.put("playerId", playerId);

        ActionLogDO[] actions = board.getActions();
        if (actions.length > 0) modelMap.put("time", actions[actions.length - 1].getTime().getTime());
        else modelMap.put("time", 0);

        return "arena/board";
    }

    @RequestMapping(value = {"/arena/playcard.html", "/arena/playcard.xml", "/arena/playcard.json"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String playCard(@RequestParam("elementSchoolName") String elementSchoolName,
                           @RequestParam("cardName") String cardName,
                           @RequestParam("cardPosition") int cardPosition,
                           @RequestParam("targetPosition") int targetPosition,
                           ModelMap modelMap) throws Exception {
        getLog().fine("Play card for " + getUser().getNickname() + " at " + cardName);
        BoardDO board = CacheInstance.getInstance().getBoard();
        if (CacheInstance.getInstance().getPlayer().getNumber() == board.getCurrentPlayerNumber()) {
            board.playCard(elementSchoolName, cardName, cardPosition, CacheInstance.getInstance().getPlayer().getNumber());
            PersistenceHelper.getPersistenceManager().makePersistent(board);
            logBoard(board);
//            modelMap.put("actions", board.getActions());
        }
        return "arena/actions";
    }

    @RequestMapping(value = {"/arena/cast.html", "/arena/cast.xml", "/arena/cast.json"}, method = RequestMethod.POST)
    public String cast(@RequestParam("cardPosition") int cardPosition,
                       @RequestParam("skillIndex") int skillIndex,
                       @RequestParam("target") String[] target,
                       ModelMap modelMap) throws Exception {
        getLog().fine("Cast card for " + getUser().getNickname() + " at " + cardPosition);
        BoardDO board = CacheInstance.getInstance().getBoard();
        if (CacheInstance.getInstance().getPlayer().getNumber() == board.getCurrentPlayerNumber()) {
            Object[] targets = new Object[target.length];
            for (int i = 0; i < target.length; i++) {
                targets[i] = null;
            }
            board.cast(cardPosition, skillIndex - 1, targets);
            PersistenceHelper.getPersistenceManager().makePersistent(board);
            logBoard(board);
//            modelMap.put("actions", board.getActions());
        }
        return "arena/actions";
    }

    @RequestMapping(value = {"/arena/endturn.html", "/arena/endturn.xml"},
            method = {RequestMethod.GET, RequestMethod.POST})
    public String endTurn(ModelMap modelMap) throws Exception {
        getLog().fine("End turn for " + getUser().getNickname());
        BoardDO board = CacheInstance.getInstance().getBoard();
        if (CacheInstance.getInstance().getPlayer().getNumber() == board.getCurrentPlayerNumber()) {
            board.endTurn();
            PersistenceHelper.getPersistenceManager().makePersistent(board);
            logBoard(board);
//            modelMap.put("actions", board.getActions());
        }
        return "arena/actions";
    }

    @RequestMapping(value = {"/arena/endcall.html", "/arena/endcall.xml"},
            method = {RequestMethod.GET, RequestMethod.POST})
    public String endCall(ModelMap modelMap) throws Exception {
        getLog().fine("End call for " + getUser().getNickname());
        BoardDO board = CacheInstance.getInstance().getBoard();
        if (CacheInstance.getInstance().getPlayer().getNumber() == board.getCurrentPlayerNumber()) {
            board.endCall();
            PersistenceHelper.getPersistenceManager().makePersistent(board);
            logBoard(board);
//            modelMap.put("actions", board.getActions());
        }
        return "arena/actions";
    }

    protected void join(BoardDO board, int number) throws BoardException {
        CacheInstance instance = CacheInstance.getInstance();
        PlayerRecordDO player = (PlayerRecordDO) board.addPlayer(instance.getPlayerId(), getUser().getNickname(), number);
        // It's important to do transaction immediately to make the Collection field persisted.
        PersistenceHelper.getPersistenceManager().makePersistent(board);
        PersistenceHelper.doTransaction();

        //TODO deal cards
        if (number > 0) {
            board.test();
            PersistenceHelper.getPersistenceManager().makePersistent(board);
            PersistenceHelper.doTransaction();
        }

        // log board to cache
        logBoard(board);
    }

    protected void quit() {
        CacheInstance instance = CacheInstance.getInstance();
        BoardDO board = instance.getBoard();
        if (board != null) {
            PlayerCache playerCache = (PlayerCache) instance.getCache().remove(instance.getPlayerId());
            PersistenceManager pm = PersistenceHelper.getPersistenceManager();
            PlayerRecordDO player = pm.getObjectById(PlayerRecordDO.class, playerCache.getPlayerKey());
            try {
                board.removePlayer(player);
            } catch (BoardException ignored) {
            }
            pm.deletePersistent(player);
            PersistenceHelper.doTransaction();

            if (board.getPlayers().length == 0) {
                CacheInstance.getInstance().getCache().remove(board.getKey());
                pm.deletePersistent(board);
                PersistenceHelper.doTransaction();
            } else {
                logBoard(board);
            }
        }
    }

    protected void logBoard(BoardDO board) {
        ActionLogDO[] actions = board.getActions();
        CacheInstance.getInstance().getCache().put(board.getKey(), actions[actions.length - 1].getTime().getTime());
    }

}
