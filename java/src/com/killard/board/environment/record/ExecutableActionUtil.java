package com.killard.board.environment.record;

import com.killard.board.card.Action;
import com.killard.board.card.action.AddCardAttributeAction;
import com.killard.board.card.action.CastCardAction;
import com.killard.board.card.action.ChangeCardAttackAction;
import com.killard.board.card.action.ChangeCardHealthAction;
import com.killard.board.card.action.ChangeCardMaxHealthAction;
import com.killard.board.card.action.ChangePlayerElementAction;
import com.killard.board.card.action.ChangePlayerHealthAction;
import com.killard.board.card.action.DropCardAction;
import com.killard.board.card.action.EquipCardAction;
import com.killard.board.card.action.EndTurnAction;
import com.killard.board.card.action.RemoveCardAttributeAction;
import com.killard.board.card.action.RemoveSkillAction;
import com.killard.board.card.action.BeginTurnAction;
import com.killard.board.card.action.DrawCardAction;
import com.killard.board.card.action.DiscardCardAction;
import com.killard.board.card.action.LoseAction;
import com.killard.board.card.action.WinAction;
import com.killard.board.card.action.RevivePlayerAction;
import com.killard.board.card.action.RevealRoleAction;
import com.killard.board.card.action.BeginPlayerCallAction;
import com.killard.board.card.action.EndPlayerCallAction;
import com.killard.board.card.action.DealCardAction;
import com.killard.board.environment.ExecutableAction;

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
        map.put(BeginPlayerCallAction.class, new ExecutableBeginPlayerCallAction());
        map.put(BeginTurnAction.class, new ExecutableBeginTurnAction());
        map.put(CastCardAction.class, new ExecutableCastCardAction());
        map.put(ChangeCardAttackAction.class, new ExecutableChangeCardAttackAction());
        map.put(ChangeCardHealthAction.class, new ExecutableChangeCardHealthAction());
        map.put(ChangeCardMaxHealthAction.class, new ExecutableChangeCardMaxHealthAction());
        map.put(ChangePlayerElementAction.class, new ExecutableChangePlayerElementAction());
        map.put(ChangePlayerHealthAction.class, new ExecutableChangePlayerHealthAction());
        map.put(DealCardAction.class, new ExecutableDealCardAction());
        map.put(DiscardCardAction.class, new ExecutableDiscardCardAction());
        map.put(DrawCardAction.class, new ExecutableDrawCardAction());
        map.put(EndPlayerCallAction.class, new ExecutableEndPlayerCallAction());
        map.put(EndTurnAction.class, new ExecutableEndTurnAction());
        map.put(EquipCardAction.class, new ExecutableEquipCardAction());
        map.put(DropCardAction.class, new ExecutableDropCardAction());
        map.put(LoseAction.class, new ExecutableLoseAction());
        map.put(RemoveCardAttributeAction.class, new ExecutableRemoveCardAttributeAction());
        map.put(RemoveSkillAction.class, new ExecutableRemoveSkillAction());
        map.put(RevealRoleAction.class, new ExecutableRevealRoleAction());
        map.put(RevivePlayerAction.class, new ExecutableRevivePlayerAction());
        map.put(WinAction.class, new ExecutableWinAction());
        return map;
    }
}
