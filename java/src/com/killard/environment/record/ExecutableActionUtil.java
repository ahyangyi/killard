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
import com.killard.card.action.BeginTurnAction;
import com.killard.card.action.DrawCardAction;
import com.killard.card.action.DiscardCardAction;
import com.killard.card.action.LoseAction;
import com.killard.card.action.WinAction;
import com.killard.card.action.RevivePlayerAction;
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
public final class ExecutableActionUtil {

    public static Map<Class<? extends Action>, ExecutableAction> buildExecutableActionsMap() {
        Map<Class<? extends Action>, ExecutableAction> map = new HashMap<Class<? extends Action>, ExecutableAction>();
        map.put(AddCardAttributeAction.class, new ExecutableAddCardAttributeAction());
        map.put(BeginTurnAction.class, new ExecutableBeginTurnAction());
        map.put(CastCardAction.class, new ExecutableCastCardAction());
        map.put(ChangeCardAttackAction.class, new ExecutableChangeCardAttackAction());
        map.put(ChangeCardHealthAction.class, new ExecutableChangeCardHealthAction());
        map.put(ChangeCardMaxHealthAction.class, new ExecutableChangeCardMaxHealthAction());
        map.put(ChangePlayerElementAction.class, new ExecutableChangePlayerElementAction());
        map.put(ChangePlayerHealthAction.class, new ExecutableChangePlayerHealthAction());
        map.put(DiscardCardAction.class, new ExecutableDiscardCardAction());
        map.put(DrawCardAction.class, new ExecutableDrawCardAction());
        map.put(EndTurnAction.class, new ExecutableEndTurnAction());
        map.put(KillCardAction.class, new ExecutableKillCardAction());
        map.put(PlayCardAction.class, new ExecutablePlayCardAction());
        map.put(LoseAction.class, new ExecutableLoseAction());
        map.put(RemoveCardAttributeAction.class, new ExecutableRemoveCardAttributeAction());
        map.put(RemoveSkillAction.class, new ExecutableRemoveSkillAction());
        map.put(RevivePlayerAction.class, new ExecutableRevivePlayerAction());
        map.put(WinAction.class, new ExecutableWinAction());
        return map;
    }
}
