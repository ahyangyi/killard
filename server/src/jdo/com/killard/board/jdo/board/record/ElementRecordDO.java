package com.killard.board.jdo.board.record;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.ElementSchoolDO;
import com.killard.board.jdo.board.MetaCardDO;
import com.killard.board.jdo.board.BoardDO;

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
public class ElementRecordDO implements Comparable<ElementRecordDO>, Comparator<MetaCardDO> {

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
    private ElementSchoolDO elementSchool;

    @NotPersistent
    private SortedSet<MetaCardDO> dealtCards;

    public ElementRecordDO(ElementSchoolDO elementSchool) {
        this.elementSchoolKey = elementSchool.getKey();
        this.amount = 0;
        this.dealtCardKeys = new ArrayList<Key>();
        this.elementSchool = elementSchool;
        this.dealtCards = new TreeSet<MetaCardDO>();
    }

    public void restore(BoardDO board) {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        elementSchool = pm.getObjectById(ElementSchoolDO.class, elementSchoolKey);
        dealtCards = new TreeSet<MetaCardDO>(this);
        for (Key key : dealtCardKeys) {
            dealtCards.add(pm.getObjectById(MetaCardDO.class, key));
        }
    }

    public Key getKey() {
        return key;
    }

    public Key getElementSchoolKey() {
        return elementSchoolKey;
    }

    public ElementSchoolDO getElementSchool() {
        return elementSchool;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public MetaCardDO[] getDealtCards() {
        return dealtCards.toArray(new MetaCardDO[dealtCardKeys.size()]);
    }

    public boolean addDealtCard(MetaCardDO card) {
        return dealtCardKeys.contains(card.getKey()) && dealtCards.add(card) && dealtCardKeys.add(card.getKey());
    }

    public boolean removeDealtCard(MetaCardDO card) {
        return dealtCardKeys.contains(card.getKey()) && dealtCards.remove(card) && dealtCardKeys.remove(card.getKey());
    }

    protected void setPlayer(PlayerRecordDO player) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(player.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), elementSchool.toString());
        this.key = keyBuilder.getKey();
    }

    public int compare(MetaCardDO card1, MetaCardDO card2) {
        if (card1.getLevel() == card2.getLevel()) return card1.compareTo(card2);
        return card1.getLevel() - card2.getLevel();
    }

    public int compareTo(ElementRecordDO elementRecord) {
        return getElementSchool().compareTo(elementRecord.getElementSchool());
    }
}
