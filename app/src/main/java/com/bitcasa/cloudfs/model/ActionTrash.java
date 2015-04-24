package com.bitcasa.cloudfs.model;

/**
 * Represents Trash Action details.
 */
public class ActionTrash extends BaseAction {

    /**
     * Initializes a new instance of the action class.
     *
     * @param action Data from base action.
     */
    public ActionTrash(final BaseAction action) {
        setAction(action.historyAction);
        setPath(action.path);
        setType(action.type);
        setVersion(action.version);
    }

    /**
     * Gets the path of the item.
     *
     * @return The path of the item.
     */
    public final String getPath() {
        return this.path;
    }

    /**
     * Sets the path of the item.
     *
     * @param path The path of the item.
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
}
