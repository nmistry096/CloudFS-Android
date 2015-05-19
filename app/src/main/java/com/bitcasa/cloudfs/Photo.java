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
     * @param parentState        The parent state of the item.
     * @param shareKey           The share key of the item if the item is of type share.
     */
    public Photo(final RESTAdapter restAdapter, final ItemMeta itemMeta,
                 final String absoluteParentPath, final String parentState, final String shareKey) {
        super(restAdapter, itemMeta, absoluteParentPath, parentState, shareKey);
    }

}
