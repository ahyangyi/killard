package com.killard.board.jdo.board.logger;

import com.killard.board.card.Card;
import com.killard.board.card.Player;
import com.killard.board.card.action.CardAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class CardActionLogger<A extends CardAction> extends BasicActionLogger<A> {

    @Override
    public String log(A action) {
        if (action.getSource() instanceof Player)
            return "\"source\":" + logPlayer((Player) action.getSource())
                    + ",\"target\":" + logCard((Card) action.getTarget());
        if (action.getSource() instanceof Card)
            return "\"source\":" + logCard((Card) action.getSource())
                    + ",\"target\":" + logCard((Card) action.getTarget());
        return "\"target\":" + logCard((Card) action.getTarget());
    }
}
