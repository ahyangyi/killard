package com.killard.jdo;

import com.killard.board.card.Action;
import com.killard.board.parser.Context;
import com.killard.board.parser.ExecutionException;
import com.killard.board.parser.GlobalContext;
import com.killard.jdo.board.BoardManagerDO;

import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public final class FunctionHelper {

    public static List<Action> handler(BoardManagerDO boardManager,
                                       Object owner, Action action, List<AttributeHandler> handlers) {
        if (handlers == null || handlers.isEmpty()) return null;
        getLog().fine("handle action: " + action + " on " + owner);
        Context context = new GlobalContext(owner, action);
        context.addVariable("board", boardManager);
        for (AttributeHandler attribute : handlers) {
            Class actionClass = attribute.getActionClass();
            boolean selfTargeted = attribute.isSelfTargeted();
            if (!actionClass.isAssignableFrom(action.getClass())) continue;
            if (selfTargeted && action.getTarget() != owner) continue;
            try {
                attribute.getFunction().execute(context);
            } catch (ExecutionException e) {
                getLog().log(Level.SEVERE, "Handle funciton for " + action.getClass().getSimpleName() + " | " + owner, e);
            } catch (Exception e) {
                getLog().log(Level.SEVERE, "Handle funciton for " + action.getClass().getSimpleName() + " | " + owner, e);
            }
        }
        if (context.getActions().isEmpty() && !context.isRejected()) return null;
        return context.getActions();
    }

    public static Logger getLog() {
        return Logger.getLogger(FunctionHelper.class.getName());
    }

}
