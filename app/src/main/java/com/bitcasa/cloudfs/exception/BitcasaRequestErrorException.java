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
    public BitcasaRequestErrorException(String message) {
        super(message);
    }

    /**
     * Initializes the BitcasaRequestErrorException instance.
     *
     * @param code    The error code.
     * @param message The error message.
     */
    public BitcasaRequestErrorException(int code, String message) {
        super(code, message);
    }

    /**
     * Initializes the BitcasaRequestErrorException instance.
     *
     * @param error The error object.
     */
    public BitcasaRequestErrorException(BitcasaError error) {
        super(error);
    }

}
