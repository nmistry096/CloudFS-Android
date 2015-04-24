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
 * The Video class provides accessibility to CloudFS Video.
 */
public class Video extends File {

    /**
     * Initializes an instance of Video.
     *
     * @param restAdapter        The REST Adapter instance.
     * @param itemMeta           The video meta data returned from REST Adapter.
     * @param absoluteParentPath The absolute parent path of this video.
     */
    public Video(final RESTAdapter restAdapter, final ItemMeta itemMeta,
                 final String absoluteParentPath) {
        super(restAdapter, itemMeta, absoluteParentPath);
    }

}
