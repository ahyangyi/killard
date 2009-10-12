package com.killard.jdo.board.player;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.card.Attribute;
import com.killard.card.Card;
import com.killard.card.CardInstance;
import com.killard.card.ElementSchool;
import com.killard.card.Role;
import com.killard.environment.record.PlayerRecord;
import com.killard.environment.record.AbstractPlayerRecord;
import com.killard.jdo.board.BoardCardDO;
import com.killard.jdo.board.BoardManagerDO;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class PlayerRecordDO extends AbstractPlayerRecord {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private Key boardManagerKey;

    @Persistent
    private String uid;

    @Persistent
    private Integer health;

    @Persistent
    private Boolean cardPlayed;

    @Persistent
    private SortedSet<CardRecordDO> livingCards;

    @Persistent
    private SortedSet<ElementRecordDO> elementRecords;

    @Persistent
    private Integer turnCount;

    public PlayerRecordDO(BoardManagerDO boardManager, String uid, int health, List<ElementRecordDO> elementRecords) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(boardManager.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), uid);
        this.key = keyBuilder.getKey();

        this.boardManagerKey = boardManager.getKey();

        this.uid = uid;
        this.health = health;
        this.cardPlayed = false;
        this.livingCards = new TreeSet<CardRecordDO>();
        this.elementRecords = new TreeSet<ElementRecordDO>(elementRecords);
        for (ElementRecordDO element : elementRecords) element.setPlayer(this);

        this.turnCount = 0;
    }

    public void restore(BoardManagerDO boardManager) {
        for (ElementRecordDO element : elementRecords) {
            element.restore(boardManager);
        }
        for (CardRecordDO card : livingCards) {
            card.restore(boardManager);
        }
        for (CardRecordDO card : livingCards) {
            for (Attribute attribute : card.getVisibleAttributes()) {
                boardManager.addActionListener(attribute, card);
            }
        }
        addStateListener(boardManager);
    }

    public Key getKey() {
        return key;
    }

    public Key getBoardManagerKey() {
        return boardManagerKey;
    }

    public String getId() {
        return uid;
    }

    public Role getRole() {
        return null;
    }

    public int getHealth() {
        return health;
    }

    public boolean isCardPlayed() {
        return cardPlayed;
    }

    public int getTurnCount() {
        return turnCount;
    }

    protected void setCardPlayed(boolean cardPlayed) {
        this.cardPlayed = cardPlayed;
    }

    public Card getHoldedCard(Integer cardIndex) {
        for (ElementRecordDO element : elementRecords) {
            for (BoardCardDO card : element.getHoldedCards()) if (card.getKey().getId() == cardIndex) return card;
        }
        return null;
    }

    public Card[] getHoldedCards(ElementSchool elementSchool) {
        for (ElementRecordDO element : elementRecords) {
            if (element.getElementSchool().equals(elementSchool)) return element.getHoldedCards();
        }
        return new Card[0];
    }

    public CardInstance[] getLivingCards() {
        return livingCards.toArray(new CardInstance[livingCards.size()]);
    }

    public CardInstance getLivingCard(Integer pos) {
        for (CardInstance card : livingCards) if (card.getPosition() == pos) return card;
        return null;
    }

    public int getElementAmount(ElementSchool elementSchool) {
        for (ElementRecordDO record : elementRecords) {
            if (record.getElementSchool().equals(elementSchool)) return record.getAmount();
        }
        return 0;
    }

    public ElementRecordDO[] getElementRecords() {
        return elementRecords.toArray(new ElementRecordDO[elementRecords.size()]);
    }

    public Integer[] getAvailablePositions() {
        List<Integer> positions = new ArrayList<Integer>();
        for (int i = 1; i <= 5; i++) {
            if (getLivingCard(i) == null) positions.add(i);
        }
        return positions.toArray(new Integer[positions.size()]);
    }

    public CardInstance[] getLivingCardsView() {
        CardInstance[] view = new CardInstance[5];
        for (int i = 0; i < view.length; i++) {
            view[i] = getLivingCard(i + 1);
        }
        return view;
    }

    protected void setHealth(int health) {
        this.health = health;
    }

    protected void setElementAmount(ElementSchool elementSchool, int amount) {
        for (ElementRecordDO record : elementRecords) {
            if (record.getElementSchool().equals(elementSchool)) record.setAmount(amount);
        }
    }

    protected void setTurnCount(int turnCount) {
        this.turnCount = turnCount;
    }

    protected boolean removeLivingCard(CardInstance card) {
        CardRecordDO record = (CardRecordDO) card;
        return livingCards.remove(record);
    }

    protected boolean addLivingCard(CardInstance card) {
        setCardPlayed(true);
        CardRecordDO record = (CardRecordDO) card;
        return livingCards.add(record);
    }
}
