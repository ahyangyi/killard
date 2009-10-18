package com.killard.board.jdo.board;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.card.Action;
import com.killard.board.card.Attribute;
import com.killard.board.card.action.DealCardAction;
import com.killard.board.card.action.DrawCardAction;
import com.killard.board.card.action.DropCardAction;
import com.killard.board.card.action.EndTurnAction;
import com.killard.board.card.action.EquipCardAction;
import com.killard.board.environment.ActionValidator;
import com.killard.board.environment.AfterAction;
import com.killard.board.environment.BeforeAction;
import com.killard.board.environment.event.ActionListener;
import com.killard.board.jdo.AttributeHandler;
import com.killard.board.jdo.FunctionHelper;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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
public class RuleDO implements ActionListener<RuleDO> {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private Text definition;

    @Persistent(serialized = "true")
    private List<AttributeHandler> validators;

    @Persistent(serialized = "true")
    private List<AttributeHandler> before;

    @Persistent(serialized = "true")
    private List<AttributeHandler> after;

    protected RuleDO(PackageDO pack,
                     String definition,
                     List<AttributeHandler> validators,
                     List<AttributeHandler> before,
                     List<AttributeHandler> after) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(pack.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), 1);
        this.key = keyBuilder.getKey();
        this.definition = new Text(definition);
        this.validators = new ArrayList<AttributeHandler>(validators);
        this.before = new ArrayList<AttributeHandler>(before);
        this.after = new ArrayList<AttributeHandler>(after);
    }

    protected RuleDO(PackageDO pack, RuleDO source) {
        this(pack, source.definition.getValue(), source.validators, source.before, source.after);
    }

    public Key getKey() {
        return key;
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

    public String getDefinition() {
        return definition.getValue();
    }

    public void setDefinition(String definition) {
        this.definition = new Text(definition);
    }

    @ActionValidator(actionClass = Action.class, selfTargeted = false)
    public List<Action> validateAction(BoardDO board, BoardDO owner, Action action) {
        List<Action> result = FunctionHelper.handler(owner, owner, action, validators);
        getLog().fine("validate " + action.getClass().getSimpleName() + " : " + result);
        return result;
    }

    @BeforeAction(actionClass = Action.class, selfTargeted = false)
    public List<Action> beforeAction(BoardDO board, BoardDO owner, Action action) {
        return FunctionHelper.handler(owner, owner, action, before);
    }

    @AfterAction(actionClass = Action.class, selfTargeted = false)
    public List<Action> afterAction(BoardDO board, BoardDO owner, Action action) {
        return FunctionHelper.handler(owner, owner, action, after);
    }

    @AfterAction(actionClass = EndTurnAction.class, selfTargeted = false)
    public void after(BoardDO board, BoardDO owner, EndTurnAction action) {
        owner.moveToNext();
    }

    @AfterAction(actionClass = DrawCardAction.class, selfTargeted = false)
    public Action before(BoardDO board, BoardDO owner, DrawCardAction action) {
        return new DealCardAction(owner.dealCard(), action.getTarget());
    }

    @AfterAction(actionClass = EquipCardAction.class, selfTargeted = false)
    public void after(BoardDO board, BoardDO owner, EquipCardAction action) {
        for (Attribute attribute : action.getTarget().getAttributes())
            owner.addActionListener(attribute, action.getTarget());
    }

    @BeforeAction(actionClass = DropCardAction.class, selfTargeted = false)
    public void before(BoardDO board, BoardDO owner, DropCardAction action) {
        for (Attribute attribute : action.getTarget().getAttributes())
            owner.removeActionListener(attribute);
    }

    public Logger getLog() {
        return Logger.getLogger(getClass().getName());
    }

    public int compareTo(RuleDO compare) {
        return getKey().compareTo(compare.getKey());
    }

    public Object getProperty(String name) {
        return null;
    }
}
