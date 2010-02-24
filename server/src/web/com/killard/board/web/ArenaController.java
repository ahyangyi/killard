package com.killard.board.web;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.card.Skill;
import com.killard.board.card.SkillTarget;
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

        Key packageKey = CacheInstance.getInstance().getPlayerCache().getPackageKey();
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

    @RequestMapping(value = {"/arena.html", "/arena.xml"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String arena(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "arena";
    }

    @RequestMapping(value = {"/arena/new.html", "/arena/new.xml"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String newGame(@RequestParam("packageBundleId") long packageBundleId,
                        @RequestParam(value = "playerNumber", required = false, defaultValue = "2") int playerNumber,
                        HttpServletRequest request, HttpServletResponse response,
                        ModelMap modelMap) throws Exception {
        getLog().fine("New game record for " + getUser().getNickname() + " package " + packageBundleId);
        PlayerRecordDO player = CacheInstance.getInstance().getPlayer();
        if (player != null) quit();
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        PackageDO gamePackage = getPackage(packageBundleId);
        long timeStart = System.currentTimeMillis();
        BoardDO board = new BoardDO(gamePackage.getKey(), getUser().getNickname(), playerNumber);
        board = pm.makePersistent(board);
        PersistenceHelper.doTransaction();

        join(board, 1);
//        redirect("/arena", request, response);
        modelMap.put("time", System.currentTimeMillis() - timeStart);
        return "arena/new";
    }

    @RequestMapping(value = {"/arena/enter.html", "/arena/enter.xml"}, method = {RequestMethod.GET, RequestMethod.POST})
    public void enter(@RequestParam("packageBundleId") long packageBundleId,
                      @RequestParam("boardId") long boardId,
                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        getLog().fine("Enter record for " + getUser().getNickname());
        PlayerRecordDO player = CacheInstance.getInstance().getPlayer();
        if (player == null || player.getBoardKey().getId() != boardId) {
            quit();
            join(getBoard(packageBundleId, boardId), -1);
        }

        redirect("/arena", request, response);
    }

    @RequestMapping(value = {"/arena/join.html", "/arena/join.xml", "/arena/join.json"},
            method = {RequestMethod.GET, RequestMethod.POST})
    public void join(@RequestParam("number") int number, HttpServletResponse response) throws Exception {
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

    @RequestMapping(value = {"/arena/playcard.html", "/arena/playcard.xml", "/arena/playcard.json"},
            method = {RequestMethod.GET, RequestMethod.POST})
    public void playCard(@RequestParam("elementSchoolName") String elementSchoolName,
                           @RequestParam("cardName") String cardName,
                           @RequestParam("cardPosition") int cardPosition,
                           HttpServletResponse response) throws Exception {
        long startTime = System.currentTimeMillis();
        getLog().fine("Play card for " + getUser().getNickname() + " at " + cardName);
        CacheInstance instance = CacheInstance.getInstance();
        BoardDO board = instance.getBoard();
        response.getWriter().println("time1: " + (System.currentTimeMillis() - startTime) + "ms");
        startTime = System.currentTimeMillis();
        if (instance.getPlayer().getNumber() == board.getCurrentPlayerNumber()) {
            board.playCard(elementSchoolName, cardName, cardPosition, instance.getPlayer().getNumber());
            response.getWriter().println("time2: " + (System.currentTimeMillis() - startTime) + "ms");
            startTime = System.currentTimeMillis();
            logBoard(board);
        }
        response.getWriter().println("time3: " + (System.currentTimeMillis() - startTime) + "ms");
    }

    @RequestMapping(value = {"/arena/cast.html", "/arena/cast.xml", "/arena/cast.json"}, method = RequestMethod.POST)
    public void cast(@RequestParam("cardPosition") int cardPosition,
                       @RequestParam("skillIndex") int skillIndex,
                       @RequestParam("target[]") String[] target,
                       HttpServletResponse response) throws Exception {
        getLog().fine("Cast card for " + getUser().getNickname() + " at " + cardPosition);
        BoardDO board = CacheInstance.getInstance().getBoard();
        if (CacheInstance.getInstance().getPlayer().getNumber() == board.getCurrentPlayerNumber()) {
            Object[] targets = new Object[target.length];
            Skill skill = board.getCurrentPlayer().getEquippedCard(cardPosition).getSkills()[skillIndex - 1];
            SkillTarget[] skillTargets = skill.getTargets();
            for (int i = 0; i < target.length; i++) {
                if (skillTargets[i] == SkillTarget.self) {
                    targets[i] = board.getCurrentPlayer();
                } else if (skillTargets[i] == SkillTarget.other) {
                    targets[i] = board.getPlayer(Integer.parseInt(target[i]));
                } else if (skillTargets[i] == SkillTarget.owncard) {
                    targets[i] = board.getCurrentPlayer().getEquippedCard(Integer.parseInt(target[i]));
                } else if (skillTargets[i] == SkillTarget.otherscard) {
                    int p = target[i].indexOf(":");
                    int number = Integer.parseInt(target[i].substring(0, p));
                    int pos = Integer.parseInt(target[i].substring(p + 1));
                    targets[i] = board.getPlayer(number).getEquippedCard(pos);
                }
            }
            board.cast(cardPosition, skillIndex - 1, targets);
            logBoard(board);
        }
    }

    @RequestMapping(value = {"/arena/endturn.html", "/arena/endturn.xml", "/arena/endturn.json"},
            method = {RequestMethod.GET, RequestMethod.POST})
    public void endTurn(HttpServletResponse response) throws Exception {
        getLog().fine("End turn for " + getUser().getNickname());
        BoardDO board = CacheInstance.getInstance().getBoard();
        if (CacheInstance.getInstance().getPlayer().getNumber() == board.getCurrentPlayerNumber()) {
            board.endTurn();
            logBoard(board);
        }
    }

    @RequestMapping(value = {"/arena/endcall.html", "/arena/endcall.xml", "/arena/endcall.json"},
            method = {RequestMethod.GET, RequestMethod.POST})
    public void endCall(HttpServletResponse response) throws Exception {
        getLog().fine("End call for " + getUser().getNickname());
        BoardDO board = CacheInstance.getInstance().getBoard();
        if (CacheInstance.getInstance().getPlayer().getNumber() == board.getCurrentPlayerNumber()) {
            board.endCall();
            logBoard(board);
        }
    }

    protected void join(BoardDO board, int number) throws BoardException {
        CacheInstance instance = CacheInstance.getInstance();
        board.addPlayer(instance.getPlayerId(), getUser().getNickname(), number);
        // It's important to do transaction immediately to make the Collection field persisted.
        PersistenceHelper.doTransaction();

        //TODO deal cards
        if (number > 0) board.test();

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

            if (board.getPlayers().length == 1) {
                CacheInstance.getInstance().getCache().remove(board.getKey());
                pm.deletePersistent(board);
                PersistenceHelper.doTransaction();
            } else {
                logBoard(board);
            }
        }
    }

    protected void logBoard(BoardDO board) {
        PersistenceHelper.endTransaction();
        CacheInstance.getInstance().getCache().put(board.getKey(), board.getLastActionLog().getTime().getTime());
    }

}
