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
    BitcasaAccountException(String message) {
        super(message);
    }

    /**
     * Initializes the BitcasaAccountException instance.
     *
     * @param code    The error code.
     * @param message The error message.
     */
    BitcasaAccountException(int code, String message) {
        super(code, message);
    }
}
