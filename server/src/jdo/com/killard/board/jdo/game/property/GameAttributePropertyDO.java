package com.killard.board.jdo.game.property;

import com.killard.board.jdo.PropertyDO;
import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.board.property.AttributePropertyDO;
import com.killard.board.jdo.game.GameAttributeDO;

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
public class GameAttributePropertyDO extends PropertyDO {

    public GameAttributePropertyDO(GameAttributeDO owner, AttributePropertyDO origin) {
        super(owner, origin);
    }
}
