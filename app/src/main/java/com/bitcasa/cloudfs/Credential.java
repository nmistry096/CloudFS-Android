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

package com.bitcasa.cloudfs;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * The Credential class provides accessibility to CloudFS Credential.
 */
public class Credential implements Parcelable, Cloneable{

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
    public Credential(final String endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * Initializes the credential instance using a Parcel.
     *
     * @param in The parcel object.
     */
    public Credential(Parcel in) {
        endpoint = in.readString();
        accessToken = in.readString();
        tokenType = in.readString();
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable's marshalled representation
     *
     * @return a bitmask indicating the set of special object types marshalled by the Parcelable
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param out   The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written. May be 0 or PARCELABLE_WRITE_RETURN_VALUE
     */
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(endpoint);
        out.writeString(accessToken);
        out.writeString(tokenType);
    }

    public static final Parcelable.Creator<Credential> CREATOR = new Parcelable.Creator<Credential>() {

        /**
         *Create a new instance of the Parcelable class, instantiating it from the given Parcel whose data had previously been written by Parcelable.writeToParcel()
         * @param source The Parcel to read the object's data from.
         * @return Returns a new instance of the Parcelable class.
         */
        @Override
        public Credential createFromParcel(Parcel source) {
            return new Credential(source);
        }

        /**
         *Create a new array of the Parcelable class
         * @param size Size of the array
         * @return Returns an array of the Parcelable class, with every entry initialized to null
         */
        @Override
        public Credential[] newArray(int size) {
            return new Credential[size];
        }
    };

    /**
     * Returns a clone of the Credential.
     *
     * @return A clone of Credential.
     */
    public Credential clone()
    {
        Credential credential = new Credential(this.endpoint);
        return  credential;
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
    public void setAccessToken(final String accessToken) {
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
    public void setTokenType(final String tokenType) {
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
    public void setEndPoint(final String endpoint) {
        this.endpoint = endpoint;
    }

}
