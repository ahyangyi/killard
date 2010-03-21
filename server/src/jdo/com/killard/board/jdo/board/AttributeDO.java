package com.killard.board.jdo.board;

import com.killard.board.card.Action;
import com.killard.board.card.Attribute;
import com.killard.board.card.Card;
import com.killard.board.environment.ActionValidator;
import com.killard.board.environment.AfterAction;
import com.killard.board.environment.BeforeAction;
import com.killard.board.jdo.AttributeHandler;
import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.FunctionHelper;
import com.killard.board.jdo.board.descriptor.AttributeDescriptorDO;
import com.killard.board.jdo.board.property.AttributePropertyDO;

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
public class AttributeDO extends DescriptableDO<AttributeDO, AttributePropertyDO, AttributeDescriptorDO>
        implements Attribute<AttributeDO> {

    @Persistent
    private ElementSchoolDO elementSchool;

    @Persistent
    private boolean visible;

    @Persistent(serialized = "true", defaultFetchGroup = "true")
    private List<AttributeHandler> validators;

    @Persistent(serialized = "true", defaultFetchGroup = "true")
    private List<AttributeHandler> before;

    @Persistent(serialized = "true", defaultFetchGroup = "true")
    private List<AttributeHandler> after;

    @Persistent(defaultFetchGroup = "true")
    @Element(dependent = "true")
    private List<AttributePropertyDO> properties;

    @Persistent
    @Element(dependent = "true")
    private transient List<AttributeDescriptorDO> descriptors;

    protected AttributeDO(ElementSchoolDO elementSchool, String name, boolean visible,
                          List<AttributeHandler> validators,
                          List<AttributeHandler> before,
                          List<AttributeHandler> after) {
        super(elementSchool, name);

        this.elementSchool = elementSchool;
        this.visible = visible;

        this.validators = new ArrayList<AttributeHandler>(validators);
        this.before = new ArrayList<AttributeHandler>(before);
        this.after = new ArrayList<AttributeHandler>(after);

        this.properties = new ArrayList<AttributePropertyDO>();
        this.descriptors = new ArrayList<AttributeDescriptorDO>();
    }

    protected AttributeDO(ElementSchoolDO elementSchool, AttributeDO source) {
        this(elementSchool, source.getName(), source.visible, source.validators, source.before,
                source.after);
    }

    public ElementSchoolDO getElementSchool() {
        return elementSchool;
    }

    public AttributePropertyDO[] getProperties() {
        return properties.toArray(new AttributePropertyDO[properties.size()]);
    }

    protected boolean addProperty(String name, String data) {
        return properties.add(new AttributePropertyDO(this, name, data));
    }

    public boolean isVisible() {
        return visible;
    }

    public AttributeDescriptorDO[] getDescriptors() {
        return descriptors.toArray(new AttributeDescriptorDO[descriptors.size()]);
    }

    protected boolean addDescriptor(String locale, String name, String description) {
        return descriptors.add(new AttributeDescriptorDO(this, locale, name, description));
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
