package com.killard.jdo.board;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;
import com.killard.card.ElementSchool;
import com.killard.jdo.card.AttributeDO;
import com.killard.jdo.card.CardDO;
import com.killard.jdo.card.ElementSchoolDO;

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
public class BoardElementSchoolDO extends BoardDescriptableDO implements ElementSchool {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private BoardPackageDO pack;

    @Persistent
    private String id;

    @Persistent(mappedBy = "elementSchool")
    private SortedSet<BoardCardDO> cards;

    @Persistent(mappedBy = "elementSchool", defaultFetchGroup = "false")
    private SortedSet<BoardAttributeDO> attributes;

    @Persistent
    private String name;

    @Persistent
    private Text description;

    @Persistent(defaultFetchGroup = "false")
    private Blob image;

    public BoardElementSchoolDO(BoardPackageDO pack, ElementSchoolDO elementSchool) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(pack.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), elementSchool.getId());
        this.key = keyBuilder.getKey();

        this.pack = pack;
        this.id = elementSchool.getId();

        this.cards = new TreeSet<BoardCardDO>();
        for (CardDO card : elementSchool.getCards()) {
            cards.add(new BoardCardDO(this, card));
        }

        this.attributes = new TreeSet<BoardAttributeDO>();
        for (AttributeDO attribute : elementSchool.getAttributes()) {
            attributes.add(new BoardAttributeDO(this, attribute));
        }

        this.name = elementSchool.getDescriptor().getName();
//        this.description = new Text(elementSchool.getDescriptor().getDescription());
//        this.image = new Blob(elementSchool.getDescriptor().getImageData());
    }

    public Key getKey() {
        return key;
    }

    public BoardPackageDO getBoardPackage() {
        return pack;
    }

    public String getId() {
        return id;
    }

    public BoardCardDO[] getCards() {
        return cards.toArray(new BoardCardDO[cards.size()]);
    }

    public BoardAttributeDO getAttribute(String id) {
        for (BoardAttributeDO attribute : attributes) {
            if (attribute.getId().equals(id)) return attribute;
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description.getValue();
    }

    public byte[] getImageData() {
        return image.getBytes();
    }
}
