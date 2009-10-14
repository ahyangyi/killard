package com.killard.board.jdo.game;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.card.Action;
import com.killard.board.card.Attribute;
import com.killard.board.card.Card;
import com.killard.board.card.ElementSchool;
import com.killard.board.environment.ActionValidator;
import com.killard.board.environment.AfterAction;
import com.killard.board.environment.BeforeAction;
import com.killard.board.jdo.AttributeHandler;
import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.FunctionHelper;
import com.killard.board.jdo.board.AttributeDO;
import com.killard.board.jdo.board.descriptor.AttributeDescriptorDO;
import com.killard.board.jdo.board.property.AttributePropertyDO;
import com.killard.board.jdo.game.descriptor.GameAttributeDescriptorDO;
import com.killard.board.jdo.game.property.GameAttributePropertyDO;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.ArrayList;
import java.util.Arrays;
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
public class GameAttributeDO extends DescriptableDO<GameAttributeDO, GameAttributePropertyDO, GameAttributeDescriptorDO> implements Attribute<GameAttributeDO> {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private GameElementSchoolDO elementSchool;

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
    private SortedSet<GameAttributePropertyDO> properties;

    @Persistent
    private SortedSet<GameAttributeDescriptorDO> descriptors;

    public GameAttributeDO(GameElementSchoolDO elementSchool, AttributeDO attribute) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(elementSchool.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), attribute.getKey().getId());
        this.key = keyBuilder.getKey();
        
        this.elementSchool = elementSchool;
        this.name = attribute.getName();
        this.visible = attribute.isVisible();

        this.validators = new ArrayList<AttributeHandler>(Arrays.asList(attribute.getValidators()));
        this.before = new ArrayList<AttributeHandler>(Arrays.asList(attribute.getBefore()));
        this.after = new ArrayList<AttributeHandler>(Arrays.asList(attribute.getAfter()));

        this.properties = new TreeSet<GameAttributePropertyDO>();
        for (AttributePropertyDO property : attribute.getProperties()) {
            this.properties.add(new GameAttributePropertyDO(this, property));
        }

        this.descriptors = new TreeSet<GameAttributeDescriptorDO>();
        for (AttributeDescriptorDO descriptor : attribute.getDescriptors()) {
            this.descriptors.add(new GameAttributeDescriptorDO(this, descriptor));
        }
    }

    public Key getKey() {
        return key;
    }

    public ElementSchool getElementSchool() {
        return elementSchool;
    }

    public String getName() {
        return name;
    }

    public GameAttributePropertyDO[] getProperties() {
        return properties.toArray(new GameAttributePropertyDO[properties.size()]);
    }

    protected boolean addProperty(String name, String data) {
        return false;
    }

    protected boolean removeProperty(GameAttributePropertyDO property) {
        return false;
    }

    protected GameAttributeDescriptorDO[] getDescriptors() {
        return descriptors.toArray(new GameAttributeDescriptorDO[descriptors.size()]);
    }

    public boolean isVisible() {
        return visible;
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
