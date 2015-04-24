package com.bitcasa.cloudfs.model;

/**
 * Represents Move Action details.
 */
public class ActionMove extends BaseAction {

    /**
     * Initializes a new instance of the action class.
     *
     * @param action Data from base action.
     */
    public ActionMove(final BaseAction action) {
        setAction(action.historyAction);
        setDataExists(action.data.exists);
        setDataName(action.data.name);
        setDataTo(action.data.to);
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
     * Gets the path that the item was moved to.
     *
     * @return The path that the item was moved to.
     */
    public final String getDataTo() {
        return this.data.to;
    }

    /**
     * Sets the path that the item was moved to.
     *
     * @param dataTo The path that the item was moved to.
     */
    public final void setDataTo(final String dataTo) {
        this.data.to = dataTo;
    }

    /**
     * Gets the exists option that was used.
     *
     * @return The exists option that was used.
     */
    public final String getDataExists() {
        return this.data.exists;
    }

    /**
     * Sets the exists option that was used.
     *
     * @param dataExists The exists option that was used.
     */
    public final void setDataExists(final String dataExists) {
        this.data.exists = dataExists;
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
}
