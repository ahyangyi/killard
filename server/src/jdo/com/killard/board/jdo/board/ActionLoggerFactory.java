package com.killard.board.jdo.board;

import com.killard.board.card.Action;
import com.killard.board.card.action.BeginGameAction;
import com.killard.board.card.action.CardAction;
import com.killard.board.card.action.ChangeCardHealthAction;
import com.killard.board.card.action.ChangePlayerElementAction;
import com.killard.board.card.action.ChangePlayerHealthAction;
import com.killard.board.card.action.DealCardAction;
import com.killard.board.card.action.DiscardCardAction;
import com.killard.board.card.action.PlayerAction;
import com.killard.board.jdo.board.logger.BeginGameActionLogger;
import com.killard.board.jdo.board.logger.CardActionLogger;
import com.killard.board.jdo.board.logger.ChangeCardHealthActionLogger;
import com.killard.board.jdo.board.logger.ChangePlayerElementActionLogger;
import com.killard.board.jdo.board.logger.ChangePlayerHealthActionLogger;
import com.killard.board.jdo.board.logger.DealCardActionLogger;
import com.killard.board.jdo.board.logger.DiscardCardActionLogger;
import com.killard.board.jdo.board.logger.PlayerActionLogger;

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
public enum ActionLoggerFactory {

    factory;

    private Map<Class, ActionLogger> loggers;

    private ActionLoggerFactory() {
        loggers = new HashMap<Class, ActionLogger>();
        loggers.put(PlayerAction.class, new PlayerActionLogger());
        loggers.put(CardAction.class, new CardActionLogger());
        loggers.put(DealCardAction.class, new DealCardActionLogger());
        loggers.put(DiscardCardAction.class, new DiscardCardActionLogger());
        loggers.put(BeginGameAction.class, new BeginGameActionLogger());
        loggers.put(ChangeCardHealthAction.class, new ChangeCardHealthActionLogger());
        loggers.put(ChangePlayerHealthAction.class, new ChangePlayerHealthActionLogger());
        loggers.put(ChangePlayerElementAction.class, new ChangePlayerElementActionLogger());
    }

    public static ActionLogger getActionLogger(Action action) {
        if (factory.loggers.containsKey(action.getClass()))
            return factory.loggers.get(action.getClass());
        if (action instanceof PlayerAction)
            return factory.loggers.get(PlayerAction.class);
        if (action instanceof CardAction)
            return factory.loggers.get(CardAction.class);
        throw new IllegalArgumentException("This action is not supported.");
    }
}
