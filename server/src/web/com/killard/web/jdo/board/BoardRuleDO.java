package com.killard.web.jdo.board;

import com.google.appengine.api.datastore.Key;
import com.killard.card.Action;
import com.killard.card.Attribute;
import com.killard.card.action.EndTurnAction;
import com.killard.card.action.KillCardAction;
import com.killard.card.action.NewCardAction;
import com.killard.environment.ActionValidator;
import com.killard.environment.AfterAction;
import com.killard.environment.BeforeAction;
import com.killard.environment.event.ActionListener;
import com.killard.web.jdo.AttributeHandler;
import com.killard.web.jdo.FunctionHelper;
import com.killard.web.jdo.card.RuleDO;

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
public class BoardRuleDO extends BoardDescriptableDO implements ActionListener {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    @Extension(vendorName="datanucleus", key="gae.parent-pk", value="true")
    private Key packageKey;

    @Persistent(serialized = "true")
    private List<AttributeHandler> validators = new ArrayList<AttributeHandler>();

    @Persistent(serialized = "true")
    private List<AttributeHandler> before = new ArrayList<AttributeHandler>();

    @Persistent(serialized = "true")
    private List<AttributeHandler> after = new ArrayList<AttributeHandler>();

    public BoardRuleDO(BoardPackageDO pack, RuleDO rule) {
        this.packageKey = pack.getPackageKey();
        validators.addAll(Arrays.asList(rule.getValidators()));
        before.addAll(Arrays.asList(rule.getBefore()));
        after.addAll(Arrays.asList(rule.getAfter()));
    }

    public Key getKey() {
        return key;
    }

    public Key getPackageKey() {
        return packageKey;
    }

    @ActionValidator(actionClass = Action.class, selfTargeted = false)
    public List<Action> validateAction(BoardManagerDO owner, Action action) {
        List<Action> result = FunctionHelper.handler(owner, action, validators);
        getLog().fine("validate " + action.getClass().getSimpleName() + " : " + result);
        return result;
    }

    @BeforeAction(actionClass = Action.class, selfTargeted = false)
    public List<Action> beforeAction(BoardManagerDO owner, Action action) {
        return FunctionHelper.handler(owner, action, before);
    }

    @AfterAction(actionClass = Action.class, selfTargeted = false)
    public List<Action> afterAction(BoardManagerDO owner, Action action) {
        return FunctionHelper.handler(owner, action, after);
    }

    @AfterAction(actionClass = EndTurnAction.class, selfTargeted = false)
    public Object after(BoardManagerDO boardManager, EndTurnAction action) {
        boardManager.moveToNext();
        return null;
    }

    @AfterAction(actionClass = NewCardAction.class, selfTargeted = false)
    public void after(BoardManagerDO boardManager, NewCardAction action) {
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
