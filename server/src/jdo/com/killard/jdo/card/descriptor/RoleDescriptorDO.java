package com.killard.jdo.card.descriptor;

import com.killard.jdo.DescriptorDO;
import com.killard.jdo.card.RoleDO;

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
public class RoleDescriptorDO extends DescriptorDO {

    public RoleDescriptorDO(RoleDO owner, String locale) {
        super(owner, locale);
    }

    public RoleDescriptorDO(RoleDO owner, Locale locale) {
        super(owner, locale);
    }
}
