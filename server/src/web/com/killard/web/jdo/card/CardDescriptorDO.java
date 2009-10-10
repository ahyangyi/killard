package com.killard.web.jdo.card;

import com.killard.web.jdo.DescriptorDO;

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
public class CardDescriptorDO extends DescriptorDO {

    public CardDescriptorDO(CardDO card, String locale) {
        super(card, locale);
    }

    public CardDescriptorDO(CardDO card, Locale locale) {
        super(card, locale);
    }

}
