package com.killard.board.jdo.board;

import com.google.appengine.api.datastore.Key;
import com.killard.board.jdo.AttributeHandler;
import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.board.descriptor.AttributeDescriptorDO;

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
    private String name;

    @Persistent
    private Boolean visible;

    @Persistent
    private Boolean useful;

    @Persistent
    private Boolean harmful;

    @Persistent(serialized = "true")
    private List<AttributeHandler> validators;

    @Persistent(serialized = "true")
    private List<AttributeHandler> before;

    @Persistent(serialized = "true")
    private List<AttributeHandler> after;

    @Persistent(defaultFetchGroup = "false")
    private SortedSet<AttributeDescriptorDO> descriptors;

    public AttributeDO(String name, ElementSchoolDO elementSchool, boolean visible, boolean useful, boolean harmful,
                       List<AttributeHandler> validators,
                       List<AttributeHandler> before,
                       List<AttributeHandler> after) {
        this.name = name;
        this.elementSchool = elementSchool;
        this.visible = visible;
        this.useful = useful;
        this.harmful = harmful;
        this.validators = new ArrayList<AttributeHandler>(validators);
        this.before = new ArrayList<AttributeHandler>(before);
        this.after = new ArrayList<AttributeHandler>(after);
        this.descriptors = new TreeSet<AttributeDescriptorDO>();
    }

    public Key getKey() {
        return key;
    }

    public ElementSchoolDO getElementSchool() {
        return elementSchool;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVisible() {
        return visible;
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

    public AttributeDO clone(ElementSchoolDO elementSchool) {
        AttributeDO attribute = new AttributeDO(getName(),
                getElementSchool(), isVisible(), isUseful(), isHarmful(), validators, before, after);
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
