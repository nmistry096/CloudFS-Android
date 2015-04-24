package com.bitcasa.cloudfs.model;

import com.google.gson.annotations.SerializedName;

/**
 * Model class for a share.
 */
public class ShareItem {

    /**
     * ID of the share.
     */
    @SerializedName("share_key")
    private final String shareKey;

    /**
     * Url of the share.
     */
    private final String url;

    /**
     * Short url of the share.
     */
    @SerializedName("short_url")
    private final String shortUrl;

    /**
     * Size of the shared content.
     */
    @SerializedName("share_size")
    private final Integer size;

    /**
     * Created date of the share.
     */
    @SerializedName("date_created")
    private final long dateCreated;

    /**
     * Name of the shared item.
     */
    @SerializedName("share_name")
    private final String name;

    /**
     * Initializes a new instance of a shared item.
     *
     * @param shareKey    ID of the share.
     * @param name        Name of the share.
     * @param url         Url of the share.
     * @param shortUrl    Short url of the share.
     * @param size        Size of the shared content.
     * @param dateCreated Created date of the share.
     */
    public ShareItem(final String shareKey,
                     final String name,
                     final String url,
                     final String shortUrl,
                     final Integer size,
                     final long dateCreated) {
        this.shareKey = shareKey;
        this.name = name;
        this.url = url;
        this.shortUrl = shortUrl;
        this.size = size;
        this.dateCreated = dateCreated;
    }

    /**
     * Gets the key of the share.
     *
     * @return Key of the share.
     */
    public final String getShareKey() {
        return this.shareKey;
    }

    /**
     * Gets the url of the share.
     *
     * @return Url of the share.
     */
    public final String getUrl() {
        return this.url;
    }

    /**
     * Gets the short url of the share.
     *
     * @return Short url of the share.
     */
    public final String getShortUrl() {
        return this.shortUrl;
    }

    /**
     * Gets the size of the shared content.
     *
     * @return Size of the shared content.
     */
    public final Integer getSize() {
        return this.size;
    }

    /**
     * Gets the created date of the share.
     *
     * @return Created date of the share.
     */
    public final long getDateCreated() {
        return this.dateCreated;
    }

    /**
     * Gets the name of the share.
     *
     * @return Name of the share.
     */
    public final String getName() {
        return this.name;
    }
}
