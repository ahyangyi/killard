package com.killard.board.jdo.board.record;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
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
    private BoardDO board;

    public ElementRecordDO(ElementSchoolDO elementSchool) {
        this.elementSchoolKey = elementSchool.getKey();
        this.amount = 10;
        this.dealtCardKeys = new LinkedList<Key>();
    }

    public void restore(BoardDO board) {
        this.board = board;
    }

    public Key getKey() {
        return key;
    }

    public Key getElementSchoolKey() {
        return elementSchoolKey;
    }

    public ElementSchoolDO getElementSchool() {
        return board.getElementSchoolDO(elementSchoolKey);
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public MetaCardDO[] getDealtCards() {
        MetaCardDO[] cards = new MetaCardDO[dealtCardKeys.size()];
        for (int i = 0; i < cards.length; i++) cards[i] = board.getMetaCardDO(dealtCardKeys.get(i));
        return cards;
    }

    public boolean addDealtCard(MetaCardDO card) {
        return dealtCardKeys.add(card.getKey());
    }

    public boolean removeDealtCard(MetaCardDO card) {
        return dealtCardKeys.remove(card.getKey());
    }

    protected void setPlayer(PlayerRecordDO player) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(player.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), elementSchoolKey.getName());
        this.key = keyBuilder.getKey();
    }

    public int compareTo(ElementRecordDO compare) {
        return key.compareTo(compare.key);
    }
}
