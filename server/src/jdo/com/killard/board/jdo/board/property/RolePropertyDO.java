package com.killard.board.jdo.board.property;

import com.killard.board.jdo.PropertyDO;
import com.killard.board.jdo.board.RoleDO;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class RolePropertyDO extends PropertyDO {

    public RolePropertyDO(RoleDO owner, String name, String data) {
        super(owner, name, data);
    }

    public RolePropertyDO(RoleDO owner, RolePropertyDO origin) {
        super(owner, origin.getName(), origin.getData());
    }
}
