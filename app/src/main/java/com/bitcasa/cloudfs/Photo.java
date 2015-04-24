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
import com.bitcasa.cloudfs.model.ItemMeta;

/**
 * The Photo class provides accessibility to CloudFS Photo.
 */
public class Photo extends File {

    /**
     * Initializes an instance of Photo.
     *
     * @param restAdapter        The REST Adapter instance.
     * @param itemMeta           The photo meta data returned from REST Adapter.
     * @param absoluteParentPath The absolute parent path of this photo.
     */
    public Photo(final RESTAdapter restAdapter, final ItemMeta itemMeta,
                 final String absoluteParentPath) {
        super(restAdapter, itemMeta, absoluteParentPath);
    }

}
