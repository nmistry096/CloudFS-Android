package com.bitcasa.cloudfs.model;

import com.google.gson.JsonElement;

/**
 * Model class for storage.
 */
public class Storage {

    /**
     * Used storage amount.
     */
    private final Integer usage;

    /**
     * Storage limit.
     */
    private final JsonElement limit;

    /**
     * Stores the value after parsing the limit attribute.
     */
    private Integer parsedLimit;

    /**
     * Over the limit.
     */
    private final Boolean otl;

    /**
     * Initializes a new instance of storage details.
     *
     * @param usage Used storage amount.
     * @param limit Storage limit.
     * @param otl   Over the limit.
     */
    public Storage(final Integer usage, final Integer limit, final Boolean otl) {
        this.usage = usage;
        this.parsedLimit = limit;
        this.limit = null;
        this.otl = otl;
    }

    /**
     * Gets the used storage amount.
     *
     * @return Used storage amount.
     */
    public final Integer getUsage() {
        return this.usage;
    }

    /**
     * Gets the storage limit.
     *
     * @return Storage limit.
     */
    public final Integer getLimit() {

        if (parsedLimit == null) {
            int limit = 0;
            try {
                limit = this.limit.getAsInt();
            }
            catch (NumberFormatException e) {
                // this.limit is not a number and limit is already assigned to 0.
            }
            finally {
                parsedLimit = new Integer(limit);
            }
        }

        return parsedLimit;
    }

    /**
     * Gets whether the storage limit is over.
     *
     * @return Over the limit.
     */
    public final Boolean getOtl() {
        return this.otl;
    }
}
