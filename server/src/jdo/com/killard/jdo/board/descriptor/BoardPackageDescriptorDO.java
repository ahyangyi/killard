package com.killard.jdo.board.descriptor;

import com.killard.jdo.DescriptorDO;
import com.killard.jdo.DescriptableDO;
import com.killard.jdo.card.descriptor.PackageDescriptorDO;
import com.killard.jdo.board.BoardPackageDO;

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
public class BoardPackageDescriptorDO extends DescriptorDO {

    public BoardPackageDescriptorDO(BoardPackageDO owner, PackageDescriptorDO descriptor) {
        super(owner, descriptor);
    }
}
