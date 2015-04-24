package com.bitcasa.cloudfs.model;

/**
 * Represents Device Creation Action details.
 */
public class ActionDeviceCreate extends BaseAction {

    /**
     * Initializes a new instance of the action class.
     *
     * @param action Data from base action.
     */
    public ActionDeviceCreate(final BaseAction action) {
        setAction(action.historyAction);
        setDataId(action.data.id);
        setDataName(action.data.name);
        setVersion(action.version);
    }

    /**
     * Gets the ID of the device.
     *
     * @return ID of the device.
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

    /**
     * Gets the name of the device.
     *
     * @return The name of the device.
     */
    public final String getDataName() {
        return this.data.name;
    }

    /**
     * Sets the name of the device.
     *
     * @param dataName The name of the device.
     */
    public final void setDataName(final String dataName) {
        this.data.name = dataName;
    }

}
