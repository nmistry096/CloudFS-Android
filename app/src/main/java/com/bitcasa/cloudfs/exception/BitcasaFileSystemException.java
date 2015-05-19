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
 * The BitcasaFileSystemException class.
 */
public class BitcasaFileSystemException extends BitcasaException {

    /**
     * Initializes the BitcasaFileSystemException instance.
     */
    BitcasaFileSystemException() {
        super("File System Exception");
    }

    /**
     * Initializes the BitcasaFileSystemException instance.
     *
     * @param message The error message.
     */
    public BitcasaFileSystemException(final String message) {
        super(message);
    }

    /**
     * Initializes the BitcasaFileSystemException instance.
     *
     * @param code    The error code.
     * @param message The error message.
     */
    public BitcasaFileSystemException(final int code, final String message) {
        super(code, message);
    }

    /**
     * Initializes the BitcasaFileSystemException instance.
     *
     * @param error The error object.
     */
    public BitcasaFileSystemException(final BitcasaError error) {
        super(error);
    }
}
