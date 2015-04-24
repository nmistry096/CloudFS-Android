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
 * The BitcasaFileException class.
 */
public class BitcasaFileException extends BitcasaException {

    /**
     * Initializes the BitcasaFileException instance.
     */
    BitcasaFileException() {
        super("Bitcasa File Exception");
    }

    /**
     * Initializes the BitcasaFileException instance.
     *
     * @param message The error message.
     */
    public BitcasaFileException(String message) {
        super(message);
    }

    /**
     * Initializes the BitcasaFileException instance.
     *
     * @param code    The error code.
     * @param message The error message.
     */
    public BitcasaFileException(int code, String message) {
        super(code, message);
    }

}
