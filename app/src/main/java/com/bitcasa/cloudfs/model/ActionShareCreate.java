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
 * Represents Share Create Action details.
 */
public class ActionShareCreate extends BaseAction {

    /**
     * Initializes a new instance of the action class.
     *
     * @param action Data from base action.
     */
    public ActionShareCreate(final BaseAction action) {
        this.setAction(action.historyAction);
        this.setVersion(action.version);
        this.setDataPaths(action.data.paths);
        this.setDataShareKey(action.data.shareKey);
        this.setDataShareUrl(action.data.shareUrl);
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
