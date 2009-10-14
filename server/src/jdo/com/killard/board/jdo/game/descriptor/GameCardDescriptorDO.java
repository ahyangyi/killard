package com.killard.board.jdo.game.descriptor;

import com.killard.board.jdo.DescriptorDO;
import com.killard.board.jdo.board.descriptor.CardDescriptorDO;
import com.killard.board.jdo.game.GameMetaCardDO;

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
public class GameCardDescriptorDO extends DescriptorDO {

    public GameCardDescriptorDO(GameMetaCardDO owner, CardDescriptorDO descriptor) {
        super(owner, descriptor);
    }
}
