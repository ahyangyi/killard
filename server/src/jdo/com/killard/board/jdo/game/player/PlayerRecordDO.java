package com.killard.board.jdo.game.player;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.card.Card;
import com.killard.board.card.ElementSchool;
import com.killard.board.card.MetaCard;
import com.killard.board.card.Role;
import com.killard.board.card.record.AbstractPlayerRecord;
import com.killard.board.jdo.game.BoardManagerDO;
import com.killard.board.jdo.game.GameCardDO;
import com.killard.board.jdo.game.GameRoleDO;
import com.killard.board.jdo.game.player.property.PlayerRecordPropertyDO;

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
public class PlayerRecordDO extends AbstractPlayerRecord<PlayerRecordDO> {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private Key boardManagerKey;

    @Persistent
    private RoleRecordDO role;

    @Persistent
    private String uid;

    @Persistent
    private Integer health;

    @Persistent
    private Boolean cardPlayed;

    @Persistent
    private SortedSet<CardRecordDO> equippedCards;

    @Persistent
    private SortedSet<ElementRecordDO> elementRecords;

    @Persistent
    private SortedSet<PlayerRecordPropertyDO> properties;

    @Persistent
    private Boolean alive;

    @Persistent
    private Boolean called;

    @Persistent
    private Boolean winner;

    @Persistent
    private Boolean loser;

    @Persistent
    private Integer turnCount;

    public PlayerRecordDO(BoardManagerDO boardManager, GameRoleDO role, String uid, List<ElementRecordDO> elementRecords) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(boardManager.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), uid);
        this.key = keyBuilder.getKey();

        this.boardManagerKey = boardManager.getKey();

        this.role = new RoleRecordDO(this, role);
        this.uid = uid;
        this.health = 0;
        this.cardPlayed = false;
        this.equippedCards = new TreeSet<CardRecordDO>();
        this.elementRecords = new TreeSet<ElementRecordDO>(elementRecords);
        for (ElementRecordDO element : elementRecords) element.setPlayer(this);

        this.properties = new TreeSet<PlayerRecordPropertyDO>();

        this.turnCount = 0;
    }

    public void restore(BoardManagerDO boardManager) {
        for (ElementRecordDO element : elementRecords) {
            element.restore(boardManager);
        }
        for (CardRecordDO card : equippedCards) {
            card.restore(boardManager);
        }
        boardManager.addActionListener(role, this);
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
        return role;
    }

    public int getHealth() {
        return health;
    }

    public PlayerRecordPropertyDO[] getProperties() {
        return properties.toArray(new PlayerRecordPropertyDO[properties.size()]);
    }

    public Object getProperty(String name) {
        for (PlayerRecordPropertyDO property : getProperties()) {
            if (property.getName().equals(name)) return property.getData();
        }
        return null;
    }

    protected void setProperty(String name, Object data) {
        for (PlayerRecordPropertyDO property : getProperties()) {
            if (property.getName().equals(name)) {
                property.setData(data.toString());
                return;
            }
        }
        properties.add(new PlayerRecordPropertyDO(this, name, data.toString()));
    }

    public boolean isCardPlayed() {
        return cardPlayed;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isCalled() {
        return called;
    }

    public boolean isWinner() {
        return winner;
    }

    public boolean isLoser() {
        return loser;
    }

    public int getTurnCount() {
        return turnCount;
    }

    public MetaCard getDealtCard(Integer cardIndex) {
        for (ElementRecordDO element : elementRecords) {
            for (GameCardDO card : element.getDealtCards()) if (card.getKey().getId() == cardIndex) return card;
        }
        return null;
    }

    public MetaCard[] getDealtCards(ElementSchool elementSchool) {
        for (ElementRecordDO element : elementRecords) {
            if (element.getElementSchool().equals(elementSchool)) return element.getDealtCards();
        }
        return new MetaCard[0];
    }

    public Card[] getEquippedCards() {
        return equippedCards.toArray(new Card[equippedCards.size()]);
    }

    public Card getEquippedCard(Integer pos) {
        for (Card card : equippedCards) if (card.getPosition() == pos) return card;
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
            if (getEquippedCard(i) == null) positions.add(i);
        }
        return positions.toArray(new Integer[positions.size()]);
    }

    public Card[] getEquippedCardsView() {
        Card[] view = new Card[5];
        for (int i = 0; i < view.length; i++) {
            view[i] = getEquippedCard(i + 1);
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

    protected boolean addDealtCard(MetaCard metaCard) {
        GameCardDO record = (GameCardDO) metaCard;
        for (ElementRecordDO element : elementRecords) {
            if (record.getElementSchool().equals(element)) return element.addDealtCard(record);
        }
        return false;
    }

    protected boolean removeDealtCard(MetaCard metaCard) {
        GameCardDO record = (GameCardDO) metaCard;
        for (ElementRecordDO element : elementRecords) {
            if (record.getElementSchool().equals(element)) return element.removeDealtCard(record);
        }
        return false;
    }

    protected boolean removeEquippedCard(Card card) {
        CardRecordDO record = (CardRecordDO) card;
        return equippedCards.remove(record);
    }

    protected boolean addEquippedCard(Card card) {
        setCardPlayed(true);
        CardRecordDO record = (CardRecordDO) card;
        return equippedCards.add(record);
    }

    protected void setCardPlayed(boolean cardPlayed) {
        this.cardPlayed = cardPlayed;
    }

    protected void setAlive(boolean alive) {
        this.alive = alive;
    }

    protected void setCalled(boolean called) {
        this.called = called;
    }

    protected void setRoleVisible(boolean visible) {
        role.setVisible(visible);
    }

    protected void setWinner(boolean winner) {
        this.winner = winner;
    }

    protected void setLoser(boolean loser) {
        this.loser = loser;
    }

    protected void setTurnCount(int turnCount) {
        this.turnCount = turnCount;
    }

    public int compareTo(PlayerRecordDO playerRecordDO) {
        return getTurnCount() - playerRecordDO.getTurnCount();
    }
}
