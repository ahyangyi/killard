package com.killard.card.action;

import com.killard.card.Role;
import com.killard.card.Player;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class LoseAction extends PlayerAction<Role> {

    public LoseAction(Role source, Player target) {
        super(source, target);
    }
}
