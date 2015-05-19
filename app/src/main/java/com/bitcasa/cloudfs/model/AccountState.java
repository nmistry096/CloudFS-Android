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
 * Model class for account state.
 */
public class AccountState {

    /**
     * Display name of the account state.
     */
    @SerializedName("display_name")
    private final String displayName;

    /**
     * ID of the account state.
     */
    private final String id;

    /**
     * Initializes a new instance of a Account State.
     *
     * @param displayName Display name of the account state.
     * @param id          ID of the account state.
     */
    public AccountState(final String displayName, final String id) {
        this.displayName = displayName;
        this.id = id;
    }

    /**
     * Gets the display name of the account state.
     *
     * @return Display name of the account state
     */
    public final String getDisplayName() {
        return this.displayName;
    }

    /**
     * Gets the ID of the account state.
     *
     * @return ID of the account state
     */
    public final String getId() {
        return this.id;
    }
}
