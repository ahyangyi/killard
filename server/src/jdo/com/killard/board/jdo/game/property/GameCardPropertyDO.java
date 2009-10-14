package com.killard.board.jdo.game.property;

import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.PropertyDO;
import com.killard.board.jdo.game.GameCardDO;
import com.killard.board.jdo.board.property.MetaCardPropertyDO;

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
public class GameCardPropertyDO extends PropertyDO {

    public GameCardPropertyDO(GameCardDO owner, MetaCardPropertyDO origin) {
        super(owner, origin);
    }
}
