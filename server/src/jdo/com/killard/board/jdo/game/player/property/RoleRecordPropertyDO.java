package com.killard.board.jdo.game.player.property;

import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.PropertyDO;

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
public class RoleRecordPropertyDO extends PropertyDO {

    public RoleRecordPropertyDO(DescriptableDO owner, String name, String data) {
        super(owner, name, data);
    }

    public RoleRecordPropertyDO(DescriptableDO owner, PropertyDO origin) {
        super(owner, origin);
    }
}
