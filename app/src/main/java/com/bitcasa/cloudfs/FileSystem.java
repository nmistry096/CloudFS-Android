/**
 * Bitcasa Client Android SDK
 * Copyright (C) 2015 Bitcasa, Inc.
 * 215 Castro Street, 2nd Floor
 * Mountain View, CA 94041
 *
 * This file contains an SDK in Java for accessing the Bitcasa infinite drive in Android platform.
 *
 * For support, please send email to support@bitcasa.com.
 */

package com.bitcasa.cloudfs;

import com.bitcasa.cloudfs.api.RESTAdapter;
import com.bitcasa.cloudfs.exception.BitcasaException;

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
     * @throws IOException      If a network error occurs.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public Folder root() throws IOException, BitcasaException {
        return restAdapter.getFolderMeta("/");
    }

    /**
     * Lists the items in the trash.
     *
     * @return The list of trash items.
     * @throws IOException      If a network error occurs.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public Item[] listTrash() throws IOException, BitcasaException {
        return restAdapter.browseTrash();
    }

    /**
     * Gets the item at the given path.
     *
     * @param path The file system item path.
     * @return The item at the given path.
     * @throws IOException      If a network error occurs.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public Item getItem(String path) throws IOException, BitcasaException {
        return restAdapter.getItemMeta(path);
    }

    /**
     * Lists the shares in the file system.
     *
     * @return The list of shares in the file system.
     * @throws IOException      If a network error occurs
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public Share[] listShares() throws IOException, BitcasaException {
        return restAdapter.listShare();
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
    public Share createShare(String path, String password) throws IOException, BitcasaException {
        return restAdapter.createShare(path, password);
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
    public Share retrieveShare(String shareKey, String password)
            throws IOException, BitcasaException {
        Share[] shares = restAdapter.listShare();
        Share sharedItem = null;

        for (Share share : shares) {
            if (share.getShareKey().equals(shareKey)) {
                sharedItem = share;
                break;
            }
        }
        return sharedItem;
    }
}
