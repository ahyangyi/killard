package com.killard.board.jdo.game.player.property;

import com.killard.board.jdo.PropertyDO;
import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.game.property.GameRolePropertyDO;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.IdentityType;

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

    public RoleRecordPropertyDO(DescriptableDO owner, GameRolePropertyDO origin) {
        super(owner, origin);
    }

    public RoleRecordPropertyDO(DescriptableDO owner, String name, String data) {
        super(owner, name, data);
    }
}
