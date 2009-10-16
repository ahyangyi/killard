package com.killard.board.jdo.game;

import com.google.appengine.api.datastore.Key;
import com.killard.board.card.BoardPackage;
import com.killard.board.card.Card;
import com.killard.board.card.MetaCard;
import com.killard.board.card.Player;
import com.killard.board.card.action.BeginGameAction;
import com.killard.board.environment.BoardException;
import com.killard.board.environment.AbstractBoard;
import com.killard.board.environment.event.ActionEvent;
import com.killard.board.jdo.game.player.CardRecordDO;
import com.killard.board.jdo.game.player.ElementRecordDO;
import com.killard.board.jdo.game.player.PlayerRecordDO;
import com.killard.board.jdo.game.property.BoardPropertyDO;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.PackageDO;
import com.killard.board.jdo.board.RoleDO;
import com.killard.board.jdo.board.ElementSchoolDO;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.NotPersistent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Arrays;

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
    private List<String> roleNames;

    @Persistent
    private List<PlayerRecordDO> roundQueue;

    @Persistent(serialized = "true")
    private Set<Key> dealtCardKeys;

    @Persistent
    private SortedSet<BoardPropertyDO> properties;

    @Persistent
    private List<ActionDO> actions;

    @Persistent
    private List<MessageDO> messages;

    @Persistent
    private Date startDate;

    @NotPersistent
    private PackageDO gamePackage;

    public BoardDO(PackageDO gamePackage, int playerNumber) {
        this.packageKey = gamePackage.getKey();
        this.currentPlayerPosition = 0;
        this.roleNames = new ArrayList<String>(Arrays.asList(gamePackage.getRoleGroup(playerNumber).getRoleNames()));
        this.roundQueue = new ArrayList<PlayerRecordDO>();
        this.dealtCardKeys = new HashSet<Key>();
        this.properties = new TreeSet<BoardPropertyDO>();
        this.actions = new ArrayList<ActionDO>();
        this.messages = new ArrayList<MessageDO>();
        this.startDate = new Date();
        this.gamePackage = gamePackage;
    }

    public void restore() {
        this.gamePackage = PersistenceHelper.getPersistenceManager().getObjectById(PackageDO.class, packageKey);
        for (PlayerRecordDO player : roundQueue) player.restore(this);
        addActionListener(getBoardPackage().getRule(), this);
    }

    public PackageDO getBoardPackage() {
        return gamePackage;
    }

    public Key getKey() {
        return key;
    }

    public Date getStartDate() {
        return startDate;
    }

    public ActionDO[] getActions() {
        return actions.toArray(new ActionDO[actions.size()]);
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
    public Player addPlayer(String playerName, int health) throws BoardException {
        RoleDO role = gamePackage.getRoles().get(roleNames.get(roundQueue.size()));
        PlayerRecordDO player = new PlayerRecordDO(this, role, playerName, makeElementRecords());
        roundQueue.add(player);
        if (roleNames.size() == roundQueue.size()) executeAction(new BeginGameAction(this));
        return player;
    }

    public BoardPackage getPackage() {
        return gamePackage;
    }

    public int getPlayerAmount() {
        return gamePackage.getRoles().length;
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
        return new CardRecordDO((GameCardDO) metaCard, this, (PlayerRecordDO) owner, (PlayerRecordDO) target,
                cardPosition);
    }

    @Override
    protected void fireActionEventBefore(ActionEvent event) throws BoardException {
        actions.add(0, new ActionDO(this, event.getAction()));
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

    protected GameCardDO dealCard() {
        List<GameCardDO> cards = new ArrayList<GameCardDO>();
        int n = 0;
        for (GameElementSchoolDO elementSchool : getBoardPackage().getElementSchools()) {
            n += elementSchool.getCards().length;
        }
        if (n == dealtCardKeys.size()) dealtCardKeys.clear();
        for (GameElementSchoolDO elementSchool : getBoardPackage().getElementSchools()) {
            for (GameCardDO card : elementSchool.getCards()) {
                if (dealtCardKeys.contains(card.getKey())) continue;
                dealtCardKeys.add(card.getKey());
                cards.add(card);
            }
        }
        int index = (int) (cards.size() * Math.random());
        return cards.get(index);
    }

    protected List<ElementRecordDO> makeElementRecords() {
        List<ElementRecordDO> record = new LinkedList<ElementRecordDO>();
        for (ElementSchoolDO elementSchool : getBoardPackage().getElementSchools()) {
            record.add(new ElementRecordDO(elementSchool));
        }
        return record;
    }

    protected void randomSelect(List<GameCardDO> remainingCards, int cardAmount) {
        int size = remainingCards.size() - cardAmount;
        for (int i = 0; i < size; i++) {
            int index = (int) Math.floor(remainingCards.size() * Math.random());
            remainingCards.remove(index);
        }
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
}
