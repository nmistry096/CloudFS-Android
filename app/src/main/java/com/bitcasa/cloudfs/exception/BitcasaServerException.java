/**
 * Bitcasa Client Android SDK
 * Copyright (C) 2013 Bitcasa, Inc.
 * 215 Castro Street, 2nd Floor
 * Mountain View, CA 94041
 *
 * This file contains an SDK in Java for accessing the Bitcasa infinite drive in Android platform.
 *
 * For support, please send email to support@bitcasa.com.
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
    public BitcasaServerException(String message) {
        super(message);
    }

    /**
     * Initializes the BitcasaServerException instance.
     *
     * @param code    error code.
     * @param message The error message.
     */
    public BitcasaServerException(int code, String message) {
        super(code, message);
    }

    /**
     * Initializes the BitcasaServerException instance.
     *
     * @param error The error object.
     */
    public BitcasaServerException(BitcasaError error) {
        super(error);
    }
}
