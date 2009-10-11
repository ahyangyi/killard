package com.killard.jdo.board;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.card.ElementSchool;
import com.killard.jdo.card.AttributeDO;
import com.killard.jdo.card.CardDO;
import com.killard.jdo.card.ElementSchoolDO;
import com.killard.jdo.card.descriptor.ElementSchoolDescriptorDO;
import com.killard.jdo.DescriptableDO;
import com.killard.jdo.board.descriptor.BoardElementSchoolDescriptorDO;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
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
public class BoardElementSchoolDO extends DescriptableDO<BoardElementSchoolDescriptorDO> implements ElementSchool {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private String name;

    @Persistent(mappedBy = "elementSchool")
    private SortedSet<BoardCardDO> cards;

    @Persistent(mappedBy = "elementSchool", defaultFetchGroup = "false")
    private SortedSet<BoardAttributeDO> attributes;

    @Persistent
    private SortedSet<BoardElementSchoolDescriptorDO> descriptors;

    public BoardElementSchoolDO(BoardPackageDO pack, ElementSchoolDO elementSchool) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(pack.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), elementSchool.getKey().getId());
        this.key = keyBuilder.getKey();

        this.name = elementSchool.getName();

        this.cards = new TreeSet<BoardCardDO>();
        for (CardDO card : elementSchool.getCards()) {
            cards.add(new BoardCardDO(this, card));
        }

        this.attributes = new TreeSet<BoardAttributeDO>();
        for (AttributeDO attribute : elementSchool.getAttributes()) {
            attributes.add(new BoardAttributeDO(this, attribute));
        }

        this.descriptors = new TreeSet<BoardElementSchoolDescriptorDO>();
        for (ElementSchoolDescriptorDO descriptor : elementSchool.getAllDescriptors()) {
            this.descriptors.add(new BoardElementSchoolDescriptorDO(this, descriptor));
        }
    }

    public Key getKey() {
        return key;
    }

    public BoardCardDO[] getCards() {
        return cards.toArray(new BoardCardDO[cards.size()]);
    }

    public BoardAttributeDO getAttribute(String id) {
        for (BoardAttributeDO attribute : attributes) {
            if (attribute.getName().equals(id)) return attribute;
        }
        return null;
    }

    public String getName() {
        return name;
    }

    protected SortedSet<BoardElementSchoolDescriptorDO> getDescriptors() {
        return descriptors;
    }
}
