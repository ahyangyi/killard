package com.killard.board.card.action;

import com.killard.board.card.Role;
import com.killard.board.card.Board;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class RevealRoleAction extends RoleAction<Board> {

    public RevealRoleAction(Board source, Role target) {
        super(source, target);
    }
}
