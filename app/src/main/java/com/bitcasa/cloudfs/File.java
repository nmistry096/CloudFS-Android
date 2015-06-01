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

import android.os.Parcel;
import android.os.Parcelable;

import com.bitcasa.cloudfs.Utils.BitcasaProgressListener;
import com.bitcasa.cloudfs.Utils.BitcasaRESTConstants;
import com.bitcasa.cloudfs.api.RESTAdapter;
import com.bitcasa.cloudfs.exception.BitcasaException;
import com.bitcasa.cloudfs.model.ItemMeta;

import java.io.IOException;
import java.io.InputStream;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

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
     * @param parentState        The parent state of the item.
     * @param shareKey           The share key of the item if the item is of type share.
     */
    public File(final RESTAdapter restAdapter,
                final ItemMeta itemMeta,
                final String absoluteParentPath,
                final String parentState,
                final String shareKey) {
        super(restAdapter, itemMeta, absoluteParentPath, parentState, shareKey);
        this.extension = itemMeta.getExtension();
        this.mime = itemMeta.getMime();
        this.size = itemMeta.getSize();
    }

    /**
     * Initializes the Item instance.
     *
     * @param source The parcel object parameter.
     */
    public File(Parcel source) {
        super(source);
        extension = source.readString();
        mime = source.readString();
        size = source.readLong();
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param out   The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written. May be 0 or PARCELABLE_WRITE_RETURN_VALUE
     */
    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeString(extension);
        out.writeString(mime);
        out.writeLong(size);
    }

    /**
     * {@inheritDoc}
     */
    public static final Parcelable.Creator<File> CREATOR = new Parcelable.Creator<File>() {

        /**
         *Create a new instance of the Parcelable class, instantiating it from the given Parcel whose data had previously been written by Parcelable.writeToParcel()
         * @param source The Parcel to read the object's data from.
         * @return Returns a new instance of the Parcelable class.
         */
        @Override
        public File createFromParcel(Parcel source) {
            return new File(source);
        }

        /**
         *Create a new array of the Parcelable class
         * @param size Size of the array
         * @return Returns an array of the Parcelable class, with every entry initialized to null
         */
        @Override
        public File[] newArray(int size) {
            return new File[size];
        }
    };

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
     */
    public boolean setMime(final String mime) throws BitcasaException {
        final AbstractMap<String, String> values = new HashMap<String, String>();
        values.put(BitcasaRESTConstants.PARAM_MIME, mime);
        final boolean status = this.changeAttributes(values, BitcasaRESTConstants.VersionExists.FAIL);

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
    public void download(final String localDestinationPath, final BitcasaProgressListener listener)
            throws BitcasaException, IOException {
        this.restAdapter.downloadFile(this, 0, localDestinationPath, listener);
    }

    /**
     * Gets the file download url.
     * Please note that this download url will expire within 24 hours.
     *
     * @return The file download url.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public String downloadUrl() throws BitcasaException {
        try {
            return this.restAdapter.downloadUrl(this.getPath(), this.getSize());
        } catch (IOException e) {
            throw new BitcasaException(e);
        }
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
    public File[] versions(final int startVersion, final int endVersion, final int limit)
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
     */
    @Override
    public boolean changeAttributes(final Map<String, String> values, BitcasaRESTConstants.VersionExists ifConflict)
            throws BitcasaException {
        if (ifConflict == null) {
            ifConflict = BitcasaRESTConstants.VersionExists.FAIL;
        }

        final File meta = this.restAdapter.alterFileMeta(this, values, this.version, ifConflict);

        this.id = meta.getId();
        this.type = meta.getType();
        this.name = meta.getName();
        this.dateCreated = meta.getDateCreated();
        this.dateMetaLastModified = meta.getDateMetaLastModified();
        this.dateContentLastModified = meta.getDateContentLastModified();
        this.version = meta.version;
        this.applicationData = meta.applicationData;
        this.extension = meta.getExtension();
        this.mime = meta.getMime();
        this.size = meta.getSize();

        return true;
    }
}
