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
 * Represents Delete Action details.
 */
public class ActionDelete extends BaseAction {

    /**
     * Initializes a new instance of the action class.
     *
     * @param action Data from base action.
     */
    public ActionDelete(final BaseAction action) {
        this.setAction(action.historyAction);
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

}
