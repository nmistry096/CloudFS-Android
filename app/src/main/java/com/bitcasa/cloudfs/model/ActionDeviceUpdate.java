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
 * Represents Device Update Action details.
 */
public class ActionDeviceUpdate extends BaseAction {

    /**
     * Initializes a new instance of the action class.
     *
     * @param action Data from base action.
     */
    public ActionDeviceUpdate(final BaseAction action) {
        this.setAction(action.historyAction);
        this.setDataIdFrom(action.data.alteredId.getFrom());
        this.setDataIdTo(action.data.alteredId.getTo());
        this.setVersion(action.version);
    }

    /**
     * Gets the previous ID of the device.
     *
     * @return The previous ID of the device.
     */
    public final String getDataIdFrom() {
        return this.data.alteredId.getFrom();
    }

    /**
     * Sets the previous ID of the device.
     *
     * @param dataIdFrom The previous ID of the device.
     */
    public final void setDataIdFrom(final String dataIdFrom) {
        this.data.alteredId.setFrom(dataIdFrom);
    }

    /**
     * Gets the new ID of the device.
     *
     * @return The new ID of the device.
     */
    public final String getDataIdTo() {
        return this.data.alteredId.getTo();
    }

    /**
     * Sets the new ID of the device.
     *
     * @param dataIdTo The new ID of the device.
     */
    public final void setDataIdTo(final String dataIdTo) {
        this.data.alteredId.setTo(dataIdTo);
    }

}
