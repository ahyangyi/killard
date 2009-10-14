package com.killard.board.jdo.game.descriptor;

import com.killard.board.jdo.DescriptorDO;
import com.killard.board.jdo.board.descriptor.PackageDescriptorDO;
import com.killard.board.jdo.game.GamePackageDO;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.IdentityType;

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
public class GamePackageDescriptorDO extends DescriptorDO {

    public GamePackageDescriptorDO(GamePackageDO owner, PackageDescriptorDO descriptor) {
        super(owner, descriptor);
    }
}
