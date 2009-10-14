package com.killard.jdo.board.player;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.jdo.PersistenceHelper;
import com.killard.jdo.board.BoardCardDO;
import com.killard.jdo.board.BoardElementSchoolDO;
import com.killard.jdo.board.BoardManagerDO;

import javax.jdo.PersistenceManager;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.ArrayList;
import java.util.Comparator;
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
public class ElementRecordDO implements Comparable<ElementRecordDO>, Comparator<BoardCardDO> {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private Key elementSchoolKey;

    @Persistent
    private int amount;

    @Persistent(serialized = "true")
    private List<Key> dealtCardKeys;

    @NotPersistent
    private BoardElementSchoolDO elementSchool;

    @NotPersistent
    private SortedSet<BoardCardDO> dealtCards;

    public ElementRecordDO(BoardElementSchoolDO elementSchool) {
        this.elementSchoolKey = elementSchool.getKey();
        this.amount = 0;
        this.dealtCardKeys = new ArrayList<Key>();
        this.elementSchool = elementSchool;
        this.dealtCards = new TreeSet<BoardCardDO>();
    }

    public void restore(BoardManagerDO boardManager) {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        elementSchool = pm.getObjectById(BoardElementSchoolDO.class, elementSchoolKey);
        dealtCards = new TreeSet<BoardCardDO>(this);
        for (Key key : dealtCardKeys) {
            dealtCards.add(pm.getObjectById(BoardCardDO.class, key));
        }
    }

    public Key getKey() {
        return key;
    }

    public Key getElementSchoolKey() {
        return elementSchoolKey;
    }

    public BoardElementSchoolDO getElementSchool() {
        return elementSchool;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public BoardCardDO[] getDealtCards() {
        return dealtCards.toArray(new BoardCardDO[dealtCardKeys.size()]);
    }

    public boolean addDealtCard(BoardCardDO card) {
        return dealtCardKeys.contains(card.getKey()) && dealtCards.add(card) && dealtCardKeys.add(card.getKey());
    }

    public boolean removeDealtCard(BoardCardDO card) {
        return dealtCardKeys.contains(card.getKey()) && dealtCards.remove(card) && dealtCardKeys.remove(card.getKey());
    }

    protected void setPlayer(PlayerRecordDO player) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(player.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), elementSchool.toString());
        this.key = keyBuilder.getKey();
    }

    public int compare(BoardCardDO card1, BoardCardDO card2) {
        if (card1.getLevel() == card2.getLevel()) return card1.compareTo(card2);
        return card1.getLevel() - card2.getLevel();
    }

    public int compareTo(ElementRecordDO elementRecord) {
        return getElementSchool().compareTo(elementRecord.getElementSchool());
    }
}
