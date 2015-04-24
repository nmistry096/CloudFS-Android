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
 * The BitcasaException class.
 */
public class BitcasaException extends Exception {

    /**
     * Initializes the BitcasaException instance.
     */
    BitcasaException() {
        super("Bitcasa exception");
    }

    /**
     * Initializes the BitcasaException instance.
     *
     * @param message The error message.
     */
    public BitcasaException(String message) {
        super(message);
    }

    /**
     * Initializes the BitcasaException instance.
     *
     * @param code    The error code.
     * @param message The error message.
     */
    public BitcasaException(int code, String message) {
        super("Error code = " + code + " Detail message = " + message);
    }

    /**
     * Initializes the BitcasaException instance.
     *
     * @param error The error object.
     */
    public BitcasaException(BitcasaError error) {
        super("Error code = " + error.getCode() + " Detail message = " + error.getMessage());
    }

    /**
     * Initializes the BitcasaException instance.
     *
     * @param throwable The throwable object.
     */
    public BitcasaException(java.lang.Throwable throwable)
    {
        super(throwable);
    }

}
