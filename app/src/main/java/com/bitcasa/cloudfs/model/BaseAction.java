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

import com.bitcasa.cloudfs.HistoryActions;
import com.google.gson.annotations.Expose;

/**
 * Represents the base action details.
 */
public class BaseAction {

    /**
     * The history action value.
     */
    @Expose
    protected HistoryActions historyAction;

    /**
     * The version of the action.
     */
    @Expose
    protected int version;

    /**
     * The action that was performed.
     */
    @Expose
    protected String action;

    /**
     * The path that the action was performed to.
     */
    @Expose
    protected String path;

    /**
     * The type of item that the action was performed on.
     */
    @Expose
    protected String type;

    /**
     * The extra details of the action.
     */
    protected ActionData data = new ActionData();

    /**
     * Initializes an empty BaseAction instance.
     */
    public BaseAction() {
    }

    /**
     * Sets the extra data of the action.
     *
     * @param actionData The extra data of the action.
     */
    public final void setData(final ActionData actionData) {
        this.data = actionData;
    }

    /**
     * Sets the history action value.
     *
     * @param action The history action value.
     */
    public final void setAction(final HistoryActions action) {
        this.historyAction = action;
    }

    /**
     * Sets the version of the action.
     *
     * @param version The version of the action.
     */
    public final void setVersion(final int version) {
        this.version = version;
    }

    /**
     * Gets the action string.
     *
     * @return The action string.
     */
    public final String getActionString() {
        return this.action;
    }

    /**
     * Gets the history action value.
     *
     * @return The history action value.
     */
    public final HistoryActions getAction() {
        return this.historyAction;
    }

    /**
     * Gets the version of the action.
     *
     * @return The version of the action.
     */
    public final int getVersion() {
        return this.version;
    }
}
