package com.killard.board.xmpp;

import com.google.appengine.api.xmpp.JID;
import com.google.appengine.api.xmpp.Message;
import com.google.appengine.api.xmpp.MessageBuilder;
import com.google.appengine.api.xmpp.SendResponse;
import com.google.appengine.api.xmpp.XMPPService;
import com.google.appengine.api.xmpp.XMPPServiceFactory;
import com.killard.board.environment.BoardException;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.BoardDO;
import com.killard.board.web.BasicController;
import com.killard.board.web.BoardController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
public class XmppController extends BasicController {

    public XmppController() {
    }

    @RequestMapping(value = "/_ah/xmpp/message/chat/", method = RequestMethod.POST)
    public void xmpp(HttpServletRequest request, HttpServletResponse response) throws Exception {
        XMPPService xmpp = XMPPServiceFactory.getXMPPService();
        Message message = xmpp.parseMessage(request);
        try {
            handleMessage(message);
        } catch (RuntimeException e) {
            error(message.getFromJid(), e);
            throw e;
        } catch (Exception e) {
            error(message.getFromJid(), e);
            throw e;
        }
    }

    protected void sendMessage(String to, String body) {
        XMPPService xmpp = XMPPServiceFactory.getXMPPService();
        JID jid = new JID(to);
        Message msg = new MessageBuilder()
            .withRecipientJids(jid)
            .withBody(body)
            .build();

        boolean messageSent = false;
        if (xmpp.getPresence(jid).isAvailable()) {
            SendResponse status = xmpp.sendMessage(msg);
            messageSent = (status.getStatusMap().get(jid) == SendResponse.Status.SUCCESS);
        }
        getLog().fine("Send message : " + messageSent);
    }

    protected void handleMessage(Message message) {
        JID from = message.getFromJid();
        String[] args = message.getBody().split("\\s");
//        if (args[0].equals("new")) newGame(from, args);
//        else if (args[0].equals("join")) join(from, args);
//        else if (args[0].equals("quit")) quit(from, args);
//        else if (args[0].equals("hold")) queryHoldedCards(from, args);
//        else if (args[0].equals("view")) queryLivingCards(from, args);
//        else if (args[0].equals("board")) queryLivingCard(from, args);
//        else if (args[0].equals("play")) playCard(from, args);
//        else if (args[0].equals("cast")) castCard(from, args);
//        else if (args[0].equals("end")) endTurn(from, args);
//        else error(from, args);
    }
//
//    protected void newGame(JID from, String[] args) {
//        PlayerRecordDO player = getPlayer(getUsername(from));
//        if (player != null) quitGame(from);
//        PersistenceHelper.doTransaction();
//        int packageId = Integer.parseInt(args[1]);
//        PackageDO packageDO = PersistenceHelper.getPersistenceManager().getObjectById(PackageDO.class, packageId);
//        BoardDO boardManager = new BoardDO(packageDO, 2);
//        joinGame(from, boardManager);
//        sendMessage(getUsername(from), "You have created a new record.");
//    }
//
//    protected void join(JID from, String[] args) {
//        if (args.length < 2) throw new IllegalArgumentException();
//        PlayerRecordDO player = getPlayer(args[1]);
//        if (player == null) throw new IllegalArgumentException();
//        BoardDO boardManager = getBoardManager(player.getId());
//        joinGame(from, boardManager);
//        for (Player p : boardManager.getPlayers()) {
//            sendMessage(p.getId(), getUsername(from) + " joined record.");
//        }
//    }
//
//    protected void quit(JID from, String[] args) {
//        quitGame(from);
//    }
//
//    protected void queryHoldedCards(JID from, String[] args) {
//        PlayerRecordDO player = getPlayer(getUsername(from));
//        if (player == null) throw new IllegalArgumentException(getUsername(from) + " has not joined any record.");
//        for (ElementSchool elementSchool : player.getAllElementSchool()) {
//            StringBuilder msg = new StringBuilder("You has these cards in element " + elementSchool.toString() + " : ");
//            for (MetaCard board : player.getDealtCards(elementSchool)) {
//                msg.append("Level " + board.getLevel() + " " + board + ", ");
//            }
//            msg.append("and " + player.getElementAmount(elementSchool) + " elements.");
//            sendMessage(getUsername(from), msg.toString());
//        }
//    }
//
//    protected void queryLivingCards(JID from, String[] args) {
//        PlayerRecordDO player;
//        if (args.length == 1) player = getPlayer(getUsername(from));
//        else player = getPlayer(args[1]);
//        if (player == null) throw new IllegalArgumentException(getUsername(from) + " has not joined any record.");
//        for (Card board : player.getEquippedCards()) {
//            sendMessage(getUsername(from),
//                    "[" + board.getPosition() + "] " + board + " level " + board.getLevel() + " health "
//                            + board.getHealth() + " attack " + board.getAttack().getData());
//        }
//    }
//
//    protected void queryLivingCard(JID from, String[] args) {
//        PlayerRecordDO player = getPlayer(getUsername(from));
//        if (player == null) throw new IllegalArgumentException(getUsername(from) + " has not joined any record.");
//    }
//
//    protected void playCard(JID from, String[] args) {
//        PlayerRecordDO player = getPlayer(getUsername(from));
//        if (player == null) throw new IllegalArgumentException(getUsername(from) + " has not joined any record.");
//        BoardDO boardManager = getBoardManager(player.getId());
//        if (!boardManager.getCurrentPlayer().getId().equals(player.getId()))
//            throw new IllegalArgumentException("This is not your turn.");
//        int cardIndex = Integer.parseInt(args[1]);
//        int cardPosition = Integer.parseInt(args[2]);
//        int targetPosition = 1;
//        if (args.length > 3) {
//            targetPosition = Integer.parseInt(args[3]);
//        }
//        boardManager.playCard(cardIndex, cardPosition, targetPosition);
//        PersistenceHelper.getPersistenceManager().makePersistent(boardManager);
//        for (Player p : boardManager.getPlayers()) {
//            sendMessage(p.getId(), player.getId() + " plays board at " + cardPosition);
//        }
//    }
//
//    protected void castCard(JID from, String[] args) {
//        PlayerRecordDO player = getPlayer(getUsername(from));
//        if (player == null) throw new IllegalArgumentException(getUsername(from) + " has not joined any record.");
//        BoardDO boardManager = getBoardManager(player.getId());
//        if (!boardManager.getCurrentPlayer().getId().equals(player.getId()))
//            throw new IllegalArgumentException("This is not your turn.");
//        int cardPosition = Integer.parseInt(args[1]);
//        if (args.length == 2) {
//            boardManager.cast(cardPosition, 0);
//        } else {
//            int skillPosition = Integer.parseInt(args[2]);
//            int targetPlayerPosition = Integer.parseInt(args[3]);
//            int targetCardPosition = Integer.parseInt(args[4]);
//            boardManager.cast(cardPosition, skillPosition, targetPlayerPosition, targetCardPosition);
//        }
//        PersistenceHelper.getPersistenceManager().makePersistent(boardManager);
//        for (Player p : boardManager.getPlayers()) {
//            sendMessage(p.getId(), player.getId() + " cast board at " + cardPosition);
//        }
//    }
//
//    protected void endTurn(JID from, String[] args) {
//        PlayerRecordDO player = getPlayer(getUsername(from));
//        if (player == null) throw new IllegalArgumentException(getUsername(from) + " has not joined any record.");
//        BoardDO boardManager = getBoardManager(player.getId());
//        if (!boardManager.getCurrentPlayer().getId().equals(player.getId()))
//            throw new IllegalArgumentException("This is not your turn.");
//        boardManager.endTurn();
//        PersistenceHelper.getPersistenceManager().makePersistent(boardManager);
//    }

    protected void error(JID from, String[] args) {
        sendMessage(getUsername(from), "Error.");
    }

    protected void error(JID from, Exception e) {
        sendMessage(getUsername(from), "Error. (" + e.getMessage() + ")");
    }

    protected void joinGame(JID from, BoardDO board) throws BoardException {
        board.addPlayer(getUsername(from), BoardController.INIT_HEALTH);
        PersistenceHelper.getPersistenceManager().makePersistent(board);
    }

    protected void quitGame(JID from) {
//        BoardDO boardManager = getBoardManager(getUsername(from));
//
//        if (boardManager != null) {
//            for (Player p : boardManager.getPlayers()) {
//                sendMessage(p.getId(), getUsername(from) + " quit record.");
//            }
//        }
//
//        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
//        Query query = pm.newQuery(PlayerRecordDO.class);
//        query.setFilter("name == playerName");
//        query.declareParameters("String playerName");
//        query.deletePersistentAll(getUsername(from));
//
//        if (boardManager != null && boardManager.getPlayers().size() < 2) {
//            pm.deletePersistent(boardManager);
//        }
    }

    protected String getUsername(JID jid) {
        String id = jid.getId();
        if (id.contains("/")) return id.substring(0, id.indexOf("/"));
        else return id;
    }
}
