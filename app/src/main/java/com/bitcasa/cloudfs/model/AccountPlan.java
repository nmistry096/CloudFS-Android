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
 * Model class for account plan.
 */
public class AccountPlan {

    /**
     * The call to action text of the account plan.
     */
    @SerializedName("cta_text")
    private final String ctaText;

    /**
     * Display name of the account plan.
     */
    @SerializedName("display_name")
    private final String displayName;

    /**
     * Call to action value of the account plan.
     */
    @SerializedName("cta_value")
    private final String ctaValue;

    /**
     * ID of the account plan.
     */
    private final String id;

    /**
     * Initializes a new instance of a account plan.
     *
     * @param ctaText     CTA text of the plan.
     * @param displayName Display name of the plan.
     * @param ctaValue    CTA value of the plan.
     * @param id          ID of the plan.
     */
    public AccountPlan(final String ctaText, final String displayName, final String ctaValue, final String id) {
        this.ctaText = ctaText;
        this.displayName = displayName;
        this.ctaValue = ctaValue;
        this.id = id;
    }

    /**
     * Gets the CTA Text of the account plan.
     *
     * @return CTA Text of the account plan.
     */
    public final String getCtaText() {
        return this.ctaText;
    }

    /**
     * Gets the display name of the account plan.
     *
     * @return Display name of the account plan
     */
    public final String getDisplayName() {
        return this.displayName;
    }

    /**
     * Get the CTA Value of the account plan.
     *
     * @return CTA Value of the account plan
     */
    public final String getCtaValue() {
        return this.ctaValue;
    }

    /**
     * Gets the ID of the account plan.
     *
     * @return ID of the account plan
     */
    public final String getId() {
        return this.id;
    }
}
