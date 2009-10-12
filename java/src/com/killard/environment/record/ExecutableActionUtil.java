package com.killard.environment.record;

import com.killard.card.Action;
import com.killard.card.action.AddCardAttributeAction;
import com.killard.card.action.CastCardAction;
import com.killard.card.action.ChangeCardAttackAction;
import com.killard.card.action.ChangeCardHealthAction;
import com.killard.card.action.ChangeCardMaxHealthAction;
import com.killard.card.action.ChangePlayerElementAction;
import com.killard.card.action.ChangePlayerHealthAction;
import com.killard.card.action.KillCardAction;
import com.killard.card.action.PlayCardAction;
import com.killard.card.action.EndTurnAction;
import com.killard.card.action.RemoveCardAttributeAction;
import com.killard.card.action.RemoveSkillAction;
import com.killard.environment.ExecutableAction;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class ExecutableActionUtil {

    public static Map<Class<? extends Action>, ExecutableAction> buildExecutableActionsMap() {
        Map<Class<? extends Action>, ExecutableAction> map = new HashMap<Class<? extends Action>, ExecutableAction>();
        map.put(PlayCardAction.class, new ExecutablePlayCardAction());
        map.put(CastCardAction.class, new ExecutableCastCardAction());
        map.put(KillCardAction.class, new ExecutableKillCardAction());
        map.put(AddCardAttributeAction.class, new ExecutableAddCardAttributeAction());
        map.put(ChangeCardAttackAction.class, new ExecutableChangeCardAttackAction());
        map.put(ChangeCardHealthAction.class, new ExecutableChangeCardHealthAction());
        map.put(ChangeCardMaxHealthAction.class, new ExecutableChangeCardMaxHealthAction());
        map.put(ChangePlayerElementAction.class, new ExecutableChangePlayerElementAction());
        map.put(ChangePlayerHealthAction.class, new ExecutableChangePlayerHealthAction());
        map.put(EndTurnAction.class, new ExecutableEndTurnAction());
        map.put(RemoveCardAttributeAction.class, new ExecutableAddCardAttributeAction());
        map.put(RemoveSkillAction.class, new ExecutableRemoveSkillAction());
        return map;
    }
}
