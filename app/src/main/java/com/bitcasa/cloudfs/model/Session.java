/**
 * Bitcasa Client Android SDK
 * Copyright (C) 2015 Bitcasa, Inc.
 * 1200 Park Place,
 * Suite 350 San Mateo, CA 94403.
 *
 * This file contains an SDK in Java for accessing the Bitcasa infinite drive in Android platform.
 *
 * For support, please send email to sdks@bitcasa.com.
 */
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
