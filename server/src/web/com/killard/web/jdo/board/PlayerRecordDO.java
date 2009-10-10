package com.killard.web.jdo.board;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.card.Attribute;
import com.killard.card.Card;
import com.killard.card.CardInstance;
import com.killard.card.ElementSchool;
import com.killard.environment.record.PlayerRecord;
import com.killard.web.PersistenceHelper;
import com.killard.web.jdo.card.CardDO;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.ArrayList;
import java.util.List;

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
public class PlayerRecordDO extends PlayerRecord {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    @Extension(vendorName = "datanucleus", key = "gae.parent-pk", value = "true")
    private Key boardManagerKey;

    @Persistent
    private Key profileKey;

    @Persistent
    private String name;

    @Persistent
    private Integer health;

    @Persistent
    private Boolean cardPlayed;

    @Persistent
    private List<CardRecordDO> livingCards;

    @Persistent(defaultFetchGroup = "false")
    private List<ElementRecordDO> elementRecords;

    @Persistent
    private Integer turnCount;

    public PlayerRecordDO() {
        super("", 0);
    }

    public PlayerRecordDO(BoardManagerDO boardManager, String name, int health, List<ElementRecordDO> elementRecords) {
        super(name, health, boardManager);

        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(boardManager.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), name);
        this.key = keyBuilder.getKey();

        this.name = name;
        this.health = health;
        this.cardPlayed = false;
        this.livingCards = new ArrayList<CardRecordDO>();
        this.elementRecords = elementRecords;
        for (ElementRecordDO element : elementRecords) {
            element.setPlayer(this);
        }
    }

    public void restore(BoardManagerDO boardManager) {
        PersistenceHelper.doTransaction();
        PersistenceHelper.doTransaction();

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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public boolean isCardPlayed() {
        return cardPlayed;
    }

    @Override
    public int getTurnCount() {
        return turnCount;
    }

    @Override
    protected void setCardPlayed(boolean cardPlayed) {
        this.cardPlayed = cardPlayed;
    }

    @Override
    public ElementSchool[] getAllElementSchool() {
        List<ElementSchool> record = new ArrayList<ElementSchool>(elementRecords.size());
        for (ElementRecordDO element : elementRecords) {
            record.add(element.getElementSchool());
        }
        return record.toArray(new ElementSchool[record.size()]);
    }

    @Override
    public Card getHoldedCard(Integer cardIndex) {
        for (ElementRecordDO element : elementRecords) {
            for (CardDO card : element.getHoldedCards()) if (card.getKey().getId() == cardIndex) return card;
        }
        return null;
    }

    @Override
    public Card[] getHoldedCards(ElementSchool elementSchool) {
        for (ElementRecordDO element : elementRecords) {
            if (element.getElementSchool().equals(elementSchool)) return element.getHoldedCards();
        }
        return new Card[0];
    }

    @Override
    public CardInstance[] getLivingCards() {
        return livingCards.toArray(new CardInstance[livingCards.size()]);
    }

    @Override
    public CardInstance getLivingCard(Integer pos) {
        for (CardInstance card : livingCards) if (card.getPosition() == pos) return card;
        return null;
    }

    @Override
    public int getElementAmount(ElementSchool elementSchool) {
        for (ElementRecordDO record : elementRecords) {
            if (record.getElementSchool().equals(elementSchool)) return record.getAmount();
        }
        return 0;
    }

    @Override
    protected void setHealth(int health) {
        this.health = health;
    }

    @Override
    protected void setElementAmount(ElementSchool elementSchool, int amount) {
        for (ElementRecordDO record : elementRecords) {
            if (record.getElementSchool().equals(elementSchool)) record.setAmount(amount);
        }
    }

    @Override
    protected void setTurnCount(int turnCount) {
        this.turnCount = turnCount;
    }

    @Override
    protected void removeLivingCard(CardInstance card) {
        CardRecordDO record = (CardRecordDO) card;
        livingCards.remove(record);
    }

    @Override
    protected void addLivingCard(CardInstance card) {
        CardRecordDO record = (CardRecordDO) card;
        livingCards.add(record);
        setCardPlayed(true);
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
}
