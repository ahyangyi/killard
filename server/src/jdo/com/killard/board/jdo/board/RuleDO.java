package com.killard.board.jdo.board;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;
import com.killard.board.jdo.AttributeHandler;
import com.killard.board.jdo.FunctionHelper;
import com.killard.board.jdo.game.BoardDO;
import com.killard.board.environment.ActionValidator;
import com.killard.board.environment.BeforeAction;
import com.killard.board.environment.AfterAction;
import com.killard.board.environment.event.ActionListener;
import com.killard.board.card.Action;
import com.killard.board.card.Attribute;
import com.killard.board.card.action.EndTurnAction;
import com.killard.board.card.action.DrawCardAction;
import com.killard.board.card.action.DealCardAction;
import com.killard.board.card.action.EquipCardAction;
import com.killard.board.card.action.DropCardAction;

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
    @Extension(vendorName="datanucleus", key="gae.parent-pk", value="true")
    private Key packageKey;

    @Persistent
    private Text definition;

    @Persistent(serialized = "true")
    private List<AttributeHandler> validators;

    @Persistent(serialized = "true")
    private List<AttributeHandler> before;

    @Persistent(serialized = "true")
    private List<AttributeHandler> after;

    public RuleDO(PackageDO pack,
                  List<AttributeHandler> validators,
                  List<AttributeHandler> before,
                  List<AttributeHandler> after) {
        this.packageKey = pack.getKey();
        this.validators = new ArrayList<AttributeHandler>(validators);
        this.before = new ArrayList<AttributeHandler>(before);
        this.after = new ArrayList<AttributeHandler>(after);
    }

    public Key getKey() {
        return key;
    }

    public Key getPackageKey() {
        return packageKey;
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
    public List<Action> validateAction(BoardDO owner, Action action) {
        List<Action> result = FunctionHelper.handler(owner, owner, action, validators);
        getLog().fine("validate " + action.getClass().getSimpleName() + " : " + result);
        return result;
    }

    @BeforeAction(actionClass = Action.class, selfTargeted = false)
    public List<Action> beforeAction(BoardDO owner, Action action) {
        return FunctionHelper.handler(owner, owner, action, before);
    }

    @AfterAction(actionClass = Action.class, selfTargeted = false)
    public List<Action> afterAction(BoardDO owner, Action action) {
        return FunctionHelper.handler(owner, owner, action, after);
    }

    @AfterAction(actionClass = EndTurnAction.class, selfTargeted = false)
    public void after(BoardDO owner, EndTurnAction action) {
//        owner.moveToNext();
    }

    @AfterAction(actionClass = DrawCardAction.class, selfTargeted = false)
    public Action before(BoardDO owner, DrawCardAction action) {
//        return new DealCardAction(action.getTarget(), owner.dealCard());
        return null;
    }

    @AfterAction(actionClass = EquipCardAction.class, selfTargeted = false)
    public void after(BoardDO owner, EquipCardAction action) {
        for (Attribute attribute : action.getTarget().getAttributes())
            owner.addActionListener(attribute, action.getTarget());
    }

    @BeforeAction(actionClass = DropCardAction.class, selfTargeted = false)
    public void before(BoardDO owner, DropCardAction action) {
        for (Attribute attribute : action.getTarget().getAttributes())
            owner.removeActionListener(attribute);
    }

    public Logger getLog() {
        return Logger.getLogger(getClass().getName());
    }

    public int compareTo(RuleDO compare) {
        return (int) (getKey().getId() - compare.getKey().getId());
    }

    public RuleDO clone(PackageDO pack) {
        return new RuleDO(pack, validators, before, after);
    }
}
