package com.killard.board.jdo.game.property;

import com.killard.board.jdo.PropertyDO;
import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.board.property.ElementSchoolPropertyDO;

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
public class GameElementSchoolPropertyDO extends PropertyDO {

    public GameElementSchoolPropertyDO(DescriptableDO owner, ElementSchoolPropertyDO origin) {
        super(owner, origin);
    }
}
