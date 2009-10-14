package com.killard.board.jdo.board.property;

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
public class MetaCardPropertyDO extends PropertyDO {

    public MetaCardPropertyDO(DescriptableDO owner, String name, String data) {
        super(owner, name, data);
    }
}
