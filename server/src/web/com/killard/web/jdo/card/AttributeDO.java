package com.killard.web.jdo.card;

import com.google.appengine.api.datastore.Key;
import com.killard.card.Action;
import com.killard.card.Attribute;
import com.killard.card.CardInstance;
import com.killard.environment.ActionValidator;
import com.killard.environment.AfterAction;
import com.killard.environment.BeforeAction;
import com.killard.web.jdo.AttributeHandler;
import com.killard.web.jdo.DescriptableDO;
import com.killard.web.jdo.FunctionHelper;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
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
public class AttributeDO extends DescriptableDO<AttributeDescriptorDO> implements Attribute {

    @Persistent
    @Extension(vendorName = "datanucleus", key = "gae.parent-pk", value = "true")
    private Key cardKey;

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

    public AttributeDO(String id, CardDO card, boolean hidden, boolean useful, boolean harmful,
                       List<AttributeHandler> validators,
                       List<AttributeHandler> before,
                       List<AttributeHandler> after) {
        super(id);
        this.cardKey = card.getKey();
        this.hidden = hidden;
        this.useful = useful;
        this.harmful = harmful;
        this.validators = validators;
        this.before = before;
        this.after = after;
    }

    public Key getCardKey() {
        return cardKey;
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

    @ActionValidator(actionClass = Action.class, selfTargeted = false)
    public List<Action> validateAction(CardInstance card, Action action) {
        return FunctionHelper.handler(card, action, validators);
    }

    @BeforeAction(actionClass = Action.class, selfTargeted = false)
    public List<Action> beforeAction(CardInstance card, Action action) {
        return FunctionHelper.handler(card, action, before);
    }

    @AfterAction(actionClass = Action.class, selfTargeted = false)
    public List<Action> afterAction(CardInstance card, Action action) {
        return FunctionHelper.handler(card, action, after);
    }

    protected SortedSet<AttributeDescriptorDO> getDescriptors() {
        return descriptors;
    }

    public AttributeDO clone(CardDO card) {
        AttributeDO attribute = new AttributeDO(getId(), card, hidden, useful, harmful, validators, before, after);
        for (AttributeDescriptorDO descriptor : descriptors) {
            AttributeDescriptorDO cloneDescriptor = new AttributeDescriptorDO(attribute, descriptor.getLocale());
            attribute.addDescriptor(cloneDescriptor);
        }
        return attribute;
    }
}
