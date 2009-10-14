package com.killard.board.jdo.board;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.card.Action;
import com.killard.board.card.Attribute;
import com.killard.board.card.CardInstance;
import com.killard.board.environment.ActionValidator;
import com.killard.board.environment.AfterAction;
import com.killard.board.environment.BeforeAction;
import com.killard.board.jdo.AttributeHandler;
import com.killard.board.jdo.FunctionHelper;
import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.board.descriptor.BoardAttributeDescriptorDO;
import com.killard.board.jdo.card.AttributeDO;
import com.killard.board.jdo.card.descriptor.AttributeDescriptorDO;

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
public class BoardAttributeDO extends DescriptableDO<BoardAttributeDescriptorDO> implements Attribute {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private BoardElementSchoolDO elementSchool;

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
    private SortedSet<BoardAttributeDescriptorDO> descriptors;

    @NotPersistent
    private BoardManagerDO boardManager;

    public BoardAttributeDO(BoardElementSchoolDO elementSchool, AttributeDO attribute) {
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

        this.descriptors = new TreeSet<BoardAttributeDescriptorDO>();
        for (AttributeDescriptorDO descriptor : attribute.getAllDescriptors()) {
            this.descriptors.add(new BoardAttributeDescriptorDO(this, descriptor));
        }
    }

    public void restore(BoardManagerDO boardManager) {
        this.boardManager = boardManager;
    }

    public Key getKey() {
        return key;
    }

    public BoardElementSchoolDO getElementSchool() {
        return elementSchool;
    }

    public String getName() {
        return name;
    }

    protected SortedSet<BoardAttributeDescriptorDO> getDescriptors() {
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
    public List<Action> validateAction(CardInstance card, Action action) {
        return FunctionHelper.handler(boardManager, card, action, validators);
    }

    @BeforeAction(actionClass = Action.class, selfTargeted = false)
    public List<Action> beforeAction(CardInstance card, Action action) {
        return FunctionHelper.handler(boardManager, card, action, before);
    }

    @AfterAction(actionClass = Action.class, selfTargeted = false)
    public List<Action> afterAction(CardInstance card, Action action) {
        return FunctionHelper.handler(boardManager, card, action, after);
    }
}
