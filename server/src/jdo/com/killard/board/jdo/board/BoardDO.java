package com.killard.board.jdo.board;

import com.google.appengine.api.datastore.Key;
import com.killard.board.card.BoardPackage;
import com.killard.board.card.Card;
import com.killard.board.card.ElementSchool;
import com.killard.board.card.MetaCard;
import com.killard.board.card.Player;
import com.killard.board.card.action.BeginGameAction;
import com.killard.board.card.action.DealCardAction;
import com.killard.board.environment.AbstractBoard;
import com.killard.board.environment.BoardException;
import com.killard.board.environment.event.ActionEvent;
import com.killard.board.jdo.PersistenceHelper;
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
    private Integer currentPlayerPosition;

    @Persistent
    private List<Key> roleNames;

    @Persistent
    private List<PlayerRecordDO> roundQueue;

    @Persistent(serialized = "true")
    private Set<Key> dealtCardKeys;

    @Persistent
    private SortedSet<BoardPropertyDO> properties;

    @Persistent
    private List<ActionLogDO> actionLogs;

    @Persistent
    private List<MessageDO> messages;

    @Persistent
    private Date startDate;

    @NotPersistent
    private PackageDO boardPackage;

    public BoardDO(PackageDO boardPackage, int playerNumber) {
        this.packageKey = boardPackage.getKey();
        this.currentPlayerPosition = 0;
        this.roleNames = new ArrayList<Key>(Arrays.asList(boardPackage.getRoleGroup(playerNumber).getRoleKeys()));
        this.roundQueue = new ArrayList<PlayerRecordDO>();
        this.dealtCardKeys = new HashSet<Key>();
        this.properties = new TreeSet<BoardPropertyDO>();
        this.actionLogs = new ArrayList<ActionLogDO>();
        this.messages = new ArrayList<MessageDO>();
        this.startDate = new Date();
        this.boardPackage = boardPackage;
    }

    public void restore() {
        this.boardPackage = PersistenceHelper.getPersistenceManager().getObjectById(PackageDO.class, packageKey);
        for (PlayerRecordDO player : roundQueue) player.restore(this);
        addActionListener(getBoardPackage().getRule(), this);
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

    public Date getStartDate() {
        return startDate;
    }

    public ActionLogDO[] getActions() {
        return actionLogs.toArray(new ActionLogDO[actionLogs.size()]);
    }

    public List<Player> getPlayers(String playerName) {
        List<Player> players = new ArrayList<Player>();
        int position;
        for (position = 0; position < roundQueue.size(); position++)
            if (roundQueue.get(position).getId().equals(playerName)) break;
        position = position - currentPlayerPosition;
        if (position < 0) position = roundQueue.size() + position;
        for (int i = 0; i < roundQueue.size(); i++)
            players.add(roundQueue.get(getPlayerPosition(position + i)));
        return players;
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
    public Player addPlayer(String playerName) throws BoardException {
        RoleDO role = boardPackage.getRoles().get(roleNames.get(roundQueue.size()).getName());
        PlayerRecordDO player = new PlayerRecordDO(this, role, playerName, makeElementRecords());

        roundQueue.add(player);
        if (roleNames.size() == roundQueue.size()) executeAction(new BeginGameAction(this));
        return player;
    }

    public BoardPackage getPackage() {
        return boardPackage;
    }

    public int getPlayerAmount() {
        return boardPackage.getRoles().size();
    }

    public Player[] getPlayers() {
        int n = roundQueue.size();
        Player[] players = new Player[n];
        for (int i = currentPlayerPosition; i < n; i++) players[i] = roundQueue.get(i);
        for (int i = 0; i < currentPlayerPosition; i++) players[n - currentPlayerPosition + i] = roundQueue.get(i);
        return players;
    }

    public Player getCurrentPlayer() {
        return roundQueue.get(getCurrentPlayerPosition());
    }

    public Player getNextPlayer() {
        return roundQueue.get(getPlayerPosition(1));
    }

    public Player getPreviousPlayer() {
        return roundQueue.get(getPlayerPosition(-1));
    }

    public Player getPlayer(int position) {
        return roundQueue.get(getPlayerPosition(position));
    }

    @Override
    protected Card createCardRecord(MetaCard metaCard, Player owner, Player target, int cardPosition) {
        return new CardRecordDO((MetaCardDO) metaCard, this, (PlayerRecordDO) owner, (PlayerRecordDO) target,
                cardPosition);
    }

    @Override
    protected void fireActionEventBefore(ActionEvent event) throws BoardException {
        actionLogs.add(0, new ActionLogDO(this, event.getAction()));
        super.fireActionEventBefore(event);
    }

    @Override
    protected void moveToNext() {
        do {
            currentPlayerPosition = getCurrentPlayerPosition() + 1;
            if (currentPlayerPosition == roundQueue.size()) currentPlayerPosition = 0;
        } while (getCurrentPlayer().getHealth() == 0);
    }

    protected int getPlayerPosition(int position) {
        int r = getCurrentPlayerPosition() + position;
        return r >= roundQueue.size() ? r - roundQueue.size() : r;
    }

    protected int getCurrentPlayerPosition() {
        if (currentPlayerPosition == roundQueue.size()) currentPlayerPosition--;
        return currentPlayerPosition;
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
                    if (roundQueue.size() == 1) executeAction(new DealCardAction(card, getCurrentPlayer()));
                    else executeAction(new DealCardAction(card, getNextPlayer()));
                }
            }
        }
    }

    protected void randomSelect(List<MetaCardDO> remainingCards, int cardAmount) {
        int size = remainingCards.size() - cardAmount;
        for (int i = 0; i < size; i++) {
            int index = (int) Math.floor(remainingCards.size() * Math.random());
            remainingCards.remove(index);
        }
    }
}
