package com.killard.board.jdo.card.descriptor;

import com.killard.board.jdo.DescriptorDO;
import com.killard.board.jdo.card.SkillDO;

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
public class SkillDescriptorDO extends DescriptorDO {

    public SkillDescriptorDO(SkillDO skill, String locale) {
        super(skill, locale);
    }

    public SkillDescriptorDO(SkillDO skill, Locale locale) {
        super(skill, locale);
    }

}
