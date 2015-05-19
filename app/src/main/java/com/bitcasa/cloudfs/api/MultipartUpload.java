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
package com.bitcasa.cloudfs.api;

import android.util.Log;

import com.bitcasa.cloudfs.BitcasaError;
import com.bitcasa.cloudfs.Credential;
import com.bitcasa.cloudfs.Utils.BitcasaProgressListener;
import com.bitcasa.cloudfs.Utils.BitcasaRESTConstants;
import com.bitcasa.cloudfs.exception.BitcasaException;
import com.bitcasa.cloudfs.model.BitcasaResponse;
import com.bitcasa.cloudfs.model.ItemMeta;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * The MultipartUpload class provides utility methods for the file upload process.
 */
public class MultipartUpload {

    /**
     * Class name constant used for logging.
     */
    private static final String TAG = MultipartUpload.class.getSimpleName();

    /**
     * The line feed parameter.
     */
    private static final String LINE_FEED = "\r\n";

    /**
     * The boundaries of the upload segments.
     */
    private final String boundary;
    /**
     * The Bitcasa REST utility instance.
     */
    private final BitcasaRESTUtility restUtility;
    /**
     * The HttpsURLConnection object.
     */
    private final HttpsURLConnection connection;
    /**
     * The file output stream.
     */
    private final OutputStream outputStream;
    /**
     * The instance of PrintWriter.
     */
    private final PrintWriter printWriter;
    /**
     * The file upload progress timer.
     */
    private long progressUpdateTimer = System.currentTimeMillis();

    /**
     * The file input stream buffer array size.
     */
    private static final int BUFFER_SIZE = 4096;

    /**
     * Initializes an instance of MultipartUpload.
     *
     * @param credential The application credentials.
     * @param url        The file url.
     * @param utility    The rest utility instance.
     * @throws IOException If a network error occurs.
     */
    public MultipartUpload(final Credential credential, final String url, final BitcasaRESTUtility utility) throws
            IOException {
        this.restUtility = utility;

        this.boundary = "---" + System.currentTimeMillis() + "---";

        final Map<String, String> headers = new HashMap<String, String>();
        headers.put(BitcasaRESTConstants.HEADER_CONTENT_TYPE, "multipart/form-data; boundary="
                + this.boundary);

        this.connection = (HttpsURLConnection) new URL(url).openConnection();
        this.connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_POST);
        this.restUtility.setRequestHeaders(credential, this.connection, headers);
        this.connection.setChunkedStreamingMode(0);
        this.connection.setDoOutput(true);
        this.outputStream = this.connection.getOutputStream();
        this.printWriter = new PrintWriter(new OutputStreamWriter(this.outputStream,
                BitcasaRESTConstants.UTF_8_ENCODING), true);
    }

    /**
     * Adds an upload form field to the print writer.
     *
     * @param fieldName  The upload form  field name.
     * @param fieldValue The upload form field value.
     */
    public void addUploadFormField(final CharSequence fieldName, final CharSequence fieldValue) {
        this.printWriter.append("--").append(this.boundary).append(this.LINE_FEED);
        this.printWriter.append("Content-Disposition: form-data; name=\"").append(fieldName).append("\"")
                .append(this.LINE_FEED);
        this.printWriter.append("Content-Type: text/plain; charset=" +
                BitcasaRESTConstants.UTF_8_ENCODING).append(
                this.LINE_FEED);
        this.printWriter.append(this.LINE_FEED);
        this.printWriter.append(fieldValue).append(this.LINE_FEED);
        this.printWriter.flush();
    }

    /**
     * Add a file to the print writer to upload.
     *
     * @param uploadFile The file to be uploaded.
     * @param listener   The upload progress listener.
     * @throws IOException If a network error occurs.
     */
    public void addFile(final File uploadFile, final BitcasaProgressListener listener) throws IOException {
        final long fileTotalSize = uploadFile.length();
        final String fileName = uploadFile.getName();
        this.printWriter.append("--").append(this.boundary).append(this.LINE_FEED);
        this.printWriter.append("Content-Disposition: form-data; name=\"" +
                BitcasaRESTConstants.BODY_FILE + "\"; filename=\"")
                .append(fileName).append("\"").append(
                this.LINE_FEED);
        this.printWriter.append("Content-Transfer-Encoding: binary")
                .append(this.LINE_FEED);
        this.printWriter.append(this.LINE_FEED);
        this.printWriter.flush();

        final FileInputStream inputStream = new FileInputStream(uploadFile);
        try {
            long totalTransferred = 0;
            final byte[] buffer = new byte[MultipartUpload.BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                if (!Thread.currentThread().isInterrupted()) {
                    this.outputStream.write(buffer, 0, bytesRead);
                    totalTransferred += bytesRead;
                    if ((listener != null)
                            && ((System.currentTimeMillis() - this.progressUpdateTimer) >
                            BitcasaRESTConstants.PROGRESS_UPDATE_INTERVAL)) {
                        Log.d(MultipartUpload.TAG, "file transferred so far: "
                                + totalTransferred + " out of total file size: "
                                + fileTotalSize);
                        final int percentage = (int) ((totalTransferred * 100) / fileTotalSize);
                        listener.onProgressUpdate(fileName, (percentage == 0) ? 1 : percentage, BitcasaProgressListener.ProgressAction.BITCASA_ACTION_UPLOAD);
                        this.progressUpdateTimer = System.currentTimeMillis();
                    }
                } else {
                    if (listener != null) {
                        listener.canceled(fileName, BitcasaProgressListener.ProgressAction.BITCASA_ACTION_UPLOAD);
                        throw new IOException("Thread Interrupted");
                    }
                }
            }
        } finally {
            inputStream.close();
        }
        this.outputStream.flush();
        inputStream.close();

        this.printWriter.append(this.LINE_FEED);
        this.printWriter.flush();

        this.printWriter.append("--").append(this.boundary).append("--").append(this.LINE_FEED);
        this.printWriter.close();
    }

    /**
     * Finishes the file upload process.
     *
     * @param restAdapter The REST Adapter instance.
     * @param parentPath  The parent path.
     * @return The uploaded file.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public com.bitcasa.cloudfs.File finishUpload(final RESTAdapter restAdapter, final String parentPath) throws BitcasaException {
        com.bitcasa.cloudfs.File meta = null;
        InputStream is = null;

        try {
            final int responseCode = this.connection.getResponseCode();
            Log.d(MultipartUpload.TAG, "upload response code: " + responseCode);
            final BitcasaError error = this.restUtility.checkRequestResponse(this.connection);
            if (error == null) {
                is = this.connection.getInputStream();
                if (is != null) {
                    final String response = this.restUtility.getResponseFromInputStream(is);
                    final BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);
                    if (!bitcasaResponse.getResult().isJsonNull()) {
                        final ItemMeta itemMeta = new Gson().fromJson(bitcasaResponse.getResult(),
                                ItemMeta.class);
                        meta = new com.bitcasa.cloudfs.File(restAdapter.clone(), itemMeta, parentPath, BitcasaRESTConstants.ITEM_STATE_NORMAL, null);
                    }
                }
            }
        } catch (final IOException e) {
            throw new BitcasaException(e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (final IOException e) {
                throw new BitcasaException(e);
            }
            if (this.connection != null) {
                this.connection.disconnect();
            }
        }

        return meta;
    }
}
