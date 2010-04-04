package com.killard.board.jdo.board;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import com.killard.board.card.Element;
import com.killard.board.jdo.AttributeHandler;
import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.board.descriptor.ElementDescriptorDO;
import com.killard.board.jdo.board.property.ElementPropertyDO;
import com.killard.board.parser.Function;

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
public class ElementDO extends DescriptableDO<ElementDO, ElementPropertyDO, ElementDescriptorDO>
        implements Element {

    @Persistent(mappedBy = "element")
    @javax.jdo.annotations.Element(dependent = "true")
    private List<MetaCardDO> cards;

    @Persistent(mappedBy = "element")
    @javax.jdo.annotations.Element(dependent = "true")
    private List<AttributeDO> attributes;

    @Persistent
    @javax.jdo.annotations.Element(dependent = "true")
    private List<SkillDO> skills;

    @Persistent
    @javax.jdo.annotations.Element(dependent = "true")
    private List<ElementPropertyDO> properties;

    @Persistent
    @javax.jdo.annotations.Element(dependent = "true")
    private transient List<ElementDescriptorDO> descriptors;

    @Persistent(defaultFetchGroup = "false")
    private transient Blob image;

    protected ElementDO(PackageDO pack, String name) {
        super(pack, name);
        this.cards = new ArrayList<MetaCardDO>();
        this.attributes = new ArrayList<AttributeDO>();
        this.properties = new ArrayList<ElementPropertyDO>();
        this.descriptors = new ArrayList<ElementDescriptorDO>();
    }

    protected ElementDO(PackageDO pack, ElementDO source) {
        this(pack, source.getName());
    }

    public ElementPropertyDO[] getProperties() {
        return properties.toArray(new ElementPropertyDO[properties.size()]);
    }

    protected boolean addProperty(String name, String data) {
        return properties.add(new ElementPropertyDO(this, name, data));
    }

    public MetaCardDO newCard(String name) {
        MetaCardDO card = new MetaCardDO(this, name);
        cards.add(card);
        return card;
    }

    public AttributeDO newAttribute(String name) {
        AttributeDO attribute = new AttributeDO(this, name, false,
                new ArrayList<AttributeHandler>(),
                new ArrayList<AttributeHandler>(), new ArrayList<AttributeHandler>());
        attributes.add(attribute);
        return attribute;
    }

    public MetaCardDO[] getCards() {
        return cards.toArray(new MetaCardDO[cards.size()]);
    }

    public SkillDO newSkill(String name, List<String> targets, int cost, Function function) {
        SkillDO skill = new SkillDO(this, name, targets, cost, function);
        skills.add(skill);
        return skill;
    }

    public SkillDO[] getSkills() {
        return skills.toArray(new SkillDO[skills.size()]);
    }

    public SkillDO getSkill(Key key) {
        for (SkillDO skill : skills) {
            if (skill.getKey().equals(key)) return skill;
        }
        return null;
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

    public AttributeDO getAttribute(Key key) {
        for (AttributeDO attribute : attributes) {
            if (attribute.getKey().equals(key)) return attribute;
        }
        return null;
    }

    public ElementDescriptorDO[] getDescriptors() {
        return descriptors.toArray(new ElementDescriptorDO[descriptors.size()]);
    }

    protected boolean addDescriptor(String locale, String name, String description) {
        return descriptors.add(new ElementDescriptorDO(this, locale, name, description));
    }
}
