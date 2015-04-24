package com.bitcasa.cloudfs.model;


/**
 * A Wrapper class of application data.
 */
public class ApplicationData {

    /**
     * The original path.
     */
    private String originalPath;

    /**
     * The relative id path.
     */
    private String relativeIdPath;

    /**
     * The nonce value.
     */
    private String nonce;

    /**
     * The payload data.
     */
    private String payload;

    /**
     * The digest value.
     */
    private String digest;

    /**
     * The album art.
     */
    private String albumArt;


    /**
     * Initializes an empty ApplicationData object.
     */
    public ApplicationData() {
        super();
    }

    /**
     * Initializes an instance of ApplicationData.
     * @param originalPath The original path.
     * @param relativePath The relative id path.
     * @param nonce The nonce data.
     * @param payload The payload data.
     * @param digest The digest value.
     * @param albumArt The album art.
     */
    public ApplicationData(final String originalPath,
                           final String relativePath,
                           final String nonce,
                           final String payload,
                           final String digest,
                           final String albumArt) {
        this();
        this.originalPath = originalPath;
        this.relativeIdPath = relativePath;
        this.nonce = nonce;
        this.payload = payload;
        this.digest = digest;
        this.albumArt = albumArt;
    }

    /**
     * Gets the original path.
     * @return The original path.
     */
    public String getOriginalPath() {
        return originalPath;
    }

    /**
     * Sets the original path.
     * @param originalPath The original path.
     */
    public void setOriginalPath(String originalPath) {
        this.originalPath = originalPath;
    }

    /**
     * Gets the relative id path.
     * @return The application relative id path.
     */
    public String getRelativeIdPath() {
        return relativeIdPath;
    }

    /**
     * Sets the application relative id path.
     * @param relativeIdPath The relative id path.
     */
    public void setRelativeIdPath(String relativeIdPath) {
        this.relativeIdPath = relativeIdPath;
    }

    /**
     * Gets the nonce data.
     * @return The nonce data.
     */
    public String getNonce() {
        return nonce;
    }

    /**
     * Sets the nonce data.
     * @param nonce The nonce data.
     */
    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    /**
     * Gets the payload data.
     * @return The payload data.
     */
    public String getPayload() {
        return payload;
    }

    /**
     * Sets the payload data.
     * @param payload The payload data.
     */
    public void setPayload(String payload) {
        this.payload = payload;
    }

    /**
     * Gets the digest data.
     * @return The digest data.
     */
    public String getDigest() {
        return digest;
    }
    /**
     * Sets the digest data.
     * @param digest The digest data.
     */
    public void setDigest(String digest) {
        this.digest = digest;
    }

    /**
     * Gets the album art.
     * @return The album art.
     */
    public String getAlbumArt() {
        return albumArt;
    }

    /**
     * Sets the album art.
     * @param albumArt The application album art.
     */
    public void setAlbumArt(String albumArt) {
        this.albumArt = albumArt;
    }
}