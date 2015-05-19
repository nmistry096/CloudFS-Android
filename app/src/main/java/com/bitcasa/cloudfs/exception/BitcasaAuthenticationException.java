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

package com.bitcasa.cloudfs.exception;

import com.bitcasa.cloudfs.BitcasaError;

/**
 * The BitcasaAuthenticationException class.
 */
public class BitcasaAuthenticationException extends BitcasaException {

    /**
     * Initializes the BitcasaAuthenticationException instance.
     */
    public BitcasaAuthenticationException() {
        super("Bitcasa Authentication Exception");
    }

    /**
     * Initializes the BitcasaAuthenticationException instance.
     *
     * @param message The error message.
     */
    public BitcasaAuthenticationException(final String message) {
        super(message);
    }

    /**
     * Initializes the BitcasaAuthenticationException instance.
     *
     * @param code    The error code.
     * @param message The error message.
     */
    public BitcasaAuthenticationException(final int code, final String message) {
        super(code, message);
    }

    /**
     * Initializes the BitcasaAuthenticationException instance.
     *
     * @param error The error object.
     */
    public BitcasaAuthenticationException(final BitcasaError error) {
        super(error);
    }
}
