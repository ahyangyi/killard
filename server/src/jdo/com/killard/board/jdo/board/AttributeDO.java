package com.killard.board.jdo.board;

import com.google.appengine.api.datastore.Key;
import com.killard.board.jdo.AttributeHandler;
import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.FunctionHelper;
import com.killard.board.jdo.board.descriptor.AttributeDescriptorDO;
import com.killard.board.jdo.board.property.AttributePropertyDO;
import com.killard.board.card.Attribute;
import com.killard.board.card.Action;
import com.killard.board.card.Card;
import com.killard.board.environment.AfterAction;
import com.killard.board.environment.BeforeAction;
import com.killard.board.environment.ActionValidator;

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
public class AttributeDO extends DescriptableDO<AttributeDO, AttributePropertyDO, AttributeDescriptorDO>
        implements Attribute<AttributeDO> {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private ElementSchoolDO elementSchool;

    @Persistent
    private String name;

    @Persistent
    private Boolean visible;

    @Persistent(serialized = "true")
    private List<AttributeHandler> validators;

    @Persistent(serialized = "true")
    private List<AttributeHandler> before;

    @Persistent(serialized = "true")
    private List<AttributeHandler> after;

    @Persistent
    private SortedSet<AttributePropertyDO> properties;

    @Persistent(defaultFetchGroup = "false")
    private SortedSet<AttributeDescriptorDO> descriptors;

    public AttributeDO(String name, ElementSchoolDO elementSchool, boolean visible,
                       List<AttributeHandler> validators,
                       List<AttributeHandler> before,
                       List<AttributeHandler> after) {
        this.name = name;
        this.elementSchool = elementSchool;
        this.visible = visible;
        this.validators = new ArrayList<AttributeHandler>(validators);
        this.before = new ArrayList<AttributeHandler>(before);
        this.after = new ArrayList<AttributeHandler>(after);
        this.properties = new TreeSet<AttributePropertyDO>();
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

    public AttributePropertyDO[] getProperties() {
        return properties.toArray(new AttributePropertyDO[properties.size()]);
    }

    protected boolean addProperty(String name, String data) {
        return properties.add(new AttributePropertyDO(this, name, data));
    }

    protected boolean removeProperty(AttributePropertyDO property) {
        return properties.remove(property);
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVisible() {
        return visible;
    }

    public AttributeDescriptorDO[] getDescriptors() {
        return descriptors.toArray(new AttributeDescriptorDO[descriptors.size()]);
    }

    public boolean addDescriptor(AttributeDescriptorDO descriptor) {
        return descriptors.add(descriptor);
    }

    public boolean removeDescriptor(AttributeDescriptorDO descriptor) {
        return descriptors.remove(descriptor);
    }

    public AttributeDO clone(ElementSchoolDO elementSchool) {
        AttributeDO attribute = new AttributeDO(getName(),
                getElementSchool(), isVisible(), validators, before, after);
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

    @ActionValidator(actionClass = Action.class, selfTargeted = false)
    public List<Action> validateAction(BoardDO board, Card owner, Action action) {
        return FunctionHelper.handler(board, owner, action, validators);
    }

    @BeforeAction(actionClass = Action.class, selfTargeted = false)
    public List<Action> beforeAction(BoardDO board, Card owner, Action action) {
        return FunctionHelper.handler(board, owner, action, before);
    }

    @AfterAction(actionClass = Action.class, selfTargeted = false)
    public List<Action> afterAction(BoardDO board, Card owner, Action action) {
        return FunctionHelper.handler(board, owner, action, after);
    }
}
