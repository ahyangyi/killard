package com.killard.jdo;

import com.killard.card.Action;
import com.killard.parser.Context;
import com.killard.parser.ExecutionException;
import com.killard.parser.GlobalContext;

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
public final class FunctionHelper {

    public static List<Action> handler(Object owner, Action action, List<AttributeHandler> handlers) {
        if (handlers == null || handlers.isEmpty()) return null;
        getLog().fine("handle action: " + action + " on " + owner);
        Context context = new GlobalContext(owner, action);
        for (AttributeHandler attribute : handlers) {
            Class actionClass = attribute.getActionClass();
            boolean selfTargeted = attribute.isSelfTargeted();
            if (!actionClass.isAssignableFrom(action.getClass())) continue;
            if (selfTargeted && action.getTarget() != owner) continue;
            try {
                attribute.getFunction().execute(context);
            } catch (ExecutionException e) {
                getLog().severe("Handle funciton for " + action.getClass().getSimpleName() + " | " + owner);
                e.printStackTrace();
            } catch (Exception e) {
                getLog().severe("Handle funciton for " + action.getClass().getSimpleName() + " | " + owner);
                e.printStackTrace();
            }
        }
        if (context.getActions().isEmpty() && !context.isRejected()) return null;
        return context.getActions();
    }

    public static Logger getLog() {
        return Logger.getLogger(FunctionHelper.class.getName());
    }

}
