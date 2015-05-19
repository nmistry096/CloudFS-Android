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
 * Represents Meta Alteration Action details.
 */
public class ActionAlterMeta extends BaseAction {

    /**
     * Initializes a new instance of the action class.
     *
     * @param action Data from base action.
     */
    public ActionAlterMeta(final BaseAction action) {
        this.setAction(action.historyAction);
        this.setDataCreatedTo(action.data.alteredDateCreated.getTo());
        this.setDataNameFrom(action.data.alteredName.getFrom());
        this.setDataNameTo(action.data.alteredName.getTo());
        this.setDateCreatedFrom(action.data.alteredDateCreated.getFrom());
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

    /**
     * Gets the previous item name.
     *
     * @return The previous item name.
     */
    public final String getDataNameFrom() {
        return this.data.alteredName.getFrom();
    }

    /**
     * Sets the previous item name.
     *
     * @param dataNameFrom The previous item name.
     */
    public final void setDataNameFrom(final String dataNameFrom) {
        this.data.alteredName.setFrom(dataNameFrom);
    }

    /**
     * Get the new item name.
     *
     * @return The new item name.
     */
    public final String getDataNameTo() {
        return this.data.alteredName.getTo();
    }

    /**
     * Sets the new item name.
     *
     * @param dataNameTo The new item name.
     */
    public final void setDataNameTo(final String dataNameTo) {
        this.data.alteredName.setTo(dataNameTo);
    }

    /**
     * Gets the previous created date.
     *
     * @return The previous created date.
     */
    public final double getDateCreatedFrom() {
        return this.data.alteredDateCreated.getFrom();
    }

    /**
     * Sets the previous created date.
     *
     * @param dataDateCreatedFrom The previous created date.
     */
    public final void setDateCreatedFrom(final double dataDateCreatedFrom) {
        this.data.alteredDateCreated.setFrom(dataDateCreatedFrom);
    }

    /**
     * Gets the new created date.
     *
     * @return The new created date.
     */
    public final double getDataCreatedTo() {
        return this.data.alteredDateCreated.getTo();
    }

    /**
     * Sets the new created date.
     *
     * @param dataDateCreatedTo The new created date.
     */
    public final void setDataCreatedTo(final double dataDateCreatedTo) {
        this.data.alteredDateCreated.setTo(dataDateCreatedTo);
    }

}
