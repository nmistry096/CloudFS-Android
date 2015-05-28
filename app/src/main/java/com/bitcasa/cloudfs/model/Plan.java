/**
 * Bitcasa Client Android SDK
 * Copyright (C) 2015 Bitcasa, Inc.
 * 1200 Park Place,
 * Suite 350 San Mateo, CA 94403.
 * <p/>
 * This file contains an SDK in Java for accessing the Bitcasa infinite drive in Android platform.
 * <p/>
 * For support, please send email to sdks@bitcasa.com.
 */
package com.bitcasa.cloudfs.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.bitcasa.cloudfs.api.RESTAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Model class for plan.
 */
public class Plan implements Parcelable{

    /**
     * Display name of the account plan.
     */
    @SerializedName("name")
    private String displayName;

    /**
     * Id of the account plan.
     */
    private String id;

    /**
     * Account plan limit.
     */
    @SerializedName("limit")
    private Long limit;

    /**
     * Initializes a new instance of the account plan.
     *
     * @param displayName Display name of the account plan.
     * @param id          Id of the account plan.
     * @param limit       Account plan limit.
     */
    public Plan(String displayName, String id, Long limit) {
        this.displayName = displayName;
        this.id = id;
        this.limit = limit;
    }

    /**
     * Initializes the Account instance using a Parcel.
     *
     * @param in The parcel object.
     */
    public Plan(Parcel in) {
        displayName = in.readString();
        id = in.readString();
        limit = in.readLong();
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
        out.writeString(displayName);
        out.writeString(id);
        out.writeLong(limit);
    }

    public static final Parcelable.Creator<Plan> CREATOR = new Parcelable.Creator<Plan>() {

        /**
         *Create a new instance of the Parcelable class, instantiating it from the given Parcel whose data had previously been written by Parcelable.writeToParcel()
         * @param source The Parcel to read the object's data from.
         * @return Returns a new instance of the Parcelable class.
         */
        @Override
        public Plan createFromParcel(Parcel source) {
            return new Plan(source);
        }

        /**
         *Create a new array of the Parcelable class
         * @param size Size of the array
         * @return Returns an array of the Parcelable class, with every entry initialized to null
         */
        @Override
        public Plan[] newArray(int size) {
            return new Plan[size];
        }
    };


    /**
     * Gets the display name of the account plan.
     *
     * @return The account plan display name.
     */
    public String getDisplayName() {
        return this.displayName;
    }

    /**
     * Sets the display name of the account plan.
     *
     * @param displayName The display name to be set.
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the id of the account plan.
     *
     * @return The account plan id.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Sets the id of the account plan.
     *
     * @param id The plan id to be set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the limit of the account plan.
     *
     * @return The account plan limit.
     */
    public Long getLimit() {
        return this.limit;
    }

    /**
     * Sets the limit of the account plan.
     *
     * @param limit The plan limit to be set.
     */
    public void setLimit(long limit) {
        this.limit = limit;
    }
}
