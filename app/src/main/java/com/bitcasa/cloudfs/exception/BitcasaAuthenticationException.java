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
    public BitcasaAuthenticationException(String message) {
        super(message);
    }

    /**
     * Initializes the BitcasaAuthenticationException instance.
     *
     * @param code    The error code.
     * @param message The error message.
     */
    public BitcasaAuthenticationException(int code, String message) {
        super(code, message);
    }

    /**
     * Initializes the BitcasaAuthenticationException instance.
     *
     * @param error The error object.
     */
    public BitcasaAuthenticationException(BitcasaError error) {
        super(error);
    }
}
