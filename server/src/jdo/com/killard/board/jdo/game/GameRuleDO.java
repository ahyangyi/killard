package com.killard.board.jdo.game;

import com.google.appengine.api.datastore.Key;
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
import com.killard.board.jdo.board.RuleDO;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.ArrayList;
import java.util.Arrays;
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
public class GameRuleDO implements ActionListener<GameRuleDO> {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    @Extension(vendorName="datanucleus", key="gae.parent-pk", value="true")
    private Key packageKey;

    @Persistent(serialized = "true")
    private List<AttributeHandler> validators;

    @Persistent(serialized = "true")
    private List<AttributeHandler> before;

    @Persistent(serialized = "true")
    private List<AttributeHandler> after;

    public GameRuleDO(GamePackageDO pack, RuleDO rule) {
        this.packageKey = pack.getKey();
        validators = new ArrayList<AttributeHandler>(Arrays.asList(rule.getValidators()));
        before = new ArrayList<AttributeHandler>(Arrays.asList(rule.getBefore()));
        after = new ArrayList<AttributeHandler>(Arrays.asList(rule.getAfter()));
    }

    public Key getKey() {
        return key;
    }

    public Key getPackageKey() {
        return packageKey;
    }

    @ActionValidator(actionClass = Action.class, selfTargeted = false)
    public List<Action> validateAction(BoardManagerDO owner, Action action) {
        List<Action> result = FunctionHelper.handler(owner, owner, action, validators);
        getLog().fine("validate " + action.getClass().getSimpleName() + " : " + result);
        return result;
    }

    @BeforeAction(actionClass = Action.class, selfTargeted = false)
    public List<Action> beforeAction(BoardManagerDO owner, Action action) {
        return FunctionHelper.handler(owner, owner, action, before);
    }

    @AfterAction(actionClass = Action.class, selfTargeted = false)
    public List<Action> afterAction(BoardManagerDO owner, Action action) {
        return FunctionHelper.handler(owner, owner, action, after);
    }

    @AfterAction(actionClass = EndTurnAction.class, selfTargeted = false)
    public void after(BoardManagerDO owner, EndTurnAction action) {
        owner.moveToNext();
    }

    @AfterAction(actionClass = DrawCardAction.class, selfTargeted = false)
    public Action before(BoardManagerDO owner, DrawCardAction action) {
        return new DealCardAction(action.getTarget(), owner.dealCard());
    }

    @AfterAction(actionClass = EquipCardAction.class, selfTargeted = false)
    public void after(BoardManagerDO owner, EquipCardAction action) {
        for (Attribute attribute : action.getTarget().getAttributes())
            owner.addActionListener(attribute, action.getTarget());
    }

    @BeforeAction(actionClass = DropCardAction.class, selfTargeted = false)
    public void before(BoardManagerDO owner, DropCardAction action) {
        for (Attribute attribute : action.getTarget().getAttributes())
            owner.removeActionListener(attribute);
    }

    public Logger getLog() {
        return Logger.getLogger(getClass().getName());
    }

    public int compareTo(GameRuleDO compare) {
        return (int) (getKey().getId() - compare.getKey().getId());
    }
}