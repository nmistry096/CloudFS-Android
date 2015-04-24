package com.bitcasa.cloudfs.model;

/**
 * Model class for account holds.
 */
public class Holds {

    /**
     * States whether the account is deleted.
     */
    private Boolean deleted;

    /**
     * States whether the account is over the limit.
     */
    private Boolean otl;

    /**
     * States whether the account is suspended.
     */
    private Boolean suspended;

    /**
     * Initializes a new instance of account holds.
     *
     * @param deleted   States whether the account is deleted.
     * @param otl       States whether the account is over the limit.
     * @param suspended States whether the account is suspended.
     */
    public Holds(final Boolean deleted, final Boolean otl, final Boolean suspended) {
        this.deleted = deleted;
        this.otl = otl;
        this.suspended = suspended;
    }

    /**
     * Gets whether the account is deleted.
     *
     * @return Boolean stating whether the account is deleted.
     */
    public final Boolean getDeleted() {
        return this.deleted;
    }

    /**
     * Sets whether the account is deleted.
     *
     * @param deleted Boolean stating whether the account is deleted.
     */
    public final void setDeleted(final Boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * Gets whether the account is over the limit.
     *
     * @return Boolean stating whether the account is over the limit.
     */
    public final Boolean getOtl() {
        return this.otl;
    }

    /**
     * Sets whether the account is over the limit.
     *
     * @param otl Boolean stating whether the account is over the limit.
     */
    public final void setOtl(final Boolean otl) {
        this.otl = otl;
    }

    /**
     * Gets whether the account is suspended.
     *
     * @return Boolean stating whether the account is suspended.
     */
    public final Boolean getSuspended() {
        return this.suspended;
    }

    /**
     * Sets whether the account is suspended.
     *
     * @param suspended Boolean stating whether the account is suspended.
     */
    public final void setSuspended(final Boolean suspended) {
        this.suspended = suspended;
    }
}
