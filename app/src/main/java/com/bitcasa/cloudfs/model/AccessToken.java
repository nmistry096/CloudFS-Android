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

import com.google.gson.annotations.SerializedName;

/**
 * Model representing access token.
 */
public class AccessToken {

    /**
     * The access token.
     */
    @SerializedName("access_token")
    private String accessToken;

    /**
     * The token type.
     */
    @SerializedName("token_type")
    private String tokenType;

    /**
     * The transmission key.
     */
    @SerializedName("transmission_key")
    private String transmissionKey;

    /**
     * @return The accessToken
     */
    public final String getAccessToken() {
        return this.accessToken;
    }

    /**
     * Sets the access token.
     *
     * @param accessToken The access token
     */
    public final void setAccessToken(final String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Gets the access token type.
     *
     * @return The access token type.
     */
    public final String getTokenType() {
        return this.tokenType;
    }

    /**
     * Sets the access token type.
     *
     * @param tokenType The access token type.
     */
    public final void setTokenType(final String tokenType) {
        this.tokenType = tokenType;
    }

    /**
     * Gets the transmission key.
     *
     * @return The transmission key.
     */
    public final String getTransmissionKey() {
        return this.transmissionKey;
    }

    /**
     * Sets the transmission key.
     *
     * @param transmissionKey The transmission key.
     */
    public final void setTransmissionKey(final String transmissionKey) {
        this.transmissionKey = transmissionKey;
    }
}
