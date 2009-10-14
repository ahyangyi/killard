package com.killard.board.jdo.game.descriptor;

import com.killard.board.jdo.DescriptorDO;
import com.killard.board.jdo.game.GameSkillDO;
import com.killard.board.jdo.board.descriptor.SkillDescriptorDO;

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
public class GameSkillDescriptorDO extends DescriptorDO {

    public GameSkillDescriptorDO(GameSkillDO owner, SkillDescriptorDO descriptor) {
        super(owner, descriptor);
    }
}
