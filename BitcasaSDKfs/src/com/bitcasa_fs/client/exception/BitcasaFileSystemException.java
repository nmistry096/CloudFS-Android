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

package com.bitcasa_fs.client.exception;

import com.bitcasa_fs.client.BitcasaError;

public class BitcasaFileSystemException extends BitcasaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	BitcasaFileSystemException() {
		super("File System Exception");
	}
	
	public BitcasaFileSystemException(String message) {
		super(message);
	}
	
	public BitcasaFileSystemException(int code, String message) {
		super(code, message);
	}
	
	public BitcasaFileSystemException(BitcasaError error) {
		super(error);
	}
}
