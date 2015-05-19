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
    public BitcasaException(final String message) {
        super(message);
    }

    /**
     * Initializes the BitcasaException instance.
     *
     * @param code    The error code.
     * @param message The error message.
     */
    public BitcasaException(final int code, final String message) {
        super("Error code = " + code + " Detail message = " + message);
    }

    /**
     * Initializes the BitcasaException instance.
     *
     * @param error The error object.
     */
    public BitcasaException(final BitcasaError error) {
        super("Error code = " + error.getCode() + " Detail message = " + error.getMessage());
    }

    /**
     * Initializes the BitcasaException instance.
     *
     * @param throwable The throwable object.
     */
    public BitcasaException(final Throwable throwable) {
        super(throwable);
    }

}
