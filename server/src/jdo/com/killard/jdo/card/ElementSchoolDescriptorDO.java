package com.killard.jdo.card;

import com.killard.jdo.DescriptorDO;

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
