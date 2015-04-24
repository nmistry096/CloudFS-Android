package com.bitcasa.cloudfs.model;

/**
 * Represents Create Action details.
 */
public class ActionCreate extends BaseAction {

    /**
     * Initializes a new instance of the action class.
     *
     * @param action Data from base action.
     */
    public ActionCreate(final BaseAction action) {
        setAction(action.historyAction);
        setApplicationData(action.data);
        setDataDateContentLastModified(action.data.dateContentLastModified);
        setDataDateCreated(action.data.dateCreated);
        setDataDateMetaLastModified(action.data.dateMetaLastModified);
        setDataExtension(action.data.extension);
        setDataIsMirrored(action.data.isMirrored);
        setDataMime(action.data.mime);
        setDataName(action.data.name);
        setDataParentId(action.data.parentId);
        setDataSize(action.data.size);
        setPath(action.path);
        setType(action.type);
        setVersion(action.version);
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
     * Gets the ID of the item's parent.
     *
     * @return The ID of the item's parent.
     */
    public final String getDataParentId() {
        return this.data.parentId;
    }

    /**
     * Sets the ID of the item's parent.
     *
     * @param dataParentId The ID of the item's parent.
     */
    public final void setDataParentId(final String dataParentId) {
        this.data.parentId = dataParentId;
    }

    /**
     * Gets the name of the item.
     *
     * @return The name of the item.
     */
    public final String getDataName() {
        return this.data.name;
    }

    /**
     * Sets the name of the item.
     *
     * @param dataName The name of the item.
     */
    public final void setDataName(final String dataName) {
        this.data.name = dataName;
    }

    /**
     * Gets the extension of the item.
     *
     * @return The extension of the item.
     */
    public final String getDataExtension() {
        return this.data.extension;
    }

    /**
     * Sets the extension of the item.
     *
     * @param dataExtension The extension of the item.
     */
    public final void setDataExtension(final String dataExtension) {
        this.data.extension = dataExtension;
    }

    /**
     * Gets the created date of the item.
     *
     * @return The created date of the item.
     */
    public final double getDataDateCreated() {
        return this.data.dateCreated;
    }

    /**
     * Sets the created date of the item.
     *
     * @param dataDateCreated The created date of the item.
     */
    public final void setDataDateCreated(final double dataDateCreated) {
        this.data.dateCreated = dataDateCreated;
    }

    /**
     * Gets the last modified date of the item's meta data.
     *
     * @return The last modified date of the item's meta data.
     */
    public final double getDataDateMetaLastModified() {
        return this.data.dateMetaLastModified;
    }

    /**
     * Sets the last modified date of the item's meta data.
     *
     * @param dataDateMetaLastModified The last modified date of the item's meta data.
     */
    public final void setDataDateMetaLastModified(final double dataDateMetaLastModified) {
        this.data.dateMetaLastModified = dataDateMetaLastModified;
    }

    /**
     * Gets the last modified date of the item's content.
     *
     * @return The last modified date of the item's content.
     */
    public final double getDataDateContentLastModified() {
        return this.data.dateContentLastModified;
    }

    /**
     * Sets the last modified date of the item's content.
     *
     * @param dataDateContentLastModified The last modified date of the item's content.
     */
    public final void setDataDateContentLastModified(final double dataDateContentLastModified) {
        this.data.dateContentLastModified = dataDateContentLastModified;
    }

    /**
     * Gets the size of the item.
     *
     * @return The size of the item.
     */
    public final double getDataSize() {
        return this.data.size;
    }

    /**
     * Sets the size of the item.
     *
     * @param dataSize The size of the item.
     */
    public final void setDataSize(final double dataSize) {
        this.data.size = dataSize;
    }

    /**
     * Gets the mime type of the item.
     *
     * @return The mime type of the item.
     */
    public final String getDataMime() {
        return this.data.mime;
    }

    /**
     * Sets the mime type of the item.
     *
     * @param dataMime The mime type of the item.
     */
    public final void setDataMime(final String dataMime) {
        this.data.mime = dataMime;
    }

    /**
     * Gets the boolean stating whether the data is mirrored.
     *
     * @return Boolean stating whether the data is mirrored.
     */
    public final boolean getDataIsMirrored() {
        return this.data.isMirrored;
    }

    /**
     * Sets the boolean stating whether the data is mirrored.
     *
     * @param dataIsMirrored Boolean stating whether the data is mirrored.
     */
    public final void setDataIsMirrored(final boolean dataIsMirrored) {
        this.data.isMirrored = dataIsMirrored;
    }

    /**
     * Gets the extra data.
     *
     * @return The extra data.
     */
    public final ActionData getApplicationData() {
        return this.data;
    }

    /**
     * Sets the extra data.
     *
     * @param applicationData The extra data.
     */
    public final void setApplicationData(final ActionData applicationData) {
        this.data = applicationData;
    }
}
