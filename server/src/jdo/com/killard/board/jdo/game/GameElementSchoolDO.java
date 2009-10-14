package com.killard.board.jdo.game;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.card.ElementSchool;
import com.killard.board.jdo.board.AttributeDO;
import com.killard.board.jdo.board.CardDO;
import com.killard.board.jdo.board.ElementSchoolDO;
import com.killard.board.jdo.board.descriptor.ElementSchoolDescriptorDO;
import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.game.descriptor.GameElementSchoolDescriptorDO;

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
public class GameElementSchoolDO extends DescriptableDO<GameElementSchoolDescriptorDO> implements ElementSchool {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private String name;

    @Persistent(mappedBy = "elementSchool")
    private SortedSet<GameMetaCardDO> cards;

    @Persistent(mappedBy = "elementSchool", defaultFetchGroup = "false")
    private SortedSet<GameAttributeDO> attributes;

    @Persistent
    private SortedSet<GameElementSchoolDescriptorDO> descriptors;

    public GameElementSchoolDO(GamePackageDO pack, ElementSchoolDO elementSchool) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(pack.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), elementSchool.getKey().getId());
        this.key = keyBuilder.getKey();

        this.name = elementSchool.getName();

        this.cards = new TreeSet<GameMetaCardDO>();
        for (CardDO card : elementSchool.getCards()) {
            cards.add(new GameMetaCardDO(this, card));
        }

        this.attributes = new TreeSet<GameAttributeDO>();
        for (AttributeDO attribute : elementSchool.getAttributes()) {
            attributes.add(new GameAttributeDO(this, attribute));
        }

        this.descriptors = new TreeSet<GameElementSchoolDescriptorDO>();
        for (ElementSchoolDescriptorDO descriptor : elementSchool.getAllDescriptors()) {
            this.descriptors.add(new GameElementSchoolDescriptorDO(this, descriptor));
        }
    }

    public Key getKey() {
        return key;
    }

    public GameMetaCardDO[] getCards() {
        return cards.toArray(new GameMetaCardDO[cards.size()]);
    }

    public GameAttributeDO getAttribute(String id) {
        for (GameAttributeDO attribute : attributes) {
            if (attribute.getName().equals(id)) return attribute;
        }
        return null;
    }

    public String getName() {
        return name;
    }

    protected SortedSet<GameElementSchoolDescriptorDO> getDescriptors() {
        return descriptors;
    }
}
