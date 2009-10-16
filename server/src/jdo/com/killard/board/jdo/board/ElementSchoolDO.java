package com.killard.board.jdo.board;

import com.google.appengine.api.datastore.Key;
import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.board.descriptor.ElementSchoolDescriptorDO;
import com.killard.board.jdo.board.property.ElementSchoolPropertyDO;
import com.killard.board.card.ElementSchool;

import javax.jdo.annotations.Extension;
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
public class ElementSchoolDO extends DescriptableDO<ElementSchoolDO, ElementSchoolPropertyDO, ElementSchoolDescriptorDO>
        implements ElementSchool {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    @Extension(vendorName = "datanucleus", key = "gae.parent-pk", value = "true")
    private Key packageKey;

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

    public ElementSchoolDO(String name, PackageDO pack) {
        this.name = name;
        this.packageKey = pack.getKey();
        this.cards = new TreeSet<MetaCardDO>();
        this.attributes = new TreeSet<AttributeDO>();
        this.properties = new TreeSet<ElementSchoolPropertyDO>();
        this.descriptors = new TreeSet<ElementSchoolDescriptorDO>();
    }

    public Key getKey() {
        return key;
    }

    public Key getPackageKey() {
        return packageKey;
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

    protected boolean removeProperty(ElementSchoolPropertyDO property) {
        return properties.remove(property);
    }

    public void setName(String name) {
        this.name = name;
    }

    public MetaCardDO[] getCards() {
        return cards.toArray(new MetaCardDO[cards.size()]);
    }

    public AttributeDO[] getAttributes() {
        return attributes.toArray(new AttributeDO[attributes.size()]);
    }

    public boolean addCard(MetaCardDO card) {
        return cards.add(card);
    }

    public boolean removeCard(MetaCardDO card) {
        return cards.remove(card);
    }

    public AttributeDO getAttribute(String name) {
        for (AttributeDO attribute : attributes) {
            if (attribute.getName().equals(name)) return attribute;
        }
        return null;
    }

    public boolean addAttribute(AttributeDO attribute) {
        return attributes.add(attribute);
    }

    public boolean removeAttribute(AttributeDO attribute) {
        return attributes.remove(attribute);
    }

    public ElementSchoolDescriptorDO[] getDescriptors() {
        return descriptors.toArray(new ElementSchoolDescriptorDO[descriptors.size()]);
    }

    public boolean addDescriptor(ElementSchoolDescriptorDO descriptor) {
        return descriptors.add(descriptor);
    }

    public boolean removeDescriptor(ElementSchoolDescriptorDO descriptor) {
        return descriptors.remove(descriptor);
    }

    public ElementSchoolDO clone(PackageDO pack) {
        ElementSchoolDO elementSchool = new ElementSchoolDO(getName(), pack);
        for (MetaCardDO card : cards) {
            elementSchool.cards.add(card);
        }
        for (ElementSchoolDescriptorDO descriptor : descriptors) {
            ElementSchoolDescriptorDO cloneDescriptor =
                    new ElementSchoolDescriptorDO(elementSchool, descriptor.getLocale());
            elementSchool.addDescriptor(cloneDescriptor);
        }
        return elementSchool;
    }
}
