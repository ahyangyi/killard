package com.killard.board.card.record;

import com.killard.board.card.Action;
import com.killard.board.card.action.AddCardAttributeAction;
import com.killard.board.card.action.BeginPlayerCallAction;
import com.killard.board.card.action.BeginTurnAction;
import com.killard.board.card.action.CastCardAction;
import com.killard.board.card.action.ChangeCardAttackAction;
import com.killard.board.card.action.ChangeCardHealthAction;
import com.killard.board.card.action.ChangeCardMaxHealthAction;
import com.killard.board.card.action.ChangeCardPropertyAction;
import com.killard.board.card.action.ChangePlayerHealthAction;
import com.killard.board.card.action.ChangePlayerPropertyAction;
import com.killard.board.card.action.ChangePlayerResourceAction;
import com.killard.board.card.action.DealCardAction;
import com.killard.board.card.action.DiscardCardAction;
import com.killard.board.card.action.DrawCardAction;
import com.killard.board.card.action.DropCardAction;
import com.killard.board.card.action.EndPlayerCallAction;
import com.killard.board.card.action.EndTurnAction;
import com.killard.board.card.action.EquipCardAction;
import com.killard.board.card.action.LoseAction;
import com.killard.board.card.action.RemoveCardAttributeAction;
import com.killard.board.card.action.RemoveSkillAction;
import com.killard.board.card.action.RevealRoleAction;
import com.killard.board.card.action.RevivePlayerAction;
import com.killard.board.card.action.TransferCardAction;
import com.killard.board.card.action.WinAction;
import com.killard.board.environment.ExecutableAction;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public enum ExecutableActions {

    instance;

    private Map<Class<? extends Action>, ExecutableAction> map;

    ExecutableActions() {
        map = new HashMap<Class<? extends Action>, ExecutableAction>();
        map.put(AddCardAttributeAction.class, new ExecutableAddCardAttributeAction());
        map.put(BeginPlayerCallAction.class, new ExecutableBeginPlayerCallAction());
        map.put(BeginTurnAction.class, new ExecutableBeginTurnAction());
        map.put(CastCardAction.class, new ExecutableCastCardAction());
        map.put(ChangeCardAttackAction.class, new ExecutableChangeCardAttackAction());
        map.put(ChangeCardHealthAction.class, new ExecutableChangeCardHealthAction());
        map.put(ChangeCardMaxHealthAction.class, new ExecutableChangeCardMaxHealthAction());
        map.put(ChangeCardPropertyAction.class, new ExecutableChangeCardPropertyAction());
        map.put(ChangePlayerResourceAction.class, new ExecutableChangePlayerResourceAction());
        map.put(ChangePlayerHealthAction.class, new ExecutableChangePlayerHealthAction());
        map.put(ChangePlayerPropertyAction.class, new ExecutableChangePlayerPropertyAction());
        map.put(DealCardAction.class, new ExecutableDealCardAction());
        map.put(DiscardCardAction.class, new ExecutableDiscardCardAction());
        map.put(DrawCardAction.class, new ExecutableDrawCardAction());
        map.put(DropCardAction.class, new ExecutableDropCardAction());
        map.put(EndPlayerCallAction.class, new ExecutableEndPlayerCallAction());
        map.put(EndTurnAction.class, new ExecutableEndTurnAction());
        map.put(EquipCardAction.class, new ExecutableEquipCardAction());
        map.put(LoseAction.class, new ExecutableLoseAction());
        map.put(RemoveCardAttributeAction.class, new ExecutableRemoveCardAttributeAction());
        map.put(RemoveSkillAction.class, new ExecutableRemoveSkillAction());
        map.put(RevealRoleAction.class, new ExecutableRevealRoleAction());
        map.put(RevivePlayerAction.class, new ExecutableRevivePlayerAction());
        map.put(TransferCardAction.class, new ExecutableTransferCardAction());
        map.put(WinAction.class, new ExecutableWinAction());
    }

    public <A extends Action> ExecutableAction getExecutableAction(A action) {
        return map.get(action.getClass());
    }

    public Set<Class<? extends Action>> getRegisterActions() {
        return map.keySet();
    }
}
