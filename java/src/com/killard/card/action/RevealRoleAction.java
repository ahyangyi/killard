package com.killard.card.action;

import com.killard.card.Role;
import com.killard.card.CardInstance;
import com.killard.card.Board;

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
