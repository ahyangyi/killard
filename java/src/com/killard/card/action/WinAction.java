package com.killard.card.action;

import com.killard.card.Player;
import com.killard.card.Role;
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
public class WinAction extends PlayerAction<Board> {

    public WinAction(Board source, Player target) {
        super(source, target);
    }
}