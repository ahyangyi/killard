package com.killard.board.jdo.game.descriptor;

import com.killard.board.jdo.DescriptorDO;
import com.killard.board.jdo.board.descriptor.CardDescriptorDO;
import com.killard.board.jdo.game.BoardCardDO;

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
public class BoardCardDescriptorDO extends DescriptorDO {

    public BoardCardDescriptorDO(BoardCardDO owner, CardDescriptorDO descriptor) {
        super(owner, descriptor);
    }
}
