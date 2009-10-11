package com.killard.jdo.board.descriptor;

import com.killard.jdo.DescriptorDO;
import com.killard.jdo.DescriptableDO;
import com.killard.jdo.card.descriptor.CardDescriptorDO;
import com.killard.jdo.board.BoardCardDO;

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
public class BoardCardDescriptorDO extends DescriptorDO {

    public BoardCardDescriptorDO(BoardCardDO owner, CardDescriptorDO descriptor) {
        super(owner, descriptor);
    }
}
