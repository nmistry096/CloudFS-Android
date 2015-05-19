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

package com.bitcasa.cloudfs;

import com.bitcasa.cloudfs.api.RESTAdapter;
import com.bitcasa.cloudfs.exception.BitcasaException;
import com.bitcasa.cloudfs.model.ShareItem;

import java.io.IOException;

/**
 * The FileSystem class provides accessibility to CloudFS FileSystem.
 */
public class FileSystem {

    /**
     * The restAdapter service to access REST end point.
     */
    private final RESTAdapter restAdapter;

    /**
     * Initializes an instance of FileSystem.
     *
     * @param restAdapter The REST Adapter instance.
     */
    public FileSystem(final RESTAdapter restAdapter) {
        this.restAdapter = restAdapter;
    }

    /**
     * Gets the file system root folder.
     *
     * @return The file system root folder.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public Folder root() throws BitcasaException {
        return this.restAdapter.getFolderMeta("/");
    }

    /**
     * Lists the items in the trash.
     *
     * @return The list of trash items.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public Item[] listTrash() throws BitcasaException {
        return this.restAdapter.browseTrash(null);
    }

    /**
     * Gets the item at the given path.
     *
     * @param path The file system item path.
     * @return The item at the given path.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public Item getItem(final String path) throws BitcasaException {
        return this.restAdapter.getItemMeta(path);
    }

    /**
     * Lists the shares in the file system.
     *
     * @return The list of shares in the file system.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public Share[] listShares() throws BitcasaException {
        return this.restAdapter.listShare();
    }

    /**
     * Creates a new share.
     *
     * @param path     The full path of an item to share.
     * @param password The password of the share to be created.
     * @return The new share instance.
     * @throws IOException      If a network error occurs.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public Share createShare(final String path, final String password) throws IOException, BitcasaException {
        return this.restAdapter.createShare(path, password);
    }

    /**
     * Creates a new share.
     *
     * @param paths     The full array paths of items to share.
     * @param password The password of the share to be created.
     * @return The new share instance.
     * @throws IOException      If a network error occurs.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public Share createShare(final String[] paths, final String password) throws IOException, BitcasaException{
        return  this.restAdapter.createShare(paths, password);
    }

    /**
     * Retrieves an existing share.
     *
     * @param shareKey The share key.
     * @param password The password of the share.
     * @return The share specified by the key.
     * @throws IOException      If a network error occurs
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public Share retrieveShare(final String shareKey, final String password)
            throws IOException, BitcasaException {
        final Share[] shares = this.restAdapter.listShare();
        Share sharedItem = null;

        if (password != null) {
            this.restAdapter.unlockShare(shareKey, password);
        }

        for (final Share share : shares) {
            if (share.getShareKey().equals(shareKey)) {
                sharedItem = share;
                break;
            }
        }

        //To handle share keys not found in the current users shares.
        if (sharedItem == null) {
            ShareItem shareItem = new ShareItem(shareKey, null, null, null, 0, 0);
            sharedItem = new Share(this.restAdapter, shareItem, null);
        }

        return sharedItem;
    }
}
