package com.killard.board.jdo.board.game.property;

import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.PropertyDO;
import com.killard.board.jdo.board.game.RoleRecordDO;

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

    public RoleRecordPropertyDO(RoleRecordDO owner, String name, String data) {
        super(null, name, data);
    }

    public RoleRecordPropertyDO(RoleRecordDO owner, PropertyDO origin) {
        super(null, origin);
    }
}
