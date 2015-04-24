package com.bitcasa.cloudfs.api;

import android.util.Log;

import com.bitcasa.cloudfs.BitcasaError;
import com.bitcasa.cloudfs.Credential;
import com.bitcasa.cloudfs.Utils.BitcasaProgressListener;
import com.bitcasa.cloudfs.Utils.BitcasaProgressListener.ProgressAction;
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
    private final String LINE_FEED = "\r\n";

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
    private HttpsURLConnection connection = null;
    /**
     * The file output stream.
     */
    private OutputStream outputStream;
    /**
     * The instance of PrintWriter.
     */
    private PrintWriter printWriter;
    /**
     * The file upload progress timer.
     */
    private long progressUpdateTimer = System.currentTimeMillis();

    /**
     * Initializes an instance of MultipartUpload.
     *
     * @param credential The application credentials.
     * @param url        The file url.
     * @param utility    The rest utility instance.
     * @throws IOException If a network error occurs.
     */
    public MultipartUpload(Credential credential, String url, BitcasaRESTUtility utility) throws
            IOException {
        this.restUtility = utility;

        boundary = "---" + System.currentTimeMillis() + "---";

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put(BitcasaRESTConstants.HEADER_CONTENT_TYPE, "multipart/form-data; boundary="
                + boundary);

        connection = (HttpsURLConnection) new URL(url).openConnection();
        connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_POST);
        restUtility.setRequestHeaders(credential, connection, headers);
        connection.setChunkedStreamingMode(0);
        connection.setDoOutput(true);
        outputStream = connection.getOutputStream();
        printWriter = new PrintWriter(new OutputStreamWriter(outputStream,
                BitcasaRESTConstants.UTF_8_ENCODING), true);
    }

    /**
     * Adds an upload form field to the print writer.
     *
     * @param fieldName  The upload form  field name.
     * @param fieldValue The upload form field value.
     */
    public void addUploadFormField(String fieldName, String fieldValue) {
        printWriter.append("--").append(boundary).append(LINE_FEED);
        printWriter.append("Content-Disposition: form-data; name=\"").append(fieldName).append("\"")
                .append(LINE_FEED);
        printWriter.append("Content-Type: text/plain; charset=" +
                BitcasaRESTConstants.UTF_8_ENCODING).append(
                LINE_FEED);
        printWriter.append(LINE_FEED);
        printWriter.append(fieldValue).append(LINE_FEED);
        printWriter.flush();
    }

    /**
     * Add a file to the print writer to upload.
     *
     * @param uploadFile The file to be uploaded.
     * @param listener   The upload progress listener.
     * @throws IOException If a network error occurs.
     */
    public void addFile(File uploadFile, BitcasaProgressListener listener) throws IOException {
        long fileTotalSize = uploadFile.length();
        long totalTransferred = 0;
        String fileName = uploadFile.getName();
        printWriter.append("--").append(boundary).append(LINE_FEED);
        printWriter.append("Content-Disposition: form-data; name=\"" +
                BitcasaRESTConstants.BODY_FILE + "\"; filename=\"")
                .append(fileName).append("\"").append(
                LINE_FEED);
        printWriter.append("Content-Transfer-Encoding: binary")
                .append(LINE_FEED);
        printWriter.append(LINE_FEED);
        printWriter.flush();

        FileInputStream inputStream = new FileInputStream(uploadFile);
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        try {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                if (!Thread.currentThread().isInterrupted()) {
                    outputStream.write(buffer, 0, bytesRead);
                    totalTransferred += bytesRead;
                    if (listener != null
                            && (System.currentTimeMillis() - progressUpdateTimer) >
                            BitcasaRESTConstants.PROGRESS_UPDATE_INTERVAL) {
                        Log.d(TAG, "file transfered so far: "
                                + totalTransferred + " out of total file size: "
                                + fileTotalSize);
                        int percentage = (int) (totalTransferred * 100 / fileTotalSize);
                        listener.onProgressUpdate(fileName, percentage == 0 ? 1
                                : percentage, ProgressAction.BITCASA_ACTION_UPLOAD);
                        progressUpdateTimer = System.currentTimeMillis();
                    }
                } else {
                    if (listener != null) {
                        listener.canceled(fileName, ProgressAction.BITCASA_ACTION_UPLOAD);
                        throw new IOException("Thread Interrupted");
                    }
                }
            }
        } finally {
            inputStream.close();
        }
        outputStream.flush();
        inputStream.close();

        printWriter.append(LINE_FEED);
        printWriter.flush();

        printWriter.append("--").append(boundary).append("--").append(LINE_FEED);
        printWriter.close();
    }

    /**
     * Finishes the file upload process.
     *
     * @param restAdapter The REST Adapter instance.
     * @return The uploaded file.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public com.bitcasa.cloudfs.File finishUpload(RESTAdapter restAdapter) throws BitcasaException {
        com.bitcasa.cloudfs.File meta = null;
        InputStream is = null;

        int responseCode;
        try {
            responseCode = connection.getResponseCode();
            Log.d(TAG, "upload response code: " + responseCode);
            BitcasaError error = restUtility.checkRequestResponse(connection);
            if (error == null) {
                is = connection.getInputStream();
                if (is != null) {
                    String response = restUtility.getResponseFromInputStream(is);
                    BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);
                    if (!bitcasaResponse.getResult().isJsonNull()) {
                        ItemMeta itemMeta = new Gson().fromJson(bitcasaResponse.getResult(),
                                ItemMeta.class);
                        meta = new com.bitcasa.cloudfs.File(restAdapter, itemMeta, null);
                    }
                }
            }
        } catch (IOException e) {
            throw new BitcasaException(e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                throw new BitcasaException(e);
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

        return meta;
    }
}
