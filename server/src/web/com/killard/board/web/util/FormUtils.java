package com.killard.board.web.util;

import com.killard.board.jdo.AttributeHandler;
import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.DescriptorDO;
import com.killard.board.parser.ScriptEngine;
import org.antlr.runtime.RecognitionException;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public final class FormUtils {

    private static final ScriptEngine engine = new ScriptEngine();

    public static void updateDescriptors(DescriptableDO descriptable,
                                         String[] locales, String[] names, String[] descriptions) {
        for (int i = 0; i < locales.length; i++) {
            if (names.length <= i || descriptions.length <= i) break;
            DescriptorDO descriptor = descriptable.getDescriptor(locales[i]);
            if (descriptor != null && descriptor.getLocale().equals(locales[i])) {
                descriptor.setName(names[i]);
                descriptor.setDescription(descriptions[i]);
            } else {
                descriptable.newDescriptor(locales[i], names[i], descriptions[i]);
            }
        }
    }

    public static List<AttributeHandler> buildHandlers(String[] actionClass,
                                                       boolean[] selfTargeted,
                                                       String[] function)
            throws RecognitionException, ClassNotFoundException {
        List<AttributeHandler> handlers = new ArrayList<AttributeHandler>();
        for (int i = 0; i < actionClass.length; i++) {
            if (selfTargeted.length <= i || function.length <= i) break;
            handlers.add(new AttributeHandler(
                    Class.forName("com.killard.board.card.action." + actionClass[i]),
                    selfTargeted[i],
                    engine.parseFunction("function(){" + function[i] + "}")
            ));
        }
        return handlers;
    }
}
