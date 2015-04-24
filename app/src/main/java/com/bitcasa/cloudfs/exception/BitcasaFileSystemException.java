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
    public BitcasaFileSystemException(String message) {
        super(message);
    }

    /**
     * Initializes the BitcasaFileSystemException instance.
     *
     * @param code    The error code.
     * @param message The error message.
     */
    public BitcasaFileSystemException(int code, String message) {
        super(code, message);
    }

    /**
     * Initializes the BitcasaFileSystemException instance.
     *
     * @param error The error object.
     */
    public BitcasaFileSystemException(BitcasaError error) {
        super(error);
    }
}
