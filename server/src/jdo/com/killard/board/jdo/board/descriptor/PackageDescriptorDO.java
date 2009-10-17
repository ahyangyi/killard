package com.killard.board.jdo.board.descriptor;

import com.killard.board.jdo.DescriptorDO;
import com.killard.board.jdo.board.PackageDO;

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
public class PackageDescriptorDO extends DescriptorDO {

    public PackageDescriptorDO(PackageDO pack, String locale, String name, String description) {
        super(pack, locale, name, description);
    }

    public PackageDescriptorDO(PackageDO pack, Locale locale, String name, String description) {
        super(pack, locale, name, description);
    }
}
