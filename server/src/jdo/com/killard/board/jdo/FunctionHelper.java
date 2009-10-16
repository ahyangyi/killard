package com.killard.board.jdo;

import com.killard.board.card.Action;
import com.killard.board.jdo.board.BoardDO;
import com.killard.board.parser.Context;
import com.killard.board.parser.ExecutionException;
import com.killard.board.parser.GlobalContext;

import java.util.List;
import java.util.logging.Level;
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
public final class FunctionHelper {

    public static List<Action> handler(BoardDO board,
                                       Object owner, Action action, List<AttributeHandler> handlers) {
        if (handlers == null || handlers.isEmpty()) return null;
        getLog().fine("handle action: " + action + " on " + owner);
        Context context = new GlobalContext(owner, action);
        context.addVariable("board", board);
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
