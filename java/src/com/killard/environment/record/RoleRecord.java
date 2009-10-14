package com.killard.environment.record;

import com.killard.card.Role;
import com.killard.environment.Record;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class RoleRecord implements Role, Record {

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
}
