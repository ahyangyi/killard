package com.killard.board.card.action;

import com.killard.board.card.Role;
import com.killard.board.card.Board;
import com.killard.board.card.Player;
import com.killard.board.card.Record;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class RevealRoleAction extends BasicAction<Record, Player> {

    public RevealRoleAction(Record source, Player target) {
        super(source, target);
    }
}
