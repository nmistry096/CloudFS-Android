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
 * The BitcasaServerException class.
 */
public class BitcasaServerException extends BitcasaException {

    /**
     * Initializes the BitcasaServerException instance.
     */

    BitcasaServerException() {
        super("Bitcasa Server Exception");
    }

    /**
     * Initializes the BitcasaServerException instance.
     *
     * @param message The error message.
     */
    public BitcasaServerException(final String message) {
        super(message);
    }

    /**
     * Initializes the BitcasaServerException instance.
     *
     * @param code    error code.
     * @param message The error message.
     */
    public BitcasaServerException(final int code, final String message) {
        super(code, message);
    }

    /**
     * Initializes the BitcasaServerException instance.
     *
     * @param error The error object.
     */
    public BitcasaServerException(final BitcasaError error) {
        super(error);
    }
}
