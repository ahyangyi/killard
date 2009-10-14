package com.killard.board.jdo.board.descriptor;

import com.killard.board.jdo.DescriptorDO;
import com.killard.board.jdo.card.descriptor.AttributeDescriptorDO;
import com.killard.board.jdo.board.BoardAttributeDO;

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
public class BoardAttributeDescriptorDO extends DescriptorDO {

    public BoardAttributeDescriptorDO(BoardAttributeDO owner, AttributeDescriptorDO descriptor) {
        super(owner, descriptor);
    }
}
