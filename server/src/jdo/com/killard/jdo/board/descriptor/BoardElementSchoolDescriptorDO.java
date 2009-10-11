package com.killard.jdo.board.descriptor;

import com.killard.jdo.DescriptorDO;
import com.killard.jdo.DescriptableDO;
import com.killard.jdo.card.descriptor.ElementSchoolDescriptorDO;
import com.killard.jdo.board.BoardElementSchoolDO;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.IdentityType;
import java.util.Locale;

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
