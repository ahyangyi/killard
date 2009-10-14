package com.killard.board.jdo.board.descriptor;

import com.killard.board.jdo.DescriptorDO;
import com.killard.board.jdo.card.descriptor.ElementSchoolDescriptorDO;
import com.killard.board.jdo.board.BoardElementSchoolDO;

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
public class BoardElementSchoolDescriptorDO extends DescriptorDO {

    public BoardElementSchoolDescriptorDO(BoardElementSchoolDO owner, ElementSchoolDescriptorDO descriptor) {
        super(owner, descriptor);
    }
}
