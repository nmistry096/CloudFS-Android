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

/**
 * The BitcasaAccountException class.
 */
public class BitcasaAccountException extends BitcasaException {

    /**
     * Initializes the BitcasaAccountException instance.
     */
    BitcasaAccountException() {
        super("Bitcasa Account Exception");
    }

    /**
     * Initializes the BitcasaAccountException instance.
     *
     * @param message The error message.
     */
    BitcasaAccountException(final String message) {
        super(message);
    }

    /**
     * Initializes the BitcasaAccountException instance.
     *
     * @param code    The error code.
     * @param message The error message.
     */
    BitcasaAccountException(final int code, final String message) {
        super(code, message);
    }
}
