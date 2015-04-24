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
 * The BitcasaClientException class.
 */
public class BitcasaClientException extends BitcasaException {

    /**
     * Initializes the BitcasaClientException instance.
     */
    BitcasaClientException() {
        super("BitcasaClient exception");
    }

    /**
     * Initializes the BitcasaClientException instance.
     *
     * @param message The error message.
     */
    public BitcasaClientException(String message) {
        super(message);
    }

    /**
     * Initializes the BitcasaClientException instance.
     *
     * @param code    The error code.
     * @param message The error message.
     */
    public BitcasaClientException(int code, String message) {
        super(code, message);
    }

}
