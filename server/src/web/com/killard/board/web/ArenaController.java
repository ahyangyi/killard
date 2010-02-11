package com.killard.board.web;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;
import com.killard.board.environment.BoardException;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.BoardDO;
import com.killard.board.jdo.board.ElementSchoolDO;
import com.killard.board.jdo.board.MetaCardDO;
import com.killard.board.jdo.board.PackageDO;
import com.killard.board.jdo.board.record.ActionLogDO;
import com.killard.board.jdo.board.record.PlayerRecordDO;
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
            byte[] data = card.getImageData();
            ImagesService imagesService = ImagesServiceFactory.getImagesService();
            Image oldImage = ImagesServiceFactory.makeImage(data);
            Transform resize = ImagesServiceFactory.makeResize(171, 264);
            Image cardImage = imagesService.applyTransform(resize, oldImage);
            response.getOutputStream().write(cardImage.getImageData());
        } else {
            throw new IOException("This board has no image.");
        }
    }

    @RequestMapping(value = "/arena/deal.*", method = {RequestMethod.GET, RequestMethod.POST})
    public void deal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        BoardDO board = getBoard();
        PlayerRecordDO player = (PlayerRecordDO) board.getCurrentPlayer();
    }

    @RequestMapping(value = "/arena.*", method = {RequestMethod.GET, RequestMethod.POST})
    public String arena(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        String playerId = getPlayerId();
//        getLog().fine("Get record record information: " + playerId);
//        BoardDO board = getBoard();
//
//        if (board == null) {
//            redirect("/packages", request, response);
//            return null;
//        }
//
//        modelMap.put("board", board);
//        modelMap.put("playerId", playerId);
//        modelMap.put("players", board.getPlayers(playerId));
//        modelMap.put("actions", board.getActions());
        return "arena";
    }

    @RequestMapping(value = "/arena/new.*", method = {RequestMethod.GET, RequestMethod.POST})
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

        join(board, 1);
        redirect("/arena", request, response);
    }

    @RequestMapping(value = "/arena/enter.*", method = {RequestMethod.GET, RequestMethod.POST})
    public void enter(@RequestParam("packageBundleId") long packageBundleId,
                      @RequestParam("boardId") long boardId,
                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        PlayerRecordDO player = getPlayer();
        if (player == null || player.getBoardManagerKey().getId() != boardId) {
            quit();
            join(getBoardManager(packageBundleId, boardId), 1);
        }

        redirect("/arena", request, response);
    }

    @RequestMapping(value = "/arena/join.*", method = {RequestMethod.GET, RequestMethod.POST})
    public void join(@RequestParam("packageBundleId") long packageBundleId,
                     @RequestParam("boardId") long boardId,
                     @RequestParam("number") int number,
                     HttpServletRequest request, HttpServletResponse response) throws Exception {
        getLog().fine("Join record for " + getPlayerId());

        PlayerRecordDO player = getPlayer();
        if (player == null || player.getBoardManagerKey().getId() != boardId) {
            quit();
            join(getBoardManager(packageBundleId, boardId), number);
        }

        redirect("/arena", request, response);
    }

    @RequestMapping(value = "/arena/quit.*", method = {RequestMethod.GET, RequestMethod.POST})
    public void quit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        getLog().fine("Quit record for " + getPlayerId());

        quit();

        redirect("/packages", request, response);
    }

    @RequestMapping(value = "/arena/board.json", method = {RequestMethod.GET, RequestMethod.POST})
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

    @RequestMapping(value = "/arena/cards.json", method = {RequestMethod.GET, RequestMethod.POST})
    public String cards(ModelMap modelMap) throws Exception {
        modelMap.put("board", getBoard());
        modelMap.put("player", getPlayer());
        return "arena/dealtCards";
    }

    @RequestMapping(value = "/arena/playcard.json", method = {RequestMethod.GET, RequestMethod.POST})
    public String playCard(@RequestParam("elementSchoolName") String elementSchoolName,
                           @RequestParam("cardName") String cardName,
                           @RequestParam("cardPosition") int cardPosition,
                           @RequestParam("targetPosition") int targetPosition,
                           ModelMap modelMap) throws Exception {
        BoardDO board = getBoard();
        board.playCard(elementSchoolName, cardName, cardPosition, targetPosition);
        PersistenceHelper.getPersistenceManager().makePersistent(board);
        modelMap.put("actions", board.getActions());
        return "arena/actions";
    }

    @RequestMapping(value = "/arena/cast.*", method = RequestMethod.POST)
    public String cast(@RequestParam("cardPosition") int cardPosition,
                       @RequestParam("skillIndex") int skillIndex, ModelMap modelMap) throws Exception {
        BoardDO board = getBoard();
        board.cast(cardPosition, skillIndex);
        PersistenceHelper.getPersistenceManager().makePersistent(board);
        modelMap.put("actions", board.getActions());
        return "arena/actions";
    }

    @RequestMapping(value = "/arena/endturn.*", method = {RequestMethod.GET, RequestMethod.POST})
    public String endTurn(ModelMap modelMap) throws Exception {
        BoardDO board = getBoard();
        board.endTurn();
        PersistenceHelper.getPersistenceManager().makePersistent(board);
        modelMap.put("actions", board.getActions());
        return "arena/actions";
    }

    @RequestMapping(value = "/arena/endcall.*", method = {RequestMethod.GET, RequestMethod.POST})
    public String endCall(ModelMap modelMap) throws Exception {
        BoardDO board = getBoard();
        board.endCall();
        PersistenceHelper.getPersistenceManager().makePersistent(board);
        modelMap.put("actions", board.getActions());
        return "arena/actions";
    }

    @RequestMapping(value = "/arena/actions.*", method = {RequestMethod.GET, RequestMethod.POST})
    public String actions(@RequestParam(value = "lastUpdatedTime", required = false, defaultValue = "0") long lastUpdatedTime,
                          ModelMap modelMap) throws Exception {
        String playerId = getPlayerId();
        BoardDO board = getBoard();
        ActionLogDO[] actions = board.getActions();
        long time = actions[actions.length - 1].getTime().getTime();
        modelMap.put("playerId", playerId);
        modelMap.put("board", board);
        modelMap.put("actions", actions);
        modelMap.put("lastUpdatedTime", lastUpdatedTime);
        modelMap.put("time", time);
        return "arena/actions";
    }

    protected void join(BoardDO board, int number) throws BoardException {
        PlayerRecordDO player = (PlayerRecordDO) board.addPlayer(getPlayerId(), getUser().getNickname(), number);
        PersistenceHelper.getPersistenceManager().makePersistent(board);
        PersistenceHelper.doTransaction();

        //TODO
        board.test();
        PersistenceHelper.getPersistenceManager().makePersistent(board);
        PersistenceHelper.doTransaction();
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
