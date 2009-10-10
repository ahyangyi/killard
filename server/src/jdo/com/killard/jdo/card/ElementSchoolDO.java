package com.killard.jdo.card;

import com.google.appengine.api.datastore.Key;
import com.killard.jdo.DescriptableDO;

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
public class ElementSchoolDO extends DescriptableDO<ElementSchoolDescriptorDO> {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    @Extension(vendorName="datanucleus", key="gae.parent-pk", value="true")
    private Key packageKey;

    @Persistent
    private String id;

    @Persistent(mappedBy = "elementSchool")
    private SortedSet<CardDO> cards = new TreeSet<CardDO>();

    @Persistent(mappedBy = "elementSchool", defaultFetchGroup = "false")
    private SortedSet<AttributeDO> attributes = new TreeSet<AttributeDO>();

    @Persistent
    private SortedSet<ElementSchoolDescriptorDO> descriptors = new TreeSet<ElementSchoolDescriptorDO>();

    public ElementSchoolDO(String id, PackageDO pack) {
        this.id = id;
        this.packageKey = pack.getKey();
    }

    public Key getKey() {
        return key;
    }

    public Key getPackageKey() {
        return packageKey;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public CardDO[] getCards() {
        return cards.toArray(new CardDO[cards.size()]);
    }

    public AttributeDO[] getAttributes() {
        return attributes.toArray(new AttributeDO[attributes.size()]);
    }

    public boolean addCard(CardDO card) {
        return cards.add(card);
    }

    public boolean removeCard(CardDO card) {
        return cards.remove(card);
    }

    public AttributeDO getAttribute(String id) {
        for (AttributeDO attribute : attributes) {
            if (attribute.getId().equals(id)) return attribute;
        }
        return null;
    }

    public boolean addAttribute(AttributeDO attribute) {
        return attributes.add(attribute);
    }

    public boolean removeAttribute(AttributeDO attribute) {
        return attributes.remove(attribute);
    }

    protected SortedSet<ElementSchoolDescriptorDO> getDescriptors() {
        return descriptors;
    }

    public ElementSchoolDO clone(PackageDO pack) {
        ElementSchoolDO elementSchool = new ElementSchoolDO(getId(), pack);
        for (CardDO card : cards) {
            elementSchool.cards.add(card);
        }
        for (ElementSchoolDescriptorDO descriptor : descriptors) {
            ElementSchoolDescriptorDO cloneDescriptor = new ElementSchoolDescriptorDO(elementSchool, descriptor.getLocale());
            elementSchool.addDescriptor(cloneDescriptor);
        }
        return elementSchool;
    }
}
