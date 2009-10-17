package com.killard.board.jdo.board.descriptor;

import com.killard.board.jdo.DescriptorDO;
import com.killard.board.jdo.board.AttributeDO;

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
public class AttributeDescriptorDO extends DescriptorDO {

    public AttributeDescriptorDO(AttributeDO attribute, String locale, String name, String description) {
        super(attribute, locale, name, description);
    }

    public AttributeDescriptorDO(AttributeDO attribute, Locale locale, String name, String description) {
        super(attribute, locale, name, description);
    }

}
