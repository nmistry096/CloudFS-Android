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
 * The BitcasaRequestErrorException class.
 */
public class BitcasaRequestErrorException extends BitcasaException {

    /**
     * Initializes the BitcasaRequestErrorException instance.
     */
    BitcasaRequestErrorException() {
        super("Bitcasa HTTP Request Error Exception");
    }

    /**
     * Initializes the BitcasaRequestErrorException instance.
     *
     * @param message The error message.
     */
    public BitcasaRequestErrorException(final String message) {
        super(message);
    }

    /**
     * Initializes the BitcasaRequestErrorException instance.
     *
     * @param code    The error code.
     * @param message The error message.
     */
    public BitcasaRequestErrorException(final int code, final String message) {
        super(code, message);
    }

    /**
     * Initializes the BitcasaRequestErrorException instance.
     *
     * @param error The error object.
     */
    public BitcasaRequestErrorException(final BitcasaError error) {
        super(error);
    }

}
