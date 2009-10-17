package com.killard.board.jdo.board.descriptor;

import com.killard.board.jdo.DescriptorDO;
import com.killard.board.jdo.board.MetaCardDO;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
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
public class MetaCardDescriptorDO extends DescriptorDO {

    public MetaCardDescriptorDO(MetaCardDO card, String locale, String name, String description) {
        super(card, locale, name, description);
    }

    public MetaCardDescriptorDO(MetaCardDO card, Locale locale, String name, String description) {
        super(card, locale, name, description);
    }

}
