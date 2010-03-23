package com.killard.board.jdo.board.logger;

import com.killard.board.card.action.ChangePlayerResourceAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class ChangePlayerResourceActionLogger extends PlayerActionLogger<ChangePlayerResourceAction> {

    @Override
    public String log(ChangePlayerResourceAction action) {
        return super.log(action) + ",\"element\":\"" + action.getElement().getName()
                + "\",\"value\":" + action.getValue();
    }

}
