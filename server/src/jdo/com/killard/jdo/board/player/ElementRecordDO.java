package com.killard.jdo.board.player;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.web.PersistenceHelper;
import com.killard.jdo.board.BoardCardDO;
import com.killard.jdo.board.BoardElementSchoolDO;
import com.killard.jdo.board.BoardManagerDO;

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
public class ElementRecordDO implements Comparator<BoardCardDO> {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private Key elementSchoolKey;

    @Persistent
    private int amount;

    @Persistent
    private List<Key> holdedCardKeys = new ArrayList<Key>();

    @NotPersistent
    private BoardElementSchoolDO elementSchool;

    @NotPersistent
    private SortedSet<BoardCardDO> holdedCards = new TreeSet<BoardCardDO>(this);

    public ElementRecordDO(BoardElementSchoolDO elementSchool, int amount, List<BoardCardDO> cards) {
        this.elementSchoolKey = elementSchool.getKey();
        this.amount = amount;

        this.holdedCardKeys = new ArrayList<Key>(cards.size());
        for (BoardCardDO card : cards) holdedCardKeys.add(card.getKey());

        this.elementSchool = elementSchool;
        this.holdedCards.addAll(cards);
    }

    public void restore(BoardManagerDO boardManager) {
        elementSchool = PersistenceHelper.getPersistenceManager().getObjectById(BoardElementSchoolDO.class, elementSchoolKey);
        if (holdedCardKeys == null) {
            holdedCardKeys = new ArrayList<Key>();
        }
        holdedCards = new TreeSet<BoardCardDO>(this);
        for (Key key : holdedCardKeys) {
            holdedCards.add(PersistenceHelper.getPersistenceManager().getObjectById(BoardCardDO.class, key));
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

    public BoardCardDO[] getHoldedCards() {
        return holdedCards.toArray(new BoardCardDO[holdedCardKeys.size()]);
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
}
