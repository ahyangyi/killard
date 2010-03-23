package com.killard.board.jdo.board.descriptor;

import com.killard.board.jdo.DescriptorDO;
import com.killard.board.jdo.board.ElementDO;

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
public class ElementDescriptorDO extends DescriptorDO {

    public ElementDescriptorDO(ElementDO element, String locale, String name, String description) {
        super(element, locale, name, description);
    }

    public ElementDescriptorDO(ElementDO element, Locale locale, String name, String description) {
        super(element, locale, name, description);
    }

}
