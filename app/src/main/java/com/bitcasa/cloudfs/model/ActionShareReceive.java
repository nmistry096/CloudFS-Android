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
        setAction(action.historyAction);
        setDataExists(action.data.exists);
        setDataPath(action.data.path);
        setVersion(action.version);
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
