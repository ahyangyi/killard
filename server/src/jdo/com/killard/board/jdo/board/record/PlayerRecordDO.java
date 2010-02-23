package com.killard.board.jdo.board.record;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.card.Card;
import com.killard.board.card.ElementSchool;
import com.killard.board.card.MetaCard;
import com.killard.board.card.Role;
import com.killard.board.card.record.AbstractPlayerRecord;
import com.killard.board.jdo.board.BoardDO;
import com.killard.board.jdo.board.MetaCardDO;
import com.killard.board.jdo.board.RoleDO;
import com.killard.board.jdo.board.record.property.PlayerRecordPropertyDO;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    private Key boardKey;

    @Persistent(dependent = "true")
    private RoleRecordDO role;

    @Persistent
    private String uid;

    @Persistent
    private String nickname;

    @Persistent
    private int number;

    @Persistent
    private int health;

    @Persistent
    private boolean cardPlayed;

    @Persistent
    @Element(dependent = "true")
    private SortedSet<CardRecordDO> equippedCards;

    @Persistent
    @Element(dependent = "true")
    private SortedSet<ElementRecordDO> elementRecords;

    @Persistent
    @Element(dependent = "true")
    private Set<PlayerRecordPropertyDO> properties;

    @Persistent
    private boolean alive;

    @Persistent
    private boolean called;

    @Persistent
    private boolean winner;

    @Persistent
    private boolean loser;

    @Persistent
    private int turnCount;

    public PlayerRecordDO(BoardDO board, RoleDO role, String uid, String nickname, int number,
                          List<ElementRecordDO> elementRecords) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(board.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), uid);
        this.key = keyBuilder.getKey();

        this.boardKey = board.getKey();

        this.role = new RoleRecordDO(this, role);
        this.uid = uid;
        this.nickname = nickname;
        this.number = number;
        this.health = 50;
        this.cardPlayed = false;
        this.equippedCards = new TreeSet<CardRecordDO>();
        for (ElementRecordDO element : elementRecords) element.setPlayer(this);
        this.elementRecords = new TreeSet<ElementRecordDO>(elementRecords);

        this.properties = new HashSet<PlayerRecordPropertyDO>();

        this.alive = true;
        this.called = false;
        this.winner = false;
        this.loser = false;
        this.turnCount = 0;
    }

    public void restore(BoardDO board) {
        for (ElementRecordDO element : elementRecords) {
            element.restore(board);
        }
        for (CardRecordDO card : equippedCards) {
            card.restore(board, this);
        }
        board.addActionListener(role, this);
        addStateListener(board);
    }

    public Key getKey() {
        return key;
    }

    public Key getBoardKey() {
        return boardKey;
    }

    public String getId() {
        return uid;
    }

    public String getNickname() {
        return nickname;
    }

    public int getNumber() {
        return number;
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
        properties.add(new PlayerRecordPropertyDO(key, name, data.toString()));
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

    public MetaCard getDealtCard(String elementSchoolName, String cardName) {
        for (ElementRecordDO element : elementRecords) {
            if (element.getElementSchool().getName().equals(elementSchoolName)) {
                for (MetaCard card : element.getDealtCards()) {
                    MetaCardDO record = (MetaCardDO) card;
                    if (record.getName().equals(cardName)) return card;
                }
            }
        }
        return null;
    }

    public MetaCard[] getDealtCards(ElementSchool elementSchool) {
        for (ElementRecordDO element : elementRecords) {
            if (element.getElementSchool().getName().equals(elementSchool.getName())) return element.getDealtCards();
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
        MetaCardDO record = (MetaCardDO) metaCard;
        for (ElementRecordDO element : elementRecords) {
            try {
                if (record.getElementSchool().getName().equals(element.getElementSchool().getName())) {
                    return element.addDealtCard(record);
                }
            } catch (NullPointerException e) {
                System.out.println("Null? record: " + record);
                System.out.println("Null? record element school: " + record.getElementSchool());
                System.out.println("Null? element: " + element);
                System.out.println("Null? element school: " + element.getElementSchool());
                throw e;
            }
        }
        return false;
    }

    protected boolean removeDealtCard(MetaCard metaCard) {
        MetaCardDO record = (MetaCardDO) metaCard;
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
        return key.compareTo(playerRecordDO.key);
    }
}
