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
        setAction(action.historyAction);
        setDataId(action.data.id);
        setVersion(action.version);
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
