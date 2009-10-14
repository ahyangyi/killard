package com.killard.board.jdo.game;

import com.google.appengine.api.datastore.Key;
import com.killard.board.card.Action;
import com.killard.board.card.Attribute;
import com.killard.board.card.action.EndTurnAction;
import com.killard.board.card.action.KillCardAction;
import com.killard.board.card.action.EquipCardAction;
import com.killard.board.card.action.DrawCardAction;
import com.killard.board.card.action.DealCardAction;
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
import javax.jdo.annotations.NotPersistent;
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
public class BoardRuleDO implements ActionListener {

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

    @NotPersistent
    private BoardManagerDO boardManager;

    public BoardRuleDO(BoardPackageDO pack, RuleDO rule) {
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

    public void restore(BoardManagerDO boardManager) {
        this.boardManager = boardManager;
    }

    @ActionValidator(actionClass = Action.class, selfTargeted = false)
    public List<Action> validateAction(BoardManagerDO owner, Action action) {
        List<Action> result = FunctionHelper.handler(boardManager, owner, action, validators);
        getLog().fine("validate " + action.getClass().getSimpleName() + " : " + result);
        return result;
    }

    @BeforeAction(actionClass = Action.class, selfTargeted = false)
    public List<Action> beforeAction(BoardManagerDO owner, Action action) {
        return FunctionHelper.handler(boardManager, owner, action, before);
    }

    @AfterAction(actionClass = Action.class, selfTargeted = false)
    public List<Action> afterAction(BoardManagerDO owner, Action action) {
        return FunctionHelper.handler(boardManager, owner, action, after);
    }

    @AfterAction(actionClass = EndTurnAction.class, selfTargeted = false)
    public void after(BoardManagerDO boardManager, EndTurnAction action) {
        boardManager.moveToNext();
    }

    @AfterAction(actionClass = DrawCardAction.class, selfTargeted = false)
    public Action before(BoardManagerDO boardManager, DrawCardAction action) {
        return new DealCardAction(action.getTarget(), boardManager.dealCard());
    }

    @AfterAction(actionClass = EquipCardAction.class, selfTargeted = false)
    public void after(BoardManagerDO boardManager, EquipCardAction action) {
        for (Attribute attribute : action.getTarget().getAttributes())
            boardManager.addActionListener(attribute, action.getTarget());
    }

    @BeforeAction(actionClass = KillCardAction.class, selfTargeted = false)
    public void before(BoardManagerDO boardManager, KillCardAction action) {
        for (Attribute attribute : action.getTarget().getAttributes())
            boardManager.removeActionListener(attribute);
    }

    public Logger getLog() {
        return Logger.getLogger(getClass().getName());
    }
}
