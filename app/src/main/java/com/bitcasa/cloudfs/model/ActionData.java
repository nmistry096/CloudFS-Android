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

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

/**
 * Represents the details of a history action.
 */
public class ActionData {

    /**
     * Initializes an empty ActionData object.
     */
    public ActionData() {
    }

    /**
     * Initializes a new instance of ActionData with data from an ActionDataDefault instance.
     *
     * @param actionData Available action data.
     */
    public ActionData(final ActionDataDefault actionData) {
        this.shareKey = actionData.shareKey;
        this.exists = actionData.exists;
        this.path = actionData.path;
        this.paths = actionData.paths;
        this.shareUrl = actionData.shareUrl;
        this.id = actionData.id;
        this.name = actionData.name;
        this.applicationData = actionData.applicationData;
        this.to = actionData.to;
        this.parentId = actionData.parentId;
        this.extension = actionData.extension;
        this.dateCreated = actionData.dateCreated;
        this.dateMetaLastModified = actionData.dateMetaLastModified;
        this.dateContentLastModified = actionData.dateContentLastModified;
        this.size = actionData.size;
        this.mime = actionData.mime;
        this.isMirrored = actionData.isMirrored;
    }

    /**
     * Initializes a new instance of ActionData with data from an ActionDataAlter instance.
     *
     * @param actionData Available action data.
     */
    public ActionData(final ActionDataAlter actionData) {
        this.isMirrored = actionData.isMirrored;
        this.shareKey = actionData.shareKey;
        this.exists = actionData.exists;
        this.path = actionData.path;
        this.paths = actionData.paths;
        this.shareUrl = actionData.shareUrl;
        this.alteredName = actionData.alteredName;
        this.alteredDateCreated = actionData.alteredDateCreated;
        this.alteredId = actionData.alteredId;
        this.applicationData = actionData.applicationData;
        this.to = actionData.to;
        this.parentId = actionData.parentId;
        this.extension = actionData.extension;
        this.dateMetaLastModified = actionData.dateMetaLastModified;
        this.dateContentLastModified = actionData.dateContentLastModified;
        this.size = actionData.size;
        this.mime = actionData.mime;
    }

    /**
     * The share key.
     */
    @SerializedName("share_key")
    protected String shareKey;

    /**
     * The exists choice that was used.
     */
    protected String exists;

    /**
     * The path that the action was performed to.
     */
    protected String path;

    /**
     * Paths that the action was performed to.
     */
    protected String[] paths;

    /**
     * The share url.
     */
    @SerializedName("share_url")
    protected String shareUrl;

    /**
     * Altered names of the item.
     */
    @SerializedName("name")
    protected AlterData alteredName;

    /**
     * Altered created dates of the item.
     */
    @SerializedName("date_created")
    protected DateCreated alteredDateCreated;

    /**
     * Altered ids of the item.
     */
    @SerializedName("id")
    protected AlterData alteredId;

    /**
     * Id of the item.
     */
    protected String id;

    /**
     * Name of the item.
     */
    protected String name;

    /**
     * Application data of the item.
     */
    @SerializedName("application_data")
    protected JsonElement applicationData;

    /**
     * Path that the item was copied/moved to.
     */
    protected String to;

    /**
     * Parent id of the item.
     */
    @SerializedName("parent_id")
    protected String parentId;

    /**
     * Extension of the item.
     */
    protected String extension;

    /**
     * Created date if the item.
     */
    @SerializedName("date_created")
    protected double dateCreated;

    /**
     * Last modified date of the item's meta data.
     */
    @SerializedName("date_meta_last_modified")
    protected double dateMetaLastModified;

    /**
     * Last modified date of the item's content.
     */
    @SerializedName("date_content_last_modified")
    protected double dateContentLastModified;

    /**
     * Size of the item.
     */
    protected double size;

    /**
     * Mime type of the item.
     */
    protected String mime;

    /**
     * Boolean stating whether the item is mirrored.
     */
    @SerializedName("is_mirrored")
    protected boolean isMirrored;
}
