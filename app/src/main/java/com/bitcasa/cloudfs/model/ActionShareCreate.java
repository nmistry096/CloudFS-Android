package com.bitcasa.cloudfs.model;

/**
 * Represents Share Create Action details.
 */
public class ActionShareCreate extends BaseAction {

    /**
     * Initializes a new instance of the action class.
     *
     * @param action Data from base action.
     */
    public ActionShareCreate(final BaseAction action) {
        setAction(action.historyAction);
        setVersion(action.version);
        setDataPaths(action.data.paths);
        setDataShareKey(action.data.shareKey);
        setDataShareUrl(action.data.shareUrl);
    }

    /**
     * Gets the share key.
     *
     * @return The share key.
     */
    public final String getDataShareKey() {
        return this.data.shareKey;
    }

    /**
     * Sets the share key.
     *
     * @param dataShareKey The share key.
     */
    public final void setDataShareKey(final String dataShareKey) {
        this.data.shareKey = dataShareKey;
    }

    /**
     * Gets the share paths.
     *
     * @return The share paths.
     */
    public final String[] getDataPaths() {
        return this.data.paths;
    }

    /**
     * Sets the share paths.
     *
     * @param dataPaths The share paths.
     */
    public final void setDataPaths(final String[] dataPaths) {
        this.data.paths = dataPaths;
    }

    /**
     * Gets the share url.
     *
     * @return The share url.
     */
    public final String getDataShareUrl() {
        return this.data.shareUrl;
    }

    /**
     * Sets the share url.
     *
     * @param dataUrl The share url.
     */
    public final void setDataShareUrl(final String dataUrl) {
        this.data.shareUrl = dataUrl;
    }

}
