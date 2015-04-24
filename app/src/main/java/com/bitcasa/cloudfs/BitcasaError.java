/**
 * Bitcasa Client Android SDK
 * Copyright (C) 2015 Bitcasa, Inc.
 * 215 Castro Street, 2nd Floor
 * Mountain View, CA 94041
 *
 * This file contains an SDK in Java for accessing the Bitcasa infinite drive in Android platform.
 *
 * For support, please send email to support@bitcasa.com.
 */

package com.bitcasa.cloudfs;

/**
 * The BitcasaError class provides custom errors.
 */
public class BitcasaError {

    /**
     * The error code.
     */
    private int code = -1;

    /**
     * The error message.
     */
    private String message;

    /**
     * The error data.
     */
    private String data;

    /**
     * BitcasaError default constructor.
     */
    public BitcasaError() {
    }

    /**
     * Initializes an instance of BitcasaError with error data.
     *
     * @param code    The error code.
     * @param message The error message.
     * @param data    The data.
     */
    public BitcasaError(int code, String message, String data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * Initializes an instance BitcasaError.
     *
     * @param code    The error code.
     * @param message The error message.
     */
    public BitcasaError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Gets the error code.
     *
     * @return The error code.
     */
    public int getCode() {
        return code;
    }

    /**
     * Sets the error code.
     *
     * @param code The error code.
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * Gets the error message.
     *
     * @return The error message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the error message.
     *
     * @param Message The error message.
     */
    public void setMessage(String Message) {
        this.message = Message;
    }

    /**
     * Gets the data value.
     *
     * @return The data value.
     */
    public String getData() {
        return data;
    }

    /**
     * Sets the data value.
     *
     * @param data The data parameter.
     */
    public void setData(String data) {
        this.data = data;
    }
}
