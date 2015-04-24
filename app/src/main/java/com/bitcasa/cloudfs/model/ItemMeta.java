package com.bitcasa.cloudfs.model;

import com.google.gson.annotations.SerializedName;

/**
 * Model class for item meta data.
 */
public class ItemMeta {

    /**
     * The item id.
     */
    private final String id;

    /**
     * The parent id.
     */
    @SerializedName("parent_id")
    private final String parentId;

    /**
     * The type of item.
     */
    private final String type;

    /**
     * The name of item.
     */
    private String name;

    /**
     * The item extension.
     */
    private final String extension;

    /**
     * The item size.
     */
    private final long size;

    /**
     * The mime type of item.
     */
    private final String mime;

    /**
     * The created timestamp.
     */
    @SerializedName("date_created")
    private final Integer dateCreated;

    /**
     * The last modified timestamp of metadata.
     */
    @SerializedName("date_meta_last_modified")
    private final Integer dateMetaLastModified;

    /**
     * The last modified timestamp of contents.
     */
    @SerializedName("date_content_last_modified")
    private final Integer dateContentLastModified;

    /**
     * The item version.
     */
    private final Integer version;

    /**
     * Additional information about item.
     */
    @SerializedName("application_data")
    private final ApplicationData applicationData;

    /**
     * A value indicating whether the item is mirrored.
     */
    @SerializedName("is_mirrored")
    private final Boolean isMirrored;

    /**
     * Initializes a new instance of an item meta.
     *
     * @param id                      The item id.
     * @param parentId                The parent id.
     * @param type                    The type of item.
     * @param name                    The name of item.
     * @param extension               The item extension.
     * @param size                    The item size.
     * @param mime                    The mime type of item.
     * @param dateCreated             The created timestamp.
     * @param dateMetaLastModified    The last modified timestamp of metadata.
     * @param dateContentLastModified The last modified timestamp of contents.
     * @param version                 The item version.
     * @param applicationData         Additional information about item.
     * @param isMirrored              A value indicating whether the item is mirrored.
     */
    public ItemMeta(final String id, final String parentId, final String type, final String name, final String extension, final long size,
                    final String mime, final Integer dateCreated, final Integer dateMetaLastModified, final Integer dateContentLastModified,
                    final Integer version, final ApplicationData applicationData, final Boolean isMirrored) {
        this.id = id;
        this.parentId = parentId;
        this.type = type;
        this.name = name;
        this.extension = extension;
        this.size = size;
        this.mime = mime;
        this.dateCreated = dateCreated;
        this.dateMetaLastModified = dateMetaLastModified;
        this.dateContentLastModified = dateContentLastModified;
        this.version = version;
        this.applicationData = applicationData;
        this.isMirrored = isMirrored;
    }

    /**
     * Gets the item id.
     *
     * @return The item id.
     */
    public final String getId() {
        return this.id;
    }

    /**
     * Gets the parent id.
     *
     * @return The parent id.
     */
    public final String getParentId() {
        return this.parentId;
    }

    /**
     * Gets the item type.
     *
     * @return The item type.
     */
    public final String getType() {
        return this.type;
    }

    /**
     * Gets the item name.
     *
     * @return The item name.
     */
    public final String getName() {
        return this.name;
    }

    /**
     * Sets the item name.
     *
     * @param name Item name.
     */
    public final void setName(final String name) {
        this.name = name;
    }

    /**
     * Gets the extension.
     *
     * @return The item extension.
     */
    public final String getExtension() {
        return this.extension;
    }

    /**
     * Gets the item size.
     *
     * @return The item size.
     */
    public final long getSize() {
        return this.size;
    }

    /**
     * Gets the mime type.
     *
     * @return The mime type.
     */
    public final String getMime() {
        return this.mime;
    }

    /**
     * Gets the created date.
     *
     * @return The created date.
     */
    public final Integer getDateCreated() {
        return this.dateCreated;
    }

    /**
     * Gets the last modified date of meta.
     *
     * @return The last modified date of meta.
     */
    public final Integer getDateMetaLastModified() {
        return this.dateMetaLastModified;
    }

    /**
     * Gets the last modified date of contents.
     *
     * @return The last modified date of meta.
     */
    public final Integer getDateContentLastModified() {
        return this.dateContentLastModified;
    }

    /**
     * Gets the version.
     *
     * @return The version.
     */
    public final Integer getVersion() {
        return this.version;
    }

    /**
     * Gets the application data.
     *
     * @return The application data.
     */
    public final ApplicationData getApplicationData() {
        return this.applicationData;
    }

    /**
     * Gets a value indicating whether the item is a folder.
     *
     * @return True if the item is a folder, otherwise false.
     */
    public final boolean isFolder() {
        return this.type.equalsIgnoreCase("folder");
    }

    /**
     * Gets a value indicating whether the item is mirrored.
     *
     * @return True if the item is mirrored, otherwise false.
     */
    public final boolean isMirrored() {
        return this.isMirrored;
    }
}
