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
 * Represents Trash Action details.
 */
public class ActionTrash extends BaseAction {

    /**
     * Initializes a new instance of the action class.
     *
     * @param action Data from base action.
     */
    public ActionTrash(final BaseAction action) {
        this.setAction(action.historyAction);
        this.setPath(action.path);
        this.setType(action.type);
        this.setVersion(action.version);
    }

    /**
     * Gets the path of the item.
     *
     * @return The path of the item.
     */
    public final String getPath() {
        return this.path;
    }

    /**
     * Sets the path of the item.
     *
     * @param path The path of the item.
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
