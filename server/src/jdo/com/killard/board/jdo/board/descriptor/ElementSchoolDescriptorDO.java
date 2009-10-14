package com.killard.board.jdo.board.descriptor;

import com.killard.board.jdo.DescriptorDO;
import com.killard.board.jdo.board.ElementSchoolDO;

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
public class ElementSchoolDescriptorDO extends DescriptorDO {

    public ElementSchoolDescriptorDO(ElementSchoolDO elementSchool, String locale) {
        super(elementSchool, locale);
    }

    public ElementSchoolDescriptorDO(ElementSchoolDO elementSchool, Locale locale) {
        super(elementSchool, locale);
    }

}