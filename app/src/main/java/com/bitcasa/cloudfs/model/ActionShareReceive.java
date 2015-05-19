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

/**
 * Represents Share Receive Action details.
 */
public class ActionShareReceive extends BaseAction {

    /**
     * Initializes a new instance of the action class.
     *
     * @param action Data from base action.
     */
    public ActionShareReceive(final BaseAction action) {
        this.setAction(action.historyAction);
        this.setDataExists(action.data.exists);
        this.setDataPath(action.data.path);
        this.setVersion(action.version);
    }

    /**
     * Gets the exists choice that was used.
     *
     * @return The exists choice that was used.
     */
    public final String getDataExists() {
        return this.data.exists;
    }

    /**
     * Sets the exists choice that was used.
     *
     * @param dataExists The exists choice that was used.
     */
    public final void setDataExists(final String dataExists) {
        this.data.exists = dataExists;
    }

    /**
     * Gets the path that the share was received to.
     *
     * @return The path that the share was received to.
     */
    public final String getDataPath() {
        return this.data.path;
    }

    /**
     * Sets the path that the share was received to.
     *
     * @param dataPath The path that the share was received to.
     */
    public final void setDataPath(final String dataPath) {
        this.data.path = dataPath;
    }

}
