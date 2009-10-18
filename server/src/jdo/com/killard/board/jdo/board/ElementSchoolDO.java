package com.killard.board.jdo.board;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.card.ElementSchool;
import com.killard.board.jdo.AttributeHandler;
import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.board.descriptor.ElementSchoolDescriptorDO;
import com.killard.board.jdo.board.property.ElementSchoolPropertyDO;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
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
public class ElementSchoolDO extends DescriptableDO<ElementSchoolDO, ElementSchoolPropertyDO, ElementSchoolDescriptorDO>
        implements ElementSchool {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private String name;

    @Persistent(mappedBy = "elementSchool")
    private SortedSet<MetaCardDO> cards;

    @Persistent(mappedBy = "elementSchool", defaultFetchGroup = "false")
    private SortedSet<AttributeDO> attributes;

    @Persistent
    private SortedSet<ElementSchoolPropertyDO> properties;

    @Persistent
    private SortedSet<ElementSchoolDescriptorDO> descriptors;

    @Persistent(defaultFetchGroup = "false")
    private Blob image;

    protected ElementSchoolDO(PackageDO pack, String name) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(pack.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), name);
        this.key = keyBuilder.getKey();

        this.name = name;
        this.cards = new TreeSet<MetaCardDO>();
        this.attributes = new TreeSet<AttributeDO>();
        this.properties = new TreeSet<ElementSchoolPropertyDO>();
        this.descriptors = new TreeSet<ElementSchoolDescriptorDO>();
    }

    protected ElementSchoolDO(PackageDO pack, ElementSchoolDO source) {
        this(pack, source.getName());
    }

    public Key getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public ElementSchoolPropertyDO[] getProperties() {
        return properties.toArray(new ElementSchoolPropertyDO[properties.size()]);
    }

    protected boolean addProperty(String name, String data) {
        return properties.add(new ElementSchoolPropertyDO(this, name, data));
    }

    public void setName(String name) {
        this.name = name;
    }

    public MetaCardDO newCard(String name) {
        MetaCardDO card = new MetaCardDO(this, name);
        cards.add(card);
        return card;
    }

    public MetaCardDO[] getCards() {
        return cards.toArray(new MetaCardDO[cards.size()]);
    }

    public AttributeDO newAttribute(String name, boolean visible,
                                    String definition,
                                    List<AttributeHandler> validators,
                                    List<AttributeHandler> before,
                                    List<AttributeHandler> after) {
        AttributeDO attribute = new AttributeDO(this, name, visible, definition, validators, before, after);
        attributes.add(attribute);
        return attribute;
    }

    public AttributeDO[] getAttributes() {
        return attributes.toArray(new AttributeDO[attributes.size()]);
    }

    public AttributeDO getAttribute(String name) {
        for (AttributeDO attribute : attributes) {
            if (attribute.getName().equals(name)) return attribute;
        }
        return null;
    }

    public ElementSchoolDescriptorDO[] getDescriptors() {
        return descriptors.toArray(new ElementSchoolDescriptorDO[descriptors.size()]);
    }

    protected boolean addDescriptor(String locale, String name, String description) {
        return descriptors.add(new ElementSchoolDescriptorDO(this, locale, name, description));
    }

    public boolean isRenderable() {
        return image != null;
    }

    public byte[] getImageData() {
        return image.getBytes();
    }

    public void setImageData(byte[] data) {
        image = new Blob(data);
    }
}
