package com.killard.board.web.util;

import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.DescriptorDO;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public final class DescriptorUtils {
    public static void updateDescriptors(DescriptableDO descriptable,
                                         String[] locales, String[] names, String[] descriptions) {
        for (int i = 0; i < locales.length; i++) {
            if (names.length <= i || descriptions.length <= i) break;
            DescriptorDO descriptor = descriptable.getDescriptor(locales[i]);
            if (descriptor == null) {
                descriptable.newDescriptor(locales[i], names[i], descriptions[i]);
            } else {
                descriptor.setName(names[i]);
                descriptor.setDescription(descriptions[i]);
            }
        }
    }
}
