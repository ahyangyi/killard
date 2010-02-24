package com.killard.board.jdo.board;

import com.google.appengine.api.datastore.Key;
import com.killard.board.card.BoardPackage;
import com.killard.board.card.Card;
import com.killard.board.card.ElementSchool;
import com.killard.board.card.MetaCard;
import com.killard.board.card.Player;
import com.killard.board.card.action.BeginGameAction;
import com.killard.board.card.action.BeginTurnAction;
import com.killard.board.card.action.DealCardAction;
import com.killard.board.card.action.PlayerJoinAction;
import com.killard.board.card.action.PlayerQuitAction;
import com.killard.board.environment.AbstractBoard;
import com.killard.board.environment.BoardException;
import com.killard.board.environment.event.ActionEvent;
import com.killard.board.jdo.board.property.BoardPropertyDO;
import com.killard.board.jdo.board.record.ActionLogDO;
import com.killard.board.jdo.board.record.CardRecordDO;
import com.killard.board.jdo.board.record.ElementRecordDO;
import com.killard.board.jdo.board.record.MessageDO;
import com.killard.board.jdo.board.record.PlayerRecordDO;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class BoardDO extends AbstractBoard<BoardDO> {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    @Extension(vendorName="datanucleus", key="gae.parent-pk", value="true")
    private Key packageKey;

    @Persistent
    private String creator;

    @Persistent
    private int currentPlayerNumber;

    @Persistent(defaultFetchGroup = "true")
    private List<Key> roleNames;

    @Persistent(defaultFetchGroup = "false")
    private List<PlayerRecordDO> players;

    @Persistent(defaultFetchGroup = "true")
    private Set<Key> dealtCardKeys;

    @Persistent
    private SortedSet<BoardPropertyDO> properties;

    @Persistent(defaultFetchGroup = "false")
    private List<ActionLogDO> actionLogs;

    @Persistent(defaultFetchGroup = "false")
    private List<MessageDO> messages;

    @Persistent
    private Date startDate;

    @NotPersistent
    private PackageDO boardPackage;

    public BoardDO(PackageDO boardPackage, String creator, int playerNumber) {
        this.packageKey = boardPackage.getKey();
        this.creator = creator;
        this.currentPlayerNumber = 1;
        this.roleNames = new ArrayList<Key>(Arrays.asList(boardPackage.getRoleGroup(playerNumber).getRoleKeys()));
        this.players = new LinkedList<PlayerRecordDO>();
        this.dealtCardKeys = new HashSet<Key>();
        this.properties = new TreeSet<BoardPropertyDO>();
        this.actionLogs = new ArrayList<ActionLogDO>();
        this.messages = new ArrayList<MessageDO>();
        this.startDate = new Date();
        this.boardPackage = boardPackage;
    }

    public void restore(PackageDO boardPackage) {
        this.boardPackage = boardPackage;
        for (PlayerRecordDO player : players) player.restore(this);
        addActionListener(boardPackage.getRule(), this);
    }

    public PackageDO getBoardPackage() {
        return boardPackage;
    }

    public Key getKey() {
        return key;
    }

    public Key getPackageKey() {
        return packageKey;
    }

    public String getCreator() {
        return creator;
    }

    public int getCurrentPlayerNumber() {
        return currentPlayerNumber;
    }

    public Date getStartDate() {
        return startDate;
    }

    public ActionLogDO[] getActions() {
        return actionLogs.toArray(new ActionLogDO[actionLogs.size()]);
    }

    public ActionLogDO getLastActionLog() {
        return actionLogs.get(actionLogs.size() - 1);
    }

    public List<MessageDO> getMessages() {
        return Collections.unmodifiableList(messages);
    }

    public void postMessage(String from, String to, String message) {
        messages.add(new MessageDO(this, from, to, message));
    }

    public int compareTo(BoardDO boardDO) {
        return boardDO.getStartDate().compareTo(getStartDate());
    }

    public BoardPropertyDO[] getProperties() {
        return properties.toArray(new BoardPropertyDO[properties.size()]);
    }

    @Override
    public Player addPlayer(String playerId, String playerName, int number) throws BoardException {
        int count = 0;
        for (Player player : players) if (player.getNumber() > 0) count++;
        if (number > 0 && roleNames.size() == count) throw new BoardException("This board has enough players.");

        RoleDO role = boardPackage.getRoles().get(roleNames.get(count).getName());
        PlayerRecordDO player = new PlayerRecordDO(this, role, playerId, playerName, number, makeElementRecords());

        players.add(player);
        if (number > 0) {
            executeAction(new PlayerJoinAction(player));
            if (roleNames.size() == count + 1) {
                executeAction(new BeginGameAction(this));
                executeAction(new BeginTurnAction(this, getCurrentPlayer()));
            }
        }
        return player;
    }

    @Override
    public void removePlayer(Player player) throws BoardException {
        executeAction(new PlayerQuitAction(player));
    }

    public BoardPackage getPackage() {
        return boardPackage;
    }

    public int getPlayerAmount() {
        int count = 0;
        for (Player player : players) if (player.getNumber() > 0) count++;
        return count;
    }

    public Player[] getPlayers() {
        return players.toArray(new Player[players.size()]);
    }

    public Player getCurrentPlayer() {
        for (Player player : players) if (player.getNumber() == currentPlayerNumber) return player;
        throw new IllegalStateException("Can not access current player " + currentPlayerNumber);
    }

    public Player getNextPlayer() {
        for (Player player : players) if (player.getNumber() > currentPlayerNumber) return player;
        return players.get(0);
    }

    public Player getPreviousPlayer() {
        Player previous = null;
        Player last = null;
        for (Player player : players) {
            if (player.getNumber() > 0) {
                last = player;
                if (player.getNumber() < currentPlayerNumber) previous = player;
            }
        }
        return previous == null ? last : previous;
    }

    public Player getPlayer(int number) {
        for (Player player : players) if (player.getNumber() == number) return player;
        throw new IllegalArgumentException("Invalid player number " + number);
    }

    @Override
    protected Card createCardRecord(MetaCard metaCard, Player owner, Player target, int cardPosition) {
        return new CardRecordDO((MetaCardDO) metaCard, this, (PlayerRecordDO) owner, (PlayerRecordDO) target,
                cardPosition);
    }

    @Override
    protected void fireActionEventBefore(ActionEvent event) throws BoardException {
        super.fireActionEventBefore(event);
        actionLogs.add(new ActionLogDO(this, event.getAction()));
    }

    @Override
    protected void moveToNext() {
        currentPlayerNumber = getNextPlayer().getNumber();
    }

    protected MetaCardDO dealCard() {
        List<MetaCardDO> cards = new ArrayList<MetaCardDO>();
        int n = 0;
        for (ElementSchool elementSchool : getBoardPackage().getElementSchools()) {
            n += elementSchool.getCards().length;
        }
        if (n == dealtCardKeys.size()) dealtCardKeys.clear();
        for (ElementSchool elementSchool : getBoardPackage().getElementSchools()) {
            for (MetaCard card : elementSchool.getCards()) {
                MetaCardDO record = (MetaCardDO) card;
                if (dealtCardKeys.contains(record.getKey())) continue;
                dealtCardKeys.add(record.getKey());
                cards.add(record);
            }
        }
        int index = (int) (cards.size() * Math.random());
        return cards.get(index);
    }

    protected List<ElementRecordDO> makeElementRecords() {
        List<ElementRecordDO> record = new LinkedList<ElementRecordDO>();
        for (ElementSchool elementSchool : getBoardPackage().getElementSchools()) {
            record.add(new ElementRecordDO((ElementSchoolDO) elementSchool));
        }
        return record;
    }

    public Object getProperty(String name) {
        for (BoardPropertyDO property : getProperties()) {
            if (property.getName().equals(name)) return property.getData();
        }
        return null;
    }

    protected void setProperty(String name, Object data) {
        for (BoardPropertyDO property : getProperties()) {
            if (property.getName().equals(name)) {
                property.setData(data.toString());
                return;
            }
        }
        properties.add(new BoardPropertyDO(this, name, data.toString()));
    }

    public void test() throws BoardException {
        //TODO
        for (ElementSchool elementSchool : getBoardPackage().getElementSchools()) {
            for (MetaCard card : elementSchool.getCards()) {
                if (Math.random() > .6) {
                    if (players.size() == 1) executeAction(new DealCardAction(card, getCurrentPlayer()));
                    else executeAction(new DealCardAction(card, getNextPlayer()));
                }
            }
        }
    }
}
