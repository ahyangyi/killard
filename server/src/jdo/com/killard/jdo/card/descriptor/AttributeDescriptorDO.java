package com.killard.jdo.card.descriptor;

import com.killard.jdo.DescriptorDO;
import com.killard.jdo.card.AttributeDO;

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

    public AttributeDescriptorDO(AttributeDO attribute, String locale) {
        super(attribute, locale);
    }

    public AttributeDescriptorDO(AttributeDO attribute, Locale locale) {
        super(attribute, locale);
    }

}
