package com.killard.web.jdo;

import com.google.appengine.api.datastore.Key;
import com.killard.card.ElementSchool;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
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
public class ElementSchoolDO extends DescriptableDO<ElementSchoolDescriptorDO> implements ElementSchool {

    @Persistent
    @Extension(vendorName="datanucleus", key="gae.parent-pk", value="true")
    private Key packageKey;

    @Persistent
    private SortedSet<CardDO> cards = new TreeSet<CardDO>();

    @Persistent
    private SortedSet<ElementSchoolDescriptorDO> descriptors = new TreeSet<ElementSchoolDescriptorDO>();

    public ElementSchoolDO(String id, PackageDO pack) {
        super(id);
        this.packageKey = pack.getKey();
    }

    public Key getPackageKey() {
        return packageKey;
    }

    public CardDO[] getCards() {
        return cards.toArray(new CardDO[cards.size()]);
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
