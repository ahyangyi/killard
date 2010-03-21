package com.killard.board.jdo.board;

import com.google.appengine.api.datastore.Blob;
import com.killard.board.card.ElementSchool;
import com.killard.board.jdo.AttributeHandler;
import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.board.descriptor.ElementSchoolDescriptorDO;
import com.killard.board.jdo.board.property.ElementSchoolPropertyDO;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import java.util.ArrayList;
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
public class ElementSchoolDO extends DescriptableDO<ElementSchoolDO, ElementSchoolPropertyDO, ElementSchoolDescriptorDO>
        implements ElementSchool {

    @Persistent(mappedBy = "elementSchool", defaultFetchGroup = "true")
    @Element(dependent = "true")
    private List<MetaCardDO> cards;

    @Persistent(mappedBy = "elementSchool", defaultFetchGroup = "true")
    @Element(dependent = "true")
    private List<AttributeDO> attributes;

    @Persistent(defaultFetchGroup = "true")
    @Element(dependent = "true")
    private List<ElementSchoolPropertyDO> properties;

    @Persistent
    @Element(dependent = "true")
    private transient List<ElementSchoolDescriptorDO> descriptors;

    @Persistent(defaultFetchGroup = "false")
    private transient Blob image;

    protected ElementSchoolDO(PackageDO pack, String name) {
        super(pack, name);
        this.cards = new ArrayList<MetaCardDO>();
        this.attributes = new ArrayList<AttributeDO>();
        this.properties = new ArrayList<ElementSchoolPropertyDO>();
        this.descriptors = new ArrayList<ElementSchoolDescriptorDO>();
    }

    protected ElementSchoolDO(PackageDO pack, ElementSchoolDO source) {
        this(pack, source.getName());
    }

    public ElementSchoolPropertyDO[] getProperties() {
        return properties.toArray(new ElementSchoolPropertyDO[properties.size()]);
    }

    protected boolean addProperty(String name, String data) {
        return properties.add(new ElementSchoolPropertyDO(this, name, data));
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
                                    List<AttributeHandler> validators,
                                    List<AttributeHandler> before,
                                    List<AttributeHandler> after) {
        AttributeDO attribute = new AttributeDO(this, name, visible, validators, before, after);
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
}
