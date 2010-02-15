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
import javax.jdo.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;

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

    @RequestMapping(value = "/arena/*/*/image.png", method = RequestMethod.GET)
    public void cardImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = request.getRequestURI().split("/");
        String elementSchoolName = ids[2];
        String cardName = ids[3];

        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Key packageKey = getBoard().getPackageKey();
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
        BoardDO board = getBoard();
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
        PlayerRecordDO player = getPlayer();
        if (player != null) {
            quit();
        }
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
        PlayerRecordDO player = getPlayer();
        if (player == null || player.getBoardKey().getId() != boardId) {
            quit();
            join(getBoardManager(packageBundleId, boardId), -1);
        }

        redirect("/arena", request, response);
    }

    @RequestMapping(value = {"/arena/join.html", "/arena/join.xml"}, method = {RequestMethod.GET, RequestMethod.POST})
    public void join(@RequestParam("number") int number) throws Exception {
        getLog().fine("Join record for " + getPlayerId() + " at " + number);

        BoardDO board = getBoard();
        quit();
        join(board, number);
    }

    @RequestMapping(value = {"/arena/quit.html"}, method = {RequestMethod.GET, RequestMethod.POST})
    public void quit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        getLog().fine("Quit record for " + getPlayerId());

        quit();

        redirect("/packages", request, response);
    }

    @RequestMapping(value = {"/arena/board.json"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String board(ModelMap modelMap) throws Exception {
        String playerId = getPlayerId();
        BoardDO board = getBoard();
        ActionLogDO[] actions = board.getActions();
        modelMap.put("board", board);
        modelMap.put("players", board.getPlayers());
        modelMap.put("playerId", playerId);
        if (actions.length > 0) {
            modelMap.put("time", actions[actions.length - 1].getTime().getTime());
        } else {
            modelMap.put("time", 0);
        }
        return "arena/board";
    }

    @RequestMapping(value = {"/arena/cards.json"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String cards(ModelMap modelMap) throws Exception {
        modelMap.put("board", getBoard());
        modelMap.put("player", getPlayer());
        return "arena/dealtCards";
    }

    @RequestMapping(value = {"/arena/playcard.json"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String playCard(@RequestParam("elementSchoolName") String elementSchoolName,
                           @RequestParam("cardName") String cardName,
                           @RequestParam("cardPosition") int cardPosition,
                           @RequestParam("targetPosition") int targetPosition,
                           ModelMap modelMap) throws Exception {
        getLog().fine("Play card for " + getUser().getNickname() + " at " + cardName);
        BoardDO board = getBoard();
        if (getPlayer().getNumber() == board.getCurrentPlayerNumber()) {
            board.playCard(elementSchoolName, cardName, cardPosition, getPlayer().getNumber());
            PersistenceHelper.getPersistenceManager().makePersistent(board);
//            modelMap.put("actions", board.getActions());
        }
        return "arena/actions";
    }

    @RequestMapping(value = {"/arena/cast.html", "/arena/cast.xml"}, method = RequestMethod.POST)
    public String cast(@RequestParam("cardPosition") int cardPosition,
                       @RequestParam("skillIndex") int skillIndex, ModelMap modelMap) throws Exception {
        getLog().fine("Cast card for " + getUser().getNickname() + " at " + cardPosition);
        BoardDO board = getBoard();
        if (getPlayer().getNumber() == board.getCurrentPlayerNumber()) {
            board.cast(cardPosition, skillIndex);
            PersistenceHelper.getPersistenceManager().makePersistent(board);
//            modelMap.put("actions", board.getActions());
        }
        return "arena/actions";
    }

    @RequestMapping(value = {"/arena/endturn.html", "/arena/endturn.xml"},
            method = {RequestMethod.GET, RequestMethod.POST})
    public String endTurn(ModelMap modelMap) throws Exception {
        getLog().fine("End turn for " + getUser().getNickname());
        BoardDO board = getBoard();
        if (getPlayer().getNumber() == board.getCurrentPlayerNumber()) {
            board.endTurn();
            PersistenceHelper.getPersistenceManager().makePersistent(board);
//            modelMap.put("actions", board.getActions());
        }
        return "arena/actions";
    }

    @RequestMapping(value = {"/arena/endcall.html", "/arena/endcall.xml"},
            method = {RequestMethod.GET, RequestMethod.POST})
    public String endCall(ModelMap modelMap) throws Exception {
        getLog().fine("End call for " + getUser().getNickname());
        BoardDO board = getBoard();
        if (getPlayer().getNumber() == board.getCurrentPlayerNumber()) {
            board.endCall();
            PersistenceHelper.getPersistenceManager().makePersistent(board);
//            modelMap.put("actions", board.getActions());
        }
        return "arena/actions";
    }

    @RequestMapping(value = {"/arena/actions.html", "/arena/actions.xml", "/arena/actions.json"},
            method = {RequestMethod.GET, RequestMethod.POST})
    public String actions(
            @RequestParam(value = "lastUpdatedTime", required = false, defaultValue = "0") long lastUpdatedTime,
            ModelMap modelMap) throws Exception {
        String playerId = getPlayerId();
        PlayerCache playerCache = getPlayerCache();

        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Query query = pm.newQuery(ActionLogDO.class);
        query.setFilter("boardKey == bKey && time > lastUpdatedTime");
        query.setOrdering("time ascending");
        query.declareParameters("com.google.appengine.api.datastore.Key bKey, java.util.Date lastUpdatedTime");
        Collection actions = (Collection) query.execute(playerCache.getBoardKey(), new Date(lastUpdatedTime));

        modelMap.put("playerId", playerId);
        modelMap.put("actions", actions);
        modelMap.put("lastUpdatedTime", lastUpdatedTime);
        return "arena/actions";
    }

    protected void join(BoardDO board, int number) throws BoardException {
        PlayerRecordDO player = (PlayerRecordDO) board.addPlayer(getPlayerId(), getUser().getNickname(), number);
        PersistenceHelper.getPersistenceManager().makePersistent(board);
        PersistenceHelper.doTransaction();

        //TODO
        if (number > 0) {
            board.test();
            PersistenceHelper.getPersistenceManager().makePersistent(board);
            PersistenceHelper.doTransaction();
        }
    }

    protected void quit() {
        BoardDO board = getBoard();
        if (board != null) {
            PlayerCache playerCache = (PlayerCache) CacheInstance.getInstance().getCache().remove(getPlayerId());
            PersistenceManager pm = PersistenceHelper.getPersistenceManager();
            PlayerRecordDO player = pm.getObjectById(PlayerRecordDO.class, playerCache.getPlayerKey());
            try {
                board.removePlayer(player);
            } catch (BoardException ignored) {
            }
            pm.deletePersistent(player);
            PersistenceHelper.doTransaction();

            if (board.getPlayers().length == 0) {
                pm.deletePersistent(board);
                PersistenceHelper.doTransaction();
            }
        }
    }

}
