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
package com.bitcasa.cloudfs.model;

import com.google.gson.JsonElement;

/**
 * Model class for errors in responses.
 */
public class ResponseError {

    /**
     * Error code.
     */
    private final Integer code;

    /**
     * Error message.
     */
    private final String message;

    /**
     * Extra error data.
     */
    private final JsonElement data;

    /**
     * Initializes a new instance of a response error.
     *
     * @param code    Error code.
     * @param message Error message.
     * @param data    Extra error data.
     */
    public ResponseError(final Integer code, final String message, final JsonElement data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * Gets the error code.
     *
     * @return Error code.
     */
    public final Integer getCode() {
        return this.code;
    }

    /**
     * Gets the error message.
     *
     * @return Error message.
     */
    public final String getMessage() {
        return this.message;
    }

    /**
     * Gets extra error data.
     *
     * @return Extra error data.
     */
    public final JsonElement getData() {
        return this.data;
    }
}
