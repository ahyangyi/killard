package com.killard.board.jdo.board;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;
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
    private Text definition;

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

    protected AttributeDO(ElementSchoolDO elementSchool, String name, boolean visible,
                          String definition,
                          List<AttributeHandler> validators,
                          List<AttributeHandler> before,
                          List<AttributeHandler> after) {
        this.elementSchool = elementSchool;
        this.name = name;
        this.visible = visible;

        this.definition = new Text(definition);

        this.validators = new ArrayList<AttributeHandler>(validators);
        this.before = new ArrayList<AttributeHandler>(before);
        this.after = new ArrayList<AttributeHandler>(after);

        this.properties = new TreeSet<AttributePropertyDO>();
        this.descriptors = new TreeSet<AttributeDescriptorDO>();
    }

    protected AttributeDO(ElementSchoolDO elementSchool, AttributeDO source) {
        this(elementSchool, source.name, source.visible, source.getDefinition(), source.validators, source.before,
                source.after);
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

    protected boolean addDescriptor(String locale, String name, String description) {
        return descriptors.add(new AttributeDescriptorDO(this, locale, name, description));
    }

    protected boolean addProperty(String name, String data) {
        return properties.add(new AttributePropertyDO(this, name, data));
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefinition() {
        return definition.getValue();
    }

    public boolean isVisible() {
        return visible;
    }

    public AttributeDescriptorDO[] getDescriptors() {
        return descriptors.toArray(new AttributeDescriptorDO[descriptors.size()]);
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
