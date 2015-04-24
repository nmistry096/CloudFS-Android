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
 * The Credential class provides accessibility to CloudFS Credential.
 */
public class Credential {

    /**
     * The CloudFS API end point.
     */
    private String endpoint;

    /**
     * The CloudFS API access token.
     */
    private String accessToken;

    /**
     * The CloudFS API token type.
     */
    private String tokenType;

    /**
     * Initializes an instance of the Credential.
     *
     * @param endpoint The CloudFS API endpoint.
     */
    public Credential(String endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * Gets the CloudFS API access token.
     *
     * @return The CloudFS API access token.
     */
    public String getAccessToken() {
        return this.accessToken;
    }

    /**
     * Sets the CloudFS API access token.
     *
     * @param accessToken The CloudFS API access token.
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Gets the CloudFS API token type.
     *
     * @return The CloudFS API token type.
     */
    public String getTokenType() {
        return this.tokenType;
    }

    /**
     * Sets the CloudFS API token type.
     *
     * @param tokenType The CloudFS API token type.
     */
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    /**
     * Gets the CloudFS API endpoint.
     *
     * @return The CloudFS API endpoint.
     */
    public String getEndPoint() {
        return this.endpoint;
    }

    /**
     * Sets the CloudFS API endpoint.
     *
     * @param endpoint The CloudFS API token type.
     */
    public void setEndPoint(String endpoint) {
        this.endpoint = endpoint;
    }

}
