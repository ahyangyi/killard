package com.killard.board.environment.record;

import com.killard.board.card.action.EquipCardAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class ExecutableEquipCardAction extends ExecutableCardAction<EquipCardAction> {

    public void execute(AbstractCardRecord record, EquipCardAction action) {
        ((AbstractPlayerRecord) action.getSource()).addEquippedCard(record, action);
    }
}
