package com.killard.board.jdo.board.descriptor;

import com.killard.board.jdo.DescriptorDO;
import com.killard.board.jdo.board.BoardSkillDO;
import com.killard.board.jdo.card.descriptor.SkillDescriptorDO;

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
public class BoardSkillDescriptorDO extends DescriptorDO {

    public BoardSkillDescriptorDO(BoardSkillDO owner, SkillDescriptorDO descriptor) {
        super(owner, descriptor);
    }
}
