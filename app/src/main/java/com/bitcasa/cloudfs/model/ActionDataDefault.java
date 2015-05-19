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
public class ActionDataDefault {

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
