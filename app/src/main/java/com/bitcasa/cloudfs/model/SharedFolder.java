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
 * Model class for item meta list.
 */
public class SharedFolder {

    /**
     * The share details.
     */
    private final ShareItem shareItem;

    /**
     * The share item meta.
     */
    private final ItemMeta meta;

    /**
     * List of sub items.
     */
    private final ItemMeta[] items;

    /**
     * Initializes a new instance of a shared folder.
     *
     * @param shareItem The share details.
     * @param meta      The share item meta.
     * @param items     List of sub items.
     */
    public SharedFolder(final ShareItem shareItem, final ItemMeta meta, final ItemMeta[] items) {
        this.shareItem = shareItem;
        this.meta = meta;
        this.items = items;
    }

    /**
     * Gets the share details.
     *
     * @return Share details
     */
    public final ShareItem getShareItem() {
        return this.shareItem;
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
