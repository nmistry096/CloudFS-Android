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
 * Model class for plan.
 */
public class PlanMeta {

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
     * Initializes a new instance of the account plan model.
     *
     * @param displayName Display name of the account plan.
     * @param id          Id of the account plan.
     * @param limit       Account plan limit.
     */
    public PlanMeta(String displayName, String id, Long limit) {
        this.displayName = displayName;
        this.id = id;
        this.limit = limit;
    }

    /**
     * Gets the display name of the account plan.
     *
     * @return The account plan display name.
     */
    public String getDisplayName() {
        return this.displayName;
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
     * Gets the limit of the account plan.
     *
     * @return The account plan limit.
     */
    public Long getLimit() {
        return this.limit;
    }

}
