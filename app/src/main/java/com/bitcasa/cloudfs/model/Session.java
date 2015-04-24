package com.bitcasa.cloudfs.model;

/**
 * Model class for a session.
 */
public class Session {

    /**
     * Locale of the session.
     */
    private final String locale;

    /**
     * Initializes a new instance of a session.
     *
     * @param locale Locale of the session.
     */
    public Session(final String locale) {
        this.locale = locale;
    }

    /**
     * Gets the locale of the session.
     *
     * @return Locale of the session.
     */
    public final String getLocale() {
        return this.locale;
    }
}
