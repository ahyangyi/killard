package com.killard.jdo.board;

import com.google.appengine.api.datastore.Key;
import com.killard.card.Card;
import com.killard.card.CardInstance;
import com.killard.card.ElementSchool;
import com.killard.card.Player;
import com.killard.environment.BoardManager;
import com.killard.environment.event.ActionEvent;
import com.killard.jdo.board.player.CardRecordDO;
import com.killard.jdo.board.player.ElementRecordDO;
import com.killard.jdo.board.player.PlayerRecordDO;
import com.killard.jdo.card.PackageDO;

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

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class BoardManagerDO extends BoardManager implements Comparable<BoardManagerDO> {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private BoardPackageDO pack;

    @Persistent
    private int currentPlayerPosition;

    @Persistent
    private int maxPlayerNumber;

    @Persistent
    private List<PlayerRecordDO> roundQueue;

    @Persistent
    private List<ActionDO> actions;

    @Persistent
    private List<MessageDO> messages;

    @Persistent
    private Date startDate;

    public BoardManagerDO(PackageDO packageDO, int maxPlayerNumber) {
        this.pack = new BoardPackageDO(packageDO);
        this.maxPlayerNumber = maxPlayerNumber;
        this.currentPlayerPosition = 0;
        this.roundQueue = new ArrayList<PlayerRecordDO>();
        this.actions = new ArrayList<ActionDO>();
        this.messages = new ArrayList<MessageDO>();
        this.startDate = new Date();
    }

    public void restore() {
        for (PlayerRecordDO player : roundQueue) player.restore(this);
        addActionListener(getRule(), this);
    }

    public BoardRuleDO getRule() {
        return getPackage().getRule();
    }

    public BoardPackageDO getPackage() {
        return pack;
    }

    public Key getKey() {
        return key;
    }

    public int getMaxPlayerNumber() {
        return maxPlayerNumber;
    }

    public Date getStartDate() {
        return startDate;
    }

    public List<ActionDO> getActions() {
        List<ActionDO> result = new ArrayList<ActionDO>(actions.size());
        for (int i = actions.size() -1; i >= 0; i--) result.add(actions.get(i));
        return result;
    }

    public List<Player> getPlayers(String playerName) {
        List<Player> players = new ArrayList<Player>();
        int position;
        for (position = 0; position < roundQueue.size(); position++)
            if (roundQueue.get(position).getName().equals(playerName)) break;
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

    @Override
    public Player addPlayer(String playerName, int health) {
        PlayerRecordDO player = new PlayerRecordDO(this, playerName, health, allocateCardsForNextPlayer(4));
        roundQueue.add(player);
        return player;
    }

    @Override
    public List<Player> getPlayers() {
        return new ArrayList<Player>(roundQueue);
    }

    @Override
    public Player getCurrentPlayer() {
        return roundQueue.get(getCurrentPlayerPosition());
    }

    @Override
    public Player getNextPlayer() {
        return roundQueue.get(getPlayerPosition(1));
    }

    @Override
    public Player getPreviousPlayer() {
        return roundQueue.get(getPlayerPosition(-1));
    }

    @Override
    public Player getPlayer(int position) {
        return roundQueue.get(getPlayerPosition(position));
    }

    @Override
    protected CardInstance createCardRecord(Card card, Player owner, Player target, int cardPosition) {
        return new CardRecordDO((BoardCardDO) card, this, (PlayerRecordDO)owner, (PlayerRecordDO)target, cardPosition);
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

    public List<ElementRecordDO> allocateCardsForNextPlayer(int cardAmount) {
        List<ElementRecordDO> record = new LinkedList<ElementRecordDO>();
        for (BoardElementSchoolDO elementSchool : getPackage().getElementSchools()) {
            List<BoardCardDO> remaining = getRemainingCards(elementSchool);
            randomSelect(remaining, cardAmount);
            record.add(new ElementRecordDO(elementSchool, (int) (3 + Math.random() * 4), remaining));
        }
        return record;
    }

    protected List<BoardCardDO> getRemainingCards(BoardElementSchoolDO elementSchool) {
        List<BoardCardDO> remaining = new LinkedList<BoardCardDO>();
        for (BoardCardDO card : elementSchool.getCards()) {
            if (isCardAllocated(elementSchool, card)) continue;
            remaining.add(card);
        }
        return remaining;
    }

    protected boolean isCardAllocated(ElementSchool elementSchool, BoardCardDO card) {
        for (PlayerRecordDO player : roundQueue)
            if (isCardAllocated(player.getHoldedCards(elementSchool), card)) return true;
        return false;
    }

    protected boolean isCardAllocated(Card[] allocatedCards, BoardCardDO card) {
        for (Card c : allocatedCards) {
            BoardCardDO allocated = (BoardCardDO) c;
            if (card.getKey().equals(allocated.getKey())) return true;
        }
        return false;
    }

    protected void randomSelect(List<BoardCardDO> remainingCards, int cardAmount) {
        int size = remainingCards.size() - cardAmount;
        for (int i = 0; i < size; i++) {
            int index = (int)Math.floor(remainingCards.size() * Math.random());
            remainingCards.remove(index);
        }
    }

    public int compareTo(BoardManagerDO boardManagerDO) {
        return getKey().compareTo(boardManagerDO.getKey());
    }
}
