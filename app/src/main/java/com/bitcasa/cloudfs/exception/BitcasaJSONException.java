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

/**
 * The BitcasaJSONException class.
 */
public class BitcasaJSONException extends BitcasaException {

    /**
     * Initializes the BitcasaJSONException instance.
     */
    BitcasaJSONException() {
        super("Bitcasa JSON Exception");
    }

    /**
     * Initializes the BitcasaJSONException instance.
     *
     * @param message The error message.
     */
    BitcasaJSONException(String message) {
        super(message);
    }

    /**
     * Initializes the BitcasaJSONException instance.
     *
     * @param code    The error code.
     * @param message The error message.
     */
    BitcasaJSONException(int code, String message) {
        super(code, message);
    }

}
