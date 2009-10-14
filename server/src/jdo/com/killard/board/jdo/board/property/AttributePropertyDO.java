package com.killard.board.jdo.board.property;

import com.killard.board.jdo.PropertyDO;
import com.killard.board.jdo.DescriptableDO;

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
public class AttributePropertyDO extends PropertyDO {

    public AttributePropertyDO(DescriptableDO owner, String name, String data) {
        super(owner, name, data);
    }
}
