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
 * Represents Device Deletion Action details.
 */
public class ActionDeviceDelete extends BaseAction {

    /**
     * Initializes a new instance of the action class.
     *
     * @param action Data from base action.
     */
    public ActionDeviceDelete(final BaseAction action) {
        this.setAction(action.historyAction);
        this.setDataId(action.data.id);
        this.setVersion(action.version);
    }

    /**
     * Gets the ID of the device.
     *
     * @return The ID of the device.
     */
    public final String getDataId() {
        return this.data.id;
    }

    /**
     * Sets the ID of the device.
     *
     * @param dataId The ID of the device.
     */
    public final void setDataId(final String dataId) {
        this.data.id = dataId;
    }

}
