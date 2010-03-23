package com.killard.board.jdo.board.logger;

import com.killard.board.card.action.ChangePlayerElementAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class ChangePlayerElementActionLogger extends PlayerActionLogger<ChangePlayerElementAction> {

    @Override
    public String log(ChangePlayerElementAction action) {
        return super.log(action) + ",\"element\":\"" + action.getElement().getName()
                + "\",\"value\":" + action.getValue();
    }

}
