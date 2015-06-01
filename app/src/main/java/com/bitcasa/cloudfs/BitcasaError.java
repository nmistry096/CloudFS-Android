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
 * The BitcasaError class provides custom errors.
 */
public class BitcasaError implements Parcelable {

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
    public BitcasaError(final int code, final String message, final String data) {
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
    public BitcasaError(final int code, final String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Initializes the BitcasaError instance.
     *
     * @param in The error parcel object.
     */
    public BitcasaError(Parcel in) {
        code = in.readInt();
        message = in.readString();
        data = in.readString();
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
        out.writeInt(code);
        out.writeString(message);
        out.writeString(data);
    }

    public static final Parcelable.Creator<BitcasaError> CREATOR = new Parcelable.Creator<BitcasaError>() {

        /**
         *Create a new instance of the Parcelable class, instantiating it from the given Parcel whose data had previously been written by Parcelable.writeToParcel()
         * @param source The Parcel to read the object's data from.
         * @return Returns a new instance of the Parcelable class.
         */
        @Override
        public BitcasaError createFromParcel(Parcel source) {
            return new BitcasaError(source);
        }

        /**
         *Create a new array of the Parcelable class
         * @param size Size of the array
         * @return Returns an array of the Parcelable class, with every entry initialized to null
         */
        @Override
        public BitcasaError[] newArray(int size) {
            return new BitcasaError[size];
        }
    };

    /**
     * Gets the error code.
     *
     * @return The error code.
     */
    public int getCode() {
        return this.code;
    }

    /**
     * Sets the error code.
     *
     * @param code The error code.
     */
    public void setCode(final int code) {
        this.code = code;
    }

    /**
     * Gets the error message.
     *
     * @return The error message.
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Sets the error message.
     *
     * @param message The error message.
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * Gets the data value.
     *
     * @return The data value.
     */
    public String getData() {
        return this.data;
    }

    /**
     * Sets the data value.
     *
     * @param data The data parameter.
     */
    public void setData(final String data) {
        this.data = data;
    }
}
