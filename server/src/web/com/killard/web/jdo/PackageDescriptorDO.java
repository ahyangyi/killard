package com.killard.web.jdo;

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

    public PackageDescriptorDO(PackageDO pack, String locale) {
        super(pack, locale);
    }

    public PackageDescriptorDO(PackageDO pack, Locale locale) {
        super(pack, locale);
    }
}
