package com.killard.board.jdo.board.record;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.card.ElementSchool;
import com.killard.board.jdo.board.BoardDO;
import com.killard.board.jdo.board.ElementSchoolDO;
import com.killard.board.jdo.board.MetaCardDO;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.LinkedList;
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
public class ElementRecordDO implements Comparable<ElementRecordDO> {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private Key elementSchoolKey;

    @Persistent
    private int amount;

    @Persistent
    private List<Key> dealtCardKeys;

    @NotPersistent
    private ElementSchoolDO elementSchool;

    @NotPersistent
    private List<MetaCardDO> dealtCards;

    public ElementRecordDO(ElementSchoolDO elementSchool) {
        this.elementSchoolKey = elementSchool.getKey();
        this.amount = 10;
        this.dealtCardKeys = new LinkedList<Key>();
        this.elementSchool = elementSchool;
        this.dealtCards = new LinkedList<MetaCardDO>();
    }

    public void restore(BoardDO board) {
        for (ElementSchool e : board.getBoardPackage().getElementSchools()) {
            ElementSchoolDO obj = (ElementSchoolDO) e;
            if (obj.getKey().equals(elementSchoolKey)) {
                elementSchool = obj;
                break;
            }
        }
        dealtCards = new LinkedList<MetaCardDO>();
        MetaCardDO[] cards = elementSchool.getCards();
        for (Key key : dealtCardKeys) {
            for (MetaCardDO c : cards) {
                if (c.getKey().equals(key)) {
                    dealtCards.add(c);
                    break;
                }
            }
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
        return dealtCards.add(card) && dealtCardKeys.add(card.getKey());
    }

    public boolean removeDealtCard(MetaCardDO card) {
        return dealtCards.remove(card) && dealtCardKeys.remove(card.getKey());
    }

    protected void setPlayer(PlayerRecordDO player) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(player.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), elementSchool.getKey().getName());
        this.key = keyBuilder.getKey();
    }

    public int compareTo(ElementRecordDO elementRecord) {
        return key.compareTo(elementRecord.key);
    }
}
