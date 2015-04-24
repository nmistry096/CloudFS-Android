package com.bitcasa.cloudfs.model;

import com.google.gson.JsonElement;

/**
 * Model class for API responses.
 */
public class BitcasaResponse {

    /**
     * Response error.
     */
    private final ResponseError error;

    /**
     * Response result.
     */
    private final JsonElement result;

    /**
     * Initializes a new instance of a Bitcasa response.
     *
     * @param error  Response error.
     * @param result Response result.
     */
    public BitcasaResponse(final ResponseError error, final JsonElement result) {
        this.error = error;
        this.result = result;
    }

    /**
     * Gets the response error.
     *
     * @return Response error.
     */
    public final ResponseError getError() {
        return this.error;
    }

    /**
     * Gets the response result.
     *
     * @return Response result.
     */
    public final JsonElement getResult() {
        return this.result;
    }
}
