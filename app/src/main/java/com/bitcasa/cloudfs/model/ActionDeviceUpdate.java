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
        setAction(action.historyAction);
        setDataIdFrom(action.data.alteredId.getFrom());
        setDataIdTo(action.data.alteredId.getTo());
        setVersion(action.version);
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
