package com.bitcasa.cloudfs.model;

/**
 * Represents the details of an altered attribute.
 */
public class AlterData {

    /**
     * Previous value.
     */
    private String from;

    /**
     * New value.
     */
    private String to;

    /**
     * Sets the previous value.
     *
     * @return The previous value.
     */
    public final String getFrom() {
        return this.from;
    }

    /**
     * Sets the previous value.
     *
     * @param from The previous value.
     */
    public final void setFrom(final String from) {
        this.from = from;
    }

    /**
     * Gets the new value.
     *
     * @return The new value.
     */
    public final String getTo() {
        return this.to;
    }

    /**
     * Sets the new value.
     *
     * @param to The new value.
     */
    public final void setTo(final String to) {
        this.to = to;
    }
}
