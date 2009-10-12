package com.killard.card.action;

import com.killard.card.Player;
import com.killard.card.Role;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class WinAction extends PlayerAction<Role> {

    public WinAction(Role source, Player target) {
        super(source, target);
    }
}
