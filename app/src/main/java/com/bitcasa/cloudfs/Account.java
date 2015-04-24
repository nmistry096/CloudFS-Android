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

import com.bitcasa.cloudfs.api.RESTAdapter;
import com.bitcasa.cloudfs.model.UserProfile;

/**
 * The Account class provides accessibility to CloudFS Account.
 */
public class Account {

    /**
     * The api service to access REST end point.
     */
    private RESTAdapter restAdapter;

    /**
     * The account locale value.
     */
    private String accountLocale;

    /**
     * The user id.
     */
    private String id;

    /**
     * The account state id.
     */
    private String stateId;

    /**
     * The account state display name.
     */
    private String stateDisplayName;

    /**
     * The account's storage usage level.
     */
    private long storageUsage;

    /**
     * The account's storage limit.
     */
    private long storageLimit;

    /**
     * The account's over storage limit.
     */
    private boolean overStorageLimit;

    /**
     * The account plan display name.
     */
    private String planDisplayName;

    /**
     * The account plan id.
     */
    private String planId;

    /**
     * The account session locale.
     */
    private String sessionLocale;

    /**
     * Initializes an instance of the Account.
     *
     * @param restAdapter The REST Adapter instance.
     * @param profile     The user profile.
     */
    public Account(final RESTAdapter restAdapter, final UserProfile profile) {
        this.restAdapter = restAdapter;
        this.accountLocale = profile.getLocale();
        this.id = profile.getId();
        this.stateDisplayName = profile.getAccountState().getDisplayName();
        this.stateId = profile.getAccountState().getId();
        this.storageUsage = profile.getStorage().getUsage();
        this.storageLimit = profile.getStorage().getLimit();
        this.overStorageLimit = profile.getStorage().getOtl();
        this.planDisplayName = profile.getAccountPlan().getDisplayName();
        this.planId = profile.getAccountPlan().getId();
        this.sessionLocale = profile.getSession().getLocale();
    }

    /**
     * Gets the user id.
     *
     * @return The user id.
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the account's storage usage.
     *
     * @return The storage used by the account.
     */
    public long getStorageUsage() {
        return storageUsage;
    }

    /**
     * Gets the account's storage limit.
     *
     * @return The account's storage limit.
     */
    public long getStorageLimit() {
        return storageLimit;
    }

    /**
     * Gets the account plan display name.
     *
     * @return The account plan display name.
     */
    public String getPlanDisplayName() {
        return planDisplayName;
    }

    /**
     * Gets the account locale value.
     *
     * @return The account locale value.
     */
    public String getAccountLocale() {
        return accountLocale;
    }

    /**
     * Gets the account state display name.
     *
     * @return The account state display name.
     */
    public String getStateDisplayName() {
        return stateDisplayName;
    }

    /**
     * Gets the account state id.
     *
     * @return The account state id.
     */
    public String getStateId() {
        return stateId;
    }

    /**
     * Gets a value indicating whether the storage limit is exceeded.
     *
     * @return True if the limit is exceeded, otherwise false.
     */
    public boolean getOverStorageLimit() {
        return overStorageLimit;
    }

    /**
     * Gets the account plan id.
     *
     * @return The account plan id
     */
    public String getPlanId() {
        return planId;
    }

    /**
     * Gets the account session locale.
     *
     * @return The account session locale.
     */
    public String getSessionLocale() {
        return sessionLocale;
    }

    /**
     * Creates a string containing a concise, human-readable description of Account object.
     *
     * @return The printable representation of Account object.
     */
    @Override
    public String toString() {

        return "] \nlocale[" + sessionLocale + "] \nstate_display_name[" + stateDisplayName
                + "] \nstate_id[" + stateId + "] \nstorage_usage[" + storageUsage +
                "] \nstorage_limit[" + storageLimit + "] \nstorage_otl[" + overStorageLimit
                + "] \nplan_display_name[" + planDisplayName + "] \nplan_id[" + planId
                + "] \nsession_locale[" + sessionLocale + "] \nid[" + id + "]*****";
    }

}
