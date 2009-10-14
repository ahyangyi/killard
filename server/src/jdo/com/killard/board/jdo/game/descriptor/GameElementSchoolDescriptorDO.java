package com.killard.board.jdo.game.descriptor;

import com.killard.board.jdo.DescriptorDO;
import com.killard.board.jdo.board.descriptor.ElementSchoolDescriptorDO;
import com.killard.board.jdo.game.GameElementSchoolDO;

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
public class GameElementSchoolDescriptorDO extends DescriptorDO {

    public GameElementSchoolDescriptorDO(GameElementSchoolDO owner, ElementSchoolDescriptorDO descriptor) {
        super(owner, descriptor);
    }
}
