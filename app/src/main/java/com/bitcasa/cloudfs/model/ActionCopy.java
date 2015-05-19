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

import com.bitcasa.cloudfs.Utils.BitcasaRESTConstants;

/**
 * Represents Copy Action details.
 */
public class ActionCopy extends BaseAction {

    /**
     * Initializes a new instance of the action class.
     *
     * @param action Data from base action.
     */
    public ActionCopy(final BaseAction action) {
        this.setAction(action.historyAction);
        this.setDataExists(action.data.exists);
        this.setDataName(action.data.name);
        this.setDataTo(action.data.to);
        this.setPath(action.path);
        this.setType(action.type);
        this.setVersion(action.version);
    }

    /**
     * Gets the path that the action was performed on.
     *
     * @return The path that the action was performed on.
     */
    public final String getPath() {
        return this.path;
    }

    /**
     * Sets the path that the action was performed on.
     *
     * @param path The path that the action was performed on.
     */
    public final void setPath(final String path) {
        this.path = path;
    }

    /**
     * Gets the type of item.
     *
     * @return The type of item.
     */
    public final String getType() {
        return this.type;
    }

    /**
     * Sets the type of item.
     *
     * @param type The type of item.
     */
    public final void setType(final String type) {
        this.type = type;
    }

    /**
     * Gets the path that the item was copied to.
     *
     * @return The path that the item was copied to.
     */
    public final String getDataTo() {
        return this.data.to;
    }

    /**
     * Sets the path that the item was copied to.
     *
     * @param dataTo The path that the item was copied to.
     */
    public final void setDataTo(final String dataTo) {
        this.data.to = dataTo;
    }

    /**
     * Gets the {@link BitcasaRESTConstants.Exists Exists} option that was used.
     *
     * @return The {@link BitcasaRESTConstants.Exists Exists} option that was used.
     */
    public final String getDataExists() {
        return this.data.exists;
    }

    /**
     * Sets the {@link BitcasaRESTConstants.Exists Exists} option that was used.
     *
     * @param dataExists The {@link BitcasaRESTConstants.Exists Exists} option that was used.
     */
    public final void setDataExists(final String dataExists) {
        this.data.exists = dataExists;
    }

    /**
     * Gets the name of the item.
     *
     * @return The name of the item.
     */
    public final String getDataName() {
        return this.data.name;
    }

    /**
     * Sets the name of the item.
     *
     * @param dataName The name of the item.
     */
    public final void setDataName(final String dataName) {
        this.data.name = dataName;
    }

}
