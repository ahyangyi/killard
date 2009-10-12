package com.killard.jdo.board;

import com.google.appengine.api.datastore.Key;
import com.killard.card.Card;
import com.killard.card.CardInstance;
import com.killard.card.ElementSchool;
import com.killard.card.Player;
import com.killard.card.BoardPackage;
import com.killard.environment.BoardManager;
import com.killard.environment.event.ActionEvent;
import com.killard.jdo.board.player.CardRecordDO;
import com.killard.jdo.board.player.ElementRecordDO;
import com.killard.jdo.board.player.PlayerRecordDO;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class BoardManagerDO extends BoardManager implements Comparable<BoardManagerDO> {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent(defaultFetchGroup = "false")
    private BoardPackageDO boardPackage;

    @Persistent
    private Integer currentPlayerPosition;

    @Persistent
    private List<PlayerRecordDO> roundQueue;

    @Persistent(serialized = "true")
    private Set<Key> dealtCards;

    @Persistent
    private List<ActionDO> actions;

    @Persistent
    private List<MessageDO> messages;

    @Persistent
    private Date startDate;

    public BoardManagerDO() {
        this.currentPlayerPosition = 0;
        this.roundQueue = new ArrayList<PlayerRecordDO>();
        this.dealtCards = new HashSet<Key>();
        this.actions = new ArrayList<ActionDO>();
        this.messages = new ArrayList<MessageDO>();
        this.startDate = new Date();
    }

    public void init(BoardPackageDO boardPackage) {
        if (this.boardPackage != null) {
            this.boardPackage = boardPackage;
        }
    }

    public void restore() {
        for (PlayerRecordDO player : roundQueue) player.restore(this);
        addActionListener(getBoardPackage().getRule(), this);
        for (PlayerRecordDO player : roundQueue) addActionListener(player.getRole(), this);
    }

    public BoardPackageDO getBoardPackage() {
        return boardPackage;
    }

    public Key getKey() {
        return key;
    }

    public Date getStartDate() {
        return startDate;
    }

    public List<ActionDO> getActions() {
        List<ActionDO> result = new ArrayList<ActionDO>(actions.size());
        for (int i = actions.size() - 1; i >= 0; i--) result.add(actions.get(i));
        return result;
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

    public int compareTo(BoardManagerDO boardManagerDO) {
        return getKey().compareTo(boardManagerDO.getKey());
    }

    @Override
    public Player addPlayer(String playerName, int health) {
        PlayerRecordDO player = new PlayerRecordDO(this, playerName, health, dealCards(4));
        roundQueue.add(player);
        return player;
    }

    public BoardPackage getPackage() {
        return boardPackage;
    }

    public int getPlayerAmount() {
        return boardPackage.getRoles().length;
    }

    public Player[] getPlayers() {
        return roundQueue.toArray(new Player[roundQueue.size()]);
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
    protected CardInstance createCardRecord(Card card, Player owner, Player target, int cardPosition) {
        return new CardRecordDO((BoardCardDO) card, this, (PlayerRecordDO) owner, (PlayerRecordDO) target,
                cardPosition);
    }

    @Override
    protected void fireActionEventBefore(ActionEvent event) {
        super.fireActionEventBefore(event);
        actions.add(new ActionDO(this, event.getAction()));
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

    protected List<ElementRecordDO> dealCards(int cardAmount) {
        List<ElementRecordDO> record = new LinkedList<ElementRecordDO>();
        for (BoardElementSchoolDO elementSchool : getBoardPackage().getElementSchools()) {
            List<BoardCardDO> remaining = new LinkedList<BoardCardDO>();
            for (BoardCardDO card : elementSchool.getCards()) {
                if (dealtCards.contains(card.getKey())) continue;
                remaining.add(card);
            }
            randomSelect(remaining, cardAmount);
            for (BoardCardDO card : remaining) dealtCards.add(card.getKey());
            record.add(new ElementRecordDO(elementSchool, (int) (3 + Math.random() * 4), remaining));
        }
        return record;
    }

    protected void randomSelect(List<BoardCardDO> remainingCards, int cardAmount) {
        int size = remainingCards.size() - cardAmount;
        for (int i = 0; i < size; i++) {
            int index = (int) Math.floor(remainingCards.size() * Math.random());
            remainingCards.remove(index);
        }
    }
}
