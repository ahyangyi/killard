package com.killard.board.jdo.game.player.property;

import com.killard.board.jdo.PropertyDO;
import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.game.property.GameAttributePropertyDO;

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
public class AttributeRecordPropertyDO extends PropertyDO {

    public AttributeRecordPropertyDO(DescriptableDO owner, String name, String data) {
        super(owner, name, data);
    }

    public AttributeRecordPropertyDO(DescriptableDO owner, GameAttributePropertyDO origin) {
        super(owner, origin);
    }
}
