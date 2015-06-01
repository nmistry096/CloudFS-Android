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

import com.bitcasa.cloudfs.api.RESTAdapter;
import com.bitcasa.cloudfs.model.UserProfile;

/**
 * The Account class provides accessibility to CloudFS Account.
 */
public class Account implements Parcelable {

    /**
     * The api service to access REST end point.
     */
    private final RESTAdapter restAdapter;

    /**
     * The account plan instance.
     */
    private Plan plan;

    /**
     * The account locale value.
     */
    private final String accountLocale;

    /**
     * The user id.
     */
    private final String id;

    /**
     * The account state id.
     */
    private final String stateId;

    /**
     * The account state display name.
     */
    private final String stateDisplayName;

    /**
     * The account's storage usage level.
     */
    private long storageUsage;

    /**
     * The account's over storage limit.
     */
    private final boolean overStorageLimit;

    /**
     * The account session locale.
     */
    private final String sessionLocale;

    /**
     * Initializes an instance of the Account.
     *
     * @param restAdapter The REST Adapter instance.
     * @param profile     The user profile.
     */
    public Account(final RESTAdapter restAdapter, final UserProfile profile, final Plan plan) {
        this.restAdapter = restAdapter;
        this.plan = plan;
        this.accountLocale = profile.getLocale();
        this.id = profile.getAccountId();
        this.stateDisplayName = profile.getAccountState().getDisplayName();
        this.stateId = profile.getAccountState().getId();
        this.storageUsage = profile.getStorage().getUsage();
        this.overStorageLimit = profile.getStorage().getOtl();
        this.sessionLocale = profile.getSession().getLocale();
    }

    /**
     * Initializes the Account instance using a Parcel.
     *
     * @param in The parcel object.
     */
    public Account(Parcel in) {
        accountLocale = in.readString();
        id = in.readString();
        stateDisplayName = in.readString();
        stateId = in.readString();
        storageUsage = in.readLong();
        overStorageLimit = in.readInt() != 0;
        sessionLocale = in.readString();
        restAdapter = (RESTAdapter) in.readValue(RESTAdapter.class.getClassLoader());
        plan = (Plan) in.readValue(Plan.class.getClassLoader());
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
        out.writeString(accountLocale);
        out.writeString(id);
        out.writeString(stateDisplayName);
        out.writeString(stateId);
        out.writeLong(storageUsage);
        out.writeInt(overStorageLimit ? 1 : 0);
        out.writeString(sessionLocale);
        out.writeValue(restAdapter);
        out.writeValue(plan);
    }

    public static final Parcelable.Creator<Account> CREATOR = new Parcelable.Creator<Account>() {

        /**
         *Create a new instance of the Parcelable class, instantiating it from the given Parcel whose data had previously been written by Parcelable.writeToParcel()
         * @param source The Parcel to read the object's data from.
         * @return Returns a new instance of the Parcelable class.
         */
        @Override
        public Account createFromParcel(Parcel source) {
            return new Account(source);
        }

        /**
         *Create a new array of the Parcelable class
         * @param size Size of the array
         * @return Returns an array of the Parcelable class, with every entry initialized to null
         */
        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    /**
     * Gets the user id.
     *
     * @return The user id.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Gets the account's storage usage.
     *
     * @return The storage used by the account.
     */
    public long getStorageUsage() {
        return this.storageUsage;
    }

    /**
     * Gets the account's storage limit.
     *
     * @return The account's storage limit.
     */
    public long getStorageLimit() {
        return this.plan.getLimit();
    }

    /**
     * Gets the account plan display name.
     *
     * @return The account plan display name.
     */
    public String getPlanDisplayName() {
        return this.plan.getDisplayName();
    }

    /**
     * Gets the account locale value.
     *
     * @return The account locale value.
     */
    public String getAccountLocale() {
        return this.accountLocale;
    }

    /**
     * Gets the account state display name.
     *
     * @return The account state display name.
     */
    public String getStateDisplayName() {
        return this.stateDisplayName;
    }

    /**
     * Gets the account state id.
     *
     * @return The account state id.
     */
    public String getStateId() {
        return this.stateId;
    }

    /**
     * Gets a value indicating whether the storage limit is exceeded.
     *
     * @return True if the limit is exceeded, otherwise false.
     */
    public boolean getOverStorageLimit() {
        return this.overStorageLimit;
    }

    /**
     * Gets the account plan id.
     *
     * @return The account plan id
     */
    public String getPlanId() {
        return this.plan.getId();
    }

    /**
     * Gets the account session locale.
     *
     * @return The account session locale.
     */
    public String getSessionLocale() {
        return this.sessionLocale;
    }

    /**
     * Gets the account plan.
     *
     * @return The account plan.
     */
    public Plan getPlan() {
        return this.plan;
    }

    /**
     * Creates a string containing a concise, human-readable description of Account object.
     *
     * @return The printable representation of Account object.
     */
    @Override
    public String toString() {

        return "] \nlocale[" + this.sessionLocale + "] \nstate_display_name[" + this.stateDisplayName
                + "] \nstate_id[" + this.stateId + "] \nstorage_usage[" + this.storageUsage +
                "] \nstorage_limit[" + this.plan.getLimit() + "] \nstorage_otl[" + this.overStorageLimit
                + "] \nplan_display_name[" + this.plan.getDisplayName() + "] \nplan_id[" + this.plan.getId()
                + "] \nsession_locale[" + this.sessionLocale + "] \nid[" + this.id + "]*****";
    }

}
