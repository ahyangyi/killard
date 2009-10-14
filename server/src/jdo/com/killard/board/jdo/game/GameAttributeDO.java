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
import com.killard.board.jdo.FunctionHelper;
import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.game.descriptor.GameAttributeDescriptorDO;
import com.killard.board.jdo.board.AttributeDO;
import com.killard.board.jdo.board.descriptor.AttributeDescriptorDO;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.NotPersistent;
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
public class GameAttributeDO extends DescriptableDO<GameAttributeDO, GameAttributeDescriptorDO> implements Attribute<GameAttributeDO> {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private GameElementSchoolDO elementSchool;

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

    @Persistent
    private SortedSet<GameAttributeDescriptorDO> descriptors;

    @NotPersistent
    private BoardManagerDO boardManager;

    public GameAttributeDO(GameElementSchoolDO elementSchool, AttributeDO attribute) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(elementSchool.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), attribute.getKey().getId());
        this.key = keyBuilder.getKey();
        
        this.elementSchool = elementSchool;
        this.name = attribute.getName();
        this.visible = attribute.isVisible();
        this.useful = attribute.isUseful();
        this.harmful = attribute.isHarmful();

        this.validators = new ArrayList<AttributeHandler>(Arrays.asList(attribute.getValidators()));
        this.before = new ArrayList<AttributeHandler>(Arrays.asList(attribute.getBefore()));
        this.after = new ArrayList<AttributeHandler>(Arrays.asList(attribute.getAfter()));

        this.descriptors = new TreeSet<GameAttributeDescriptorDO>();
        for (AttributeDescriptorDO descriptor : attribute.getAllDescriptors()) {
            this.descriptors.add(new GameAttributeDescriptorDO(this, descriptor));
        }
    }

    public void restore(BoardManagerDO boardManager) {
        this.boardManager = boardManager;
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

    protected SortedSet<GameAttributeDescriptorDO> getDescriptors() {
        return descriptors;
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

    @ActionValidator(actionClass = Action.class, selfTargeted = false)
    public List<Action> validateAction(Card card, Action action) {
        return FunctionHelper.handler(boardManager, card, action, validators);
    }

    @BeforeAction(actionClass = Action.class, selfTargeted = false)
    public List<Action> beforeAction(Card card, Action action) {
        return FunctionHelper.handler(boardManager, card, action, before);
    }

    @AfterAction(actionClass = Action.class, selfTargeted = false)
    public List<Action> afterAction(Card card, Action action) {
        return FunctionHelper.handler(boardManager, card, action, after);
    }
}
