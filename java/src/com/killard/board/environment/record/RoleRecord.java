package com.killard.board.environment.record;

import com.killard.board.card.Role;
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
public class RoleRecord implements Role<RoleRecord> {

    private boolean visible;

    public RoleRecord(Role role) {
        this.visible = role.isVisible();
    }

    protected void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public int compareTo(RoleRecord roleRecord) {
        return 0;
    }
}
