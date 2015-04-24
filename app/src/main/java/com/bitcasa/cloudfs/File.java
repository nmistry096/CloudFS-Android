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

import com.bitcasa.cloudfs.Utils.BitcasaProgressListener;
import com.bitcasa.cloudfs.Utils.BitcasaRESTConstants;
import com.bitcasa.cloudfs.api.RESTAdapter;
import com.bitcasa.cloudfs.exception.BitcasaException;
import com.bitcasa.cloudfs.model.ItemMeta;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * The File class provides accessibility to CloudFS File.
 */
public class File extends Item {

    /**
     * The file mime type.
     */
    private String mime;

    /**
     * The file extension.
     */
    private String extension;

    /**
     * The file size.
     */
    private long size;

    /**
     * Initializes an instance of CloudFS File.
     *
     * @param restAdapter        The REST Adapter instance.
     * @param itemMeta           The file meta data returned from REST Adapter.
     * @param absoluteParentPath The absolute parent path of this file.
     */
    public File(final RESTAdapter restAdapter,
                final ItemMeta itemMeta,
                final String absoluteParentPath) {
        super(restAdapter, itemMeta, absoluteParentPath);
        this.extension = itemMeta.getExtension();
        this.mime = itemMeta.getMime();
        this.size = itemMeta.getSize();
    }

    /**
     * Gets the file extension.
     *
     * @return The file extension.
     */
    public String getExtension() {
        return this.extension;
    }

    /**
     * Gets the file size.
     *
     * @return The file size.
     */
    public long getSize() {
        return this.size;
    }

    /**
     * Gets the file mime type.
     *
     * @return The mime type.
     */
    public String getMime() {
        return this.mime;
    }

    /**
     * Sets the file mime type.
     *
     * @return A value indicating whether the operation was successful or not.
     * @throws BitcasaException If a CloudsFS API error occurs.
     * @throws IOException      If a network error occurs.
     */
    public boolean setMime(String mime) throws BitcasaException, IOException {
        HashMap<String, String> values = new HashMap<String, String>();
        values.put(BitcasaRESTConstants.PARAM_MIME, mime);
        boolean status = this.changeAttributes(values, BitcasaRESTConstants.VersionExists.FAIL);

        if (status) {
            this.mime = mime;
        }

        return status;
    }

    /**
     * Returns the InputStream of this file.
     *
     * @return The file InputStream.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public InputStream read() throws BitcasaException {
        return this.restAdapter.download(this, 0);
    }

    /**
     * Downloads this file into the given local destination path.
     *
     * @param localDestinationPath Local destination path to download the file.
     * @param listener             The BitcasaProgressListener to track the file download progress.
     * @throws BitcasaException If a CloudFS API error occurs.
     * @throws IOException      If a network error occurs.
     */
    public void download(String localDestinationPath, BitcasaProgressListener listener)
            throws BitcasaException, IOException {
        this.restAdapter.downloadFile(this, 0, localDestinationPath, listener);
    }

    /**
     * Gets the file download url.
     * Please note that this download url will expire within 24 hours.
     *
     * @return The file download url.
     * @throws IOException If a network error occurs.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public String downloadUrl() throws IOException, BitcasaException {
       String url = this.restAdapter.downloadUrl(this.getPath(), this.getSize());
        return url;
    }

    /**
     * Gets the file versions.
     *
     * @param startVersion The starting file version.
     * @param endVersion   The ending file version.
     * @param limit        The file version list limit.
     * @return The file version list.
     * @throws IOException If a network error occurs.
     */
    public File[] versions(int startVersion, int endVersion, int limit)
            throws BitcasaException, IOException {
        return this.restAdapter.listFileVersions(this.getPath(), startVersion, endVersion, limit);
    }

    /**
     * Changes the specified item attributes.
     *
     * @param values     The attributes to be changed.
     * @param ifConflict The action to be taken if a conflict occurs.
     * @return boolean          A value indicating whether the operation was successful or not.
     * @throws BitcasaException If a CloudFS API error occurs.
     * @throws IOException      If a network error occurs.
     */
    public boolean changeAttributes(HashMap<String, String> values, BitcasaRESTConstants.VersionExists ifConflict)
            throws BitcasaException, IOException {
        if (ifConflict == null) {
            ifConflict = BitcasaRESTConstants.VersionExists.FAIL;
        }

        File meta;
        meta = restAdapter.alterFileMeta(this, values, this.version, ifConflict);

        this.id = meta.getId();
        this.type = meta.getType();
        this.name = meta.getName();
        this.dateCreated = meta.getDateCreated();
        this.dateMetaLastModified = meta.getDateMetaLastModified();
        this.dateContentLastModified = meta.getDateContentLastModified();
        this.version = meta.version;
        this.extension = meta.getExtension();
        this.mime = meta.getMime();
        this.size = meta.getSize();

        return meta != null;
    }
}
