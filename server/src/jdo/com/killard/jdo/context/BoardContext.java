package com.killard.jdo.context;

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
public class BoardContext {

    private static final ThreadLocal<Locale> locale = new ThreadLocal<Locale>();

    public BoardContext() {
    }

    public static Locale getLocale() {
        return locale.get();
    }

    public static void setLocale(Locale locale) {
        BoardContext.locale.set(locale);
    }
}
