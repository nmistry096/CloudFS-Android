package com.bitcasa.cloudfs.model;

/**
 * Model class for item meta list.
 */
public class ItemList {

    /**
     * The item meta.
     */
    private final ItemMeta meta;

    /**
     * List of sub items.
     */
    private final ItemMeta[] items;

    /**
     * Initializes a new instance of an item list.
     *
     * @param meta  The item meta.
     * @param items List of sub items.
     */
    public ItemList(final ItemMeta meta, final ItemMeta[] items) {
        this.meta = meta;
        this.items = items;
    }

    /**
     * Gets item meta.
     *
     * @return The item meta.
     */
    public final ItemMeta getMeta() {
        return this.meta;
    }

    /**
     * Gets list of sub items.
     *
     * @return List of sub items.
     */
    public final ItemMeta[] getItems() {
        return this.items;
    }
}
