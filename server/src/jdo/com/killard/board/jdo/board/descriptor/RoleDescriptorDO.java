package com.killard.board.jdo.board.descriptor;

import com.killard.board.jdo.DescriptorDO;
import com.killard.board.jdo.board.RoleDO;

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
public class RoleDescriptorDO extends DescriptorDO {

    public RoleDescriptorDO(RoleDO owner, String locale, String name, String description) {
        super(owner, locale, name, description);
    }

    public RoleDescriptorDO(RoleDO owner, Locale locale, String name, String description) {
        super(owner, locale, name, description);
    }
}
