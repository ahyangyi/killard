package com.killard.web.jdo.card;

import com.google.appengine.api.datastore.Key;
import com.killard.web.jdo.AttributeHandler;
import com.killard.web.jdo.DescriptableDO;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.ArrayList;
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
public class AttributeDO extends DescriptableDO<AttributeDescriptorDO> {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private ElementSchoolDO elementSchool;

    @Persistent
    private String id;

    @Persistent
    private Boolean hidden;

    @Persistent
    private Boolean useful;

    @Persistent
    private Boolean harmful;

    @Persistent(serialized = "true")
    private List<AttributeHandler> validators = new ArrayList<AttributeHandler>();

    @Persistent(serialized = "true")
    private List<AttributeHandler> before = new ArrayList<AttributeHandler>();

    @Persistent(serialized = "true")
    private List<AttributeHandler> after = new ArrayList<AttributeHandler>();

    @Persistent(defaultFetchGroup = "false")
    private SortedSet<AttributeDescriptorDO> descriptors = new TreeSet<AttributeDescriptorDO>();

    public AttributeDO(String id, ElementSchoolDO elementSchool, boolean hidden, boolean useful, boolean harmful,
                       List<AttributeHandler> validators,
                       List<AttributeHandler> before,
                       List<AttributeHandler> after) {
        this.id = id;
        this.elementSchool = elementSchool;
        this.hidden = hidden;
        this.useful = useful;
        this.harmful = harmful;
        this.validators = validators;
        this.before = before;
        this.after = after;
    }

    public Key getKey() {
        return key;
    }

    public ElementSchoolDO getElementSchool() {
        return elementSchool;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isHidden() {
        return hidden;
    }

    public boolean isUseful() {
        return useful;
    }

    public boolean isHarmful() {
        return harmful;
    }

    protected SortedSet<AttributeDescriptorDO> getDescriptors() {
        return descriptors;
    }

    public AttributeDO clone(CardDO card) {
        AttributeDO attribute = new AttributeDO(getId(),
                getElementSchool(), isHidden(), isUseful(), isHarmful(), validators, before, after);
        for (AttributeDescriptorDO descriptor : descriptors) {
            AttributeDescriptorDO cloneDescriptor = new AttributeDescriptorDO(attribute, descriptor.getLocale());
            attribute.addDescriptor(cloneDescriptor);
        }
        return attribute;
    }

    public AttributeHandler[] getValidators() {
        return validators.toArray(new AttributeHandler[validators.size()]);
    }

    public AttributeHandler[] getBefore() {
        return before.toArray(new AttributeHandler[before.size()]);
    }

    public AttributeHandler[] getAfter() {
        return after.toArray(new AttributeHandler[after.size()]);
    }
}
