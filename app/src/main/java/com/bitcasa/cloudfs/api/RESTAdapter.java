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

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.bitcasa.cloudfs.Account;
import com.bitcasa.cloudfs.BitcasaError;
import com.bitcasa.cloudfs.Credential;
import com.bitcasa.cloudfs.Folder;
import com.bitcasa.cloudfs.Item;
import com.bitcasa.cloudfs.Plan;
import com.bitcasa.cloudfs.Session;
import com.bitcasa.cloudfs.Share;
import com.bitcasa.cloudfs.User;
import com.bitcasa.cloudfs.Utils.BitcasaProgressListener;
import com.bitcasa.cloudfs.Utils.BitcasaRESTConstants;
import com.bitcasa.cloudfs.exception.BitcasaAuthenticationException;
import com.bitcasa.cloudfs.exception.BitcasaClientException;
import com.bitcasa.cloudfs.exception.BitcasaException;
import com.bitcasa.cloudfs.model.AccessToken;
import com.bitcasa.cloudfs.model.ActionData;
import com.bitcasa.cloudfs.model.ActionDataAlter;
import com.bitcasa.cloudfs.model.ActionDataDefault;
import com.bitcasa.cloudfs.model.BaseAction;
import com.bitcasa.cloudfs.model.BitcasaResponse;
import com.bitcasa.cloudfs.model.ItemList;
import com.bitcasa.cloudfs.model.ItemMeta;
import com.bitcasa.cloudfs.model.PlanMeta;
import com.bitcasa.cloudfs.model.ShareItem;
import com.bitcasa.cloudfs.model.SharedFolder;
import com.bitcasa.cloudfs.model.Storage;
import com.bitcasa.cloudfs.model.UserProfile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.net.ssl.HttpsURLConnection;

/**
 * Entry point to all CloudFS API requests.
 */
public class RESTAdapter implements Parcelable, Cloneable {

    private static final String TAG = RESTAdapter.class.getSimpleName();
    private static final String MISSING_PARAM = "Missing required parameter : ";

    private final BitcasaRESTUtility bitcasaRESTUtility;
    private final Credential credential;

    /**
     * The file download buffer data array size.
     */
    private static final int BUFFERED_INPUTSTREAM_DATA_ARRAY_SIZE = 1024;

    /**
     * The bitcasa authentication error message code
     */
    private static final int BITCASA_AUTHENTICATION_ERROR_CODE = 1020;

    /**
     * Constructor, takes in a credential instance and initialises the RESTAdapter instance.
     *
     * @param credential Application Credentials.
     */
    public RESTAdapter(final Credential credential) {
        this.bitcasaRESTUtility = new BitcasaRESTUtility();
        this.credential = credential;
    }

    /**
     * Initializes the credential instance using a Parcel.
     *
     * @param in The parcel object.
     */
    public RESTAdapter(Parcel in) {
        credential = (Credential) in.readValue(Credential.class.getClassLoader());
        bitcasaRESTUtility = new BitcasaRESTUtility();
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable's marshalled representation
     *
     * @return a bitmask indicating the set of special object types marshalled by the Parcelable
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param out   The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written. May be 0 or PARCELABLE_WRITE_RETURN_VALUE
     */
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeValue(credential);
    }

    public static final Parcelable.Creator<RESTAdapter> CREATOR = new Parcelable.Creator<RESTAdapter>() {

        /**
         *Create a new instance of the Parcelable class, instantiating it from the given Parcel whose data had previously been written by Parcelable.writeToParcel()
         * @param source The Parcel to read the object's data from.
         * @return Returns a new instance of the Parcelable class.
         */
        @Override
        public RESTAdapter createFromParcel(Parcel source) {
            return new RESTAdapter(source);
        }

        /**
         *Create a new array of the Parcelable class
         * @param size Size of the array
         * @return Returns an array of the Parcelable class, with every entry initialized to null
         */
        @Override
        public RESTAdapter[] newArray(int size) {
            return new RESTAdapter[size];
        }
    };

    /**
     * Returns a clone of the RESTAdapter.
     *
     * @return A clone of RESTAdapter.
     */
    public RESTAdapter clone() {
        return new RESTAdapter(this.credential);
    }

    /**
     * Validates whether the given string parameter is null or empty.
     *
     * @param parameter The parameter to validate.
     * @return The validate result, if the string is null or empty, return true and if not false.
     */
    private static boolean stringParameterNotValid(final String parameter) {
        return (parameter == null) || parameter.isEmpty();
    }

    /**
     * Validate whether an object parameter is not null.
     *
     * @param parameter The parameter to validate.
     * @return The validate result, if the object is null return true and if not false.
     */
    private static boolean objectParameterNotValid(final Object parameter) {
        return parameter == null;
    }

    /**
     * Authenticates with CloudFS and retrieves the access token.
     *
     * @param session  Session object.
     * @param username String username.
     * @param password String password.
     * @throws IOException              If a network error occurs.
     * @throws BitcasaException         If a CloudFS API error occurs.
     * @throws IllegalArgumentException If the parameters are invalid or misused.
     */
    public void authenticate(final Session session, final String username, final String password)
            throws IOException, BitcasaException, IllegalArgumentException {
        if (RESTAdapter.stringParameterNotValid(username)) {
            throw new IllegalArgumentException(RESTAdapter.MISSING_PARAM + "username");
        }

        if (RESTAdapter.stringParameterNotValid(password)) {
            throw new IllegalArgumentException(RESTAdapter.MISSING_PARAM + "password");
        }

        HttpsURLConnection connection = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;

        try {
            final SimpleDateFormat dateFormat = new SimpleDateFormat(BitcasaRESTConstants.DATE_FORMAT,
                    Locale.US);
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            String date = dateFormat.format(Calendar.getInstance(Locale.US).getTime());
            int index = date.lastIndexOf('+');
            if (index == -1) {
                index = date.length();
            }
            date = date.substring(0, index);

            final Map<String, String> bodyParams = new TreeMap<String, String>();
            bodyParams.put(BitcasaRESTConstants.PARAM_GRANT_TYPE,
                    Uri.encode(BitcasaRESTConstants.PARAM_PASSWORD, " "));
            bodyParams.put(BitcasaRESTConstants.PARAM_USERNAME, Uri.encode(username, " "));
            bodyParams.put(BitcasaRESTConstants.PARAM_PASSWORD, Uri.encode(password, " "));

            final String parameters = this.bitcasaRESTUtility.generateParamsString(bodyParams);
            final String url = this.bitcasaRESTUtility.getRequestUrl(this.credential,
                    BitcasaRESTConstants.METHOD_OAUTH2, BitcasaRESTConstants.METHOD_TOKEN, null);
            //generate authorization value
            final String uri = BitcasaRESTConstants.API_VERSION_2 + BitcasaRESTConstants.METHOD_OAUTH2 +
                    BitcasaRESTConstants.METHOD_TOKEN;
            final String authorizationValue = this.bitcasaRESTUtility.generateAuthorizationValue(session, uri,
                    parameters, date);

            Log.d(RESTAdapter.TAG, "getAccessToken URL: " + url);
            connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_POST);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setReadTimeout(BitcasaRESTConstants.CONNECTION_TIME_OUT);
            connection.setConnectTimeout(BitcasaRESTConstants.CONNECTION_TIME_OUT);
            connection.setUseCaches(false);
            connection.setRequestProperty(BitcasaRESTConstants.HEADER_CONTENT_TYPE,
                    BitcasaRESTConstants.FORM_URLENCODED);
            connection.setRequestProperty(BitcasaRESTConstants.HEADER_DATE, date);
            connection.setRequestProperty(BitcasaRESTConstants.HEADER_AUTORIZATION,
                    authorizationValue);

            if (parameters != null) {
                outputStream = connection.getOutputStream();
                Log.d(RESTAdapter.TAG, "POST string: " + parameters);
                outputStream.write(parameters.getBytes());
            }

            // read response code
            final int responseCode = connection.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                inputStream = connection.getInputStream();
            } else if ((responseCode == HttpsURLConnection.HTTP_NOT_FOUND) || (responseCode ==
                    HttpsURLConnection.HTTP_BAD_REQUEST)) {
                inputStream = connection.getErrorStream();
            }

            AccessToken accessToken = new AccessToken();
            if (inputStream != null) {
                final String response = this.bitcasaRESTUtility.getResponseFromInputStream(inputStream);
                accessToken = new Gson().fromJson(response, AccessToken.class);
            }
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                if (this.credential != null) {
                    this.credential.setAccessToken(accessToken.getAccessToken());
                }
                if (this.credential != null) {
                    this.credential.setTokenType(accessToken.getTokenType());
                }
            } else {
                throw new BitcasaAuthenticationException(Integer.toString(responseCode));
            }


        } catch (final InvalidKeyException e) {
            throw new BitcasaException(e);
        } catch (final NoSuchAlgorithmException e) {
            throw new BitcasaException(e);
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    //region Account Data

    /**
     * Requests to retrieve account information from the CloudFS server.
     *
     * @return Bitcasa Account object.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public Account requestAccountInfo() throws BitcasaException {
        final String url = this.bitcasaRESTUtility.getRequestUrl(this.credential, BitcasaRESTConstants.METHOD_USER,
                BitcasaRESTConstants.METHOD_PROFILE, null);

        Log.d(RESTAdapter.TAG, "getProfile URL: " + url);
        HttpsURLConnection connection = null;

        Account account = null;
        try {
            connection = (HttpsURLConnection) new URL(url)
                    .openConnection();
            connection.setDoInput(true);
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_GET);
            this.bitcasaRESTUtility.setRequestHeaders(this.credential, connection, null);

            final BitcasaError error = this.bitcasaRESTUtility.checkRequestResponse(connection);

            if (error == null) {

                final String response = this.bitcasaRESTUtility.getResponseFromInputStream(connection.getInputStream());
                long limit = Long.valueOf(connection.getHeaderField("X-BCS-Account-Storage-Limit")).longValue();
                long usage = Long.valueOf(connection.getHeaderField("X-BCS-Account-Storage-Usage")).longValue();

                final BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    final UserProfile userProfile = new Gson().fromJson(bitcasaResponse.getResult(),
                            UserProfile.class);

                    Plan plan = new Plan(userProfile.getAccountPlan().getDisplayName(),
                            userProfile.getAccountId(),
                            limit);

                    Storage storage = new Storage(usage, limit, userProfile.getStorage().getOtl());
                    userProfile.setStorage(storage);
                    account = new Account(this.clone(), userProfile, plan);


                } else {
                    throw new BitcasaException(bitcasaResponse.getError().getCode(),
                            bitcasaResponse.getError().getMessage());
                }
            }

        } catch (final ProtocolException e) {
            throw new BitcasaException(e);
        } catch (final MalformedURLException e) {
            throw new BitcasaException(e);
        } catch (final IOException ioe) {
            throw new BitcasaException(ioe);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return account;
    }

    /**
     * Requests to retrieve user information from the CloudFS server.
     *
     * @return Bitcasa User object.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public User requestUserInfo() throws BitcasaException {
        final String url = this.bitcasaRESTUtility.getRequestUrl(this.credential, BitcasaRESTConstants.METHOD_USER,
                BitcasaRESTConstants.METHOD_PROFILE, null);

        Log.d(RESTAdapter.TAG, "getProfile URL: " + url);
        HttpsURLConnection connection = null;

        User user = null;
        try {
            connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setDoInput(true);
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_GET);
            this.bitcasaRESTUtility.setRequestHeaders(this.credential, connection, null);

            final BitcasaError error = this.bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {

                final String response = this.bitcasaRESTUtility.getResponseFromInputStream(connection.getInputStream());
                final BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    final UserProfile userProfile = new Gson().fromJson(bitcasaResponse.getResult(),
                            UserProfile.class);
                    user = new User(this.clone(), userProfile);
                } else {
                    throw new BitcasaException(bitcasaResponse.getError().getCode(),
                            bitcasaResponse.getError().getMessage());
                }
            }

        } catch (final MalformedURLException e) {
            throw new BitcasaException(e);
        } catch (final ProtocolException e) {
            throw new BitcasaException(e);
        } catch (final IOException ioe) {
            throw new BitcasaException(ioe);
        } catch (final BitcasaException e) {
            throw new BitcasaException(e);
        } catch (final JsonSyntaxException e) {
            throw new BitcasaException(e);
        } catch (final RuntimeException e) {
            throw new BitcasaException(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return user;
    }

    /**
     * Lists all the files and folders under the given folder path.
     *
     * @param folderPath String folder path to get the list.
     * @param version    String version of the folder.
     * @param depth      Integer folder depth to read.
     * @param filter     String filter to be applied when reading the list.
     * @return Item array of files and folders.
     * @throws IOException      If a network error occurs.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public Item[] getList(final String folderPath, final int version, final int depth, final String filter) throws
            IOException, BitcasaException {
        final ArrayList<Item> items = new ArrayList<Item>();
        final StringBuilder endpoint = new StringBuilder();

        endpoint.append(BitcasaRESTConstants.METHOD_FOLDERS);
        if (folderPath != null) {
            endpoint.append(folderPath);
        } else {
            endpoint.append(File.separator);
        }

        final Map<String, String> parameters = new TreeMap<String, String>();
        if (version >= 0) {
            parameters.put(BitcasaRESTConstants.PARAM_VERSION, Integer.toString(version));
        }
        if (depth >= 0) {
            parameters.put(BitcasaRESTConstants.PARAM_DEPTH, Integer.toString(depth));
        }
        if (filter != null) {
            parameters.put(BitcasaRESTConstants.PARAM_FILTER, filter);
        }

        final String url = this.bitcasaRESTUtility.getRequestUrl(this.credential, endpoint.toString(), null,
                (!parameters.isEmpty()) ? parameters : null);

        Log.d(RESTAdapter.TAG, "getList URL: " + url);
        HttpsURLConnection connection = null;
        InputStream inputStream = null;

        try {
            connection = (HttpsURLConnection) new URL(url)
                    .openConnection();
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_GET);
            this.bitcasaRESTUtility.setRequestHeaders(this.credential, connection, null);

            final BitcasaError error = this.bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {
                inputStream = connection.getInputStream();
                final String response = this.bitcasaRESTUtility.getResponseFromInputStream(connection.getInputStream());
                final BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    final ItemList itemList = new Gson().fromJson(bitcasaResponse.getResult(),
                            ItemList.class);

                    for (final ItemMeta meta : itemList.getItems()) {
                        if (meta.isFolder()) {
                            items.add(new Folder(this.clone(), meta, folderPath, BitcasaRESTConstants.ITEM_STATE_NORMAL, null));
                        } else {
                            items.add(new com.bitcasa.cloudfs.File(this.clone(), meta, folderPath, BitcasaRESTConstants.ITEM_STATE_NORMAL, null));
                        }
                    }
                } else {
                    throw new BitcasaException(bitcasaResponse.getError().getCode(),
                            bitcasaResponse.getError().getMessage());
                }
            }

        } catch (final ProtocolException e) {
            throw new BitcasaException(e);
        } catch (final MalformedURLException e) {
            throw new BitcasaException(e);
        } catch (final IOException ioe) {
            if (ioe.getMessage().contains("authentication challenge")) {
                throw new BitcasaAuthenticationException(RESTAdapter.BITCASA_AUTHENTICATION_ERROR_CODE, "Authorization code not recognized");
            } else {
                if (ioe != null) {
                    throw new BitcasaException(ioe);
                }
            }
        } catch (final BitcasaException e) {
            throw new BitcasaException(e);
        } catch (final JsonSyntaxException e) {
            throw new BitcasaException(e);
        } catch (final RuntimeException e) {
            if (e != null) {
                throw new BitcasaException(e);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

        return items.toArray(new Item[items.size()]);
    }

    /**
     * Download a file from CloudFS file system.
     *
     * @param file             Bitcasa Item with valid bitcasa file path and file name.
     * @param range            Any valid content range. No less than 0, no greater than the
     *                         file size.
     * @param localDestination Device file location with file path and name.
     * @param listener         The progress listener to listen to the file download progress.
     * @throws BitcasaException If a CloudFS API error occurs.
     * @throws IOException      If a network error occurs.
     */
    public void downloadFile(final com.bitcasa.cloudfs.File file, final long range, final String localDestination,
                             final BitcasaProgressListener listener) throws BitcasaException,
            IOException {
        if (RESTAdapter.stringParameterNotValid(localDestination)) {
            throw new IllegalArgumentException(RESTAdapter.MISSING_PARAM + "localDestination");
        }

        final File local = new File(localDestination);
        //create file
        local.createNewFile();

        final String url = this.bitcasaRESTUtility.getRequestUrl(this.credential, BitcasaRESTConstants.METHOD_FILES,
                file.getPath(), null);

        final Map<String, String> header = new TreeMap<String, String>();
        header.put(BitcasaRESTConstants.HEADER_RANGE, "bytes=" + range + '-');

        Log.d(RESTAdapter.TAG, "downloadFile url: " + url);
        BufferedOutputStream bufferedOutputStream = null;
        FileOutputStream fileOutputStream = null;
        BufferedInputStream bufferedInputStream = null;
        HttpsURLConnection connection = null;
        try {
            HttpURLConnection.setFollowRedirects(true);
            connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setDoInput(true);
            this.bitcasaRESTUtility.setRequestHeaders(this.credential, connection, header);

            // check response code first
            final BitcasaError error = this.bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {
                // prepare for writing to file
                bufferedInputStream = new BufferedInputStream(connection.getInputStream());
                fileOutputStream = range == 0 ? new FileOutputStream(local) :
                        new FileOutputStream(local, true);    // if file exists append
                bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

                // start writing
                final byte[] data = new byte[RESTAdapter.BUFFERED_INPUTSTREAM_DATA_ARRAY_SIZE];
                int x;
                // use for progress update
                final BigInteger fileSize = new BigInteger(Long.toString(file.getSize()));
                BigInteger dataReceived = BigInteger.valueOf(range);
                long progressUpdateTimer = System.currentTimeMillis();
                while ((x = bufferedInputStream.read(data, 0, RESTAdapter.BUFFERED_INPUTSTREAM_DATA_ARRAY_SIZE)) >= 0) {
                    if (Thread.currentThread().isInterrupted()) {
                        if (listener != null) {
                            listener.canceled(file.getName(),
                                    BitcasaProgressListener.ProgressAction.BITCASA_ACTION_DOWNLOAD);
                            break;
                        }
                    }
                    bufferedOutputStream.write(data, 0, x);
                    dataReceived = dataReceived.add(BigInteger.valueOf(x));
                    // update progress
                    if ((listener != null) && ((System.currentTimeMillis() - progressUpdateTimer) >
                            BitcasaRESTConstants.PROGRESS_UPDATE_INTERVAL)) {
                        final int percentage = dataReceived.multiply(BigInteger.valueOf(100))
                                .divide(fileSize).intValue();
                        listener.onProgressUpdate(file.getName(), percentage,
                                BitcasaProgressListener.ProgressAction.BITCASA_ACTION_DOWNLOAD);
                        progressUpdateTimer = System.currentTimeMillis();
                    }

                    // make sure everything is written to the file so we can compare the size later
                    bufferedOutputStream.flush();
                }

                // make sure that we did download the whole file
                Log.d(RESTAdapter.TAG, "local file size: " + local.length() + ", file size should be: " +
                        fileSize);
            }

        } catch (final FileNotFoundException e) {
            throw new BitcasaException(e);
        } catch (final MalformedURLException e) {
            throw new BitcasaException(e);
        } catch (final IOException ioe) {
            throw new BitcasaException(ioe);
        } finally {

            if (bufferedOutputStream != null) {
                bufferedOutputStream.close();
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            if (bufferedInputStream != null) {
                bufferedInputStream.close();
            }

            if (Thread.interrupted()) {
                local.delete();
            }

            if (connection != null) {
                connection.disconnect();
            }
        }

    }

    /**
     * Creates a redirect url from a given path.
     *
     * @param path The actual file path.
     * @param size The actual size of the file
     * @return The redirect url.
     * @throws IOException If a network error occurs.
     */
    public String downloadUrl(final String path, final long size) throws IOException {

        final String newPath = this.bitcasaRESTUtility.getRequestUrl(this.credential,
                BitcasaRESTConstants.METHOD_FILES,
                path, null);

        final Map<String, String> header = new TreeMap<String, String>();
        header.put(BitcasaRESTConstants.HEADER_RANGE, "bytes=" + size + '-');

        Log.d(RESTAdapter.TAG, "downloadFile url: " + newPath);

        final HttpsURLConnection connection = (HttpsURLConnection) new URL(newPath).openConnection();
        connection.setInstanceFollowRedirects(false);
        HttpURLConnection.setFollowRedirects(false);
        this.bitcasaRESTUtility.setRequestHeaders(this.credential, connection, header);

        boolean redirect = false;

        final int status = connection.getResponseCode();
        if (status != HttpURLConnection.HTTP_OK) {
            if (status == HttpURLConnection.HTTP_MOVED_TEMP) {
                redirect = true;
            }
        }

        String newUrl = null;

        if (redirect) {
            newUrl = connection.getHeaderField("Location");
        }

        return newUrl;
    }

    /**
     * Download a file from CloudFS file system.
     *
     * @param file  Item object of a file.
     * @param range long range of the file to be downloaded.
     * @return InputStream of the downloaded file.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public InputStream download(final Item file, final long range) throws BitcasaException {
        final String url = this.bitcasaRESTUtility.getRequestUrl(this.credential, BitcasaRESTConstants.METHOD_FILES,
                file.getAbsoluteParentPath(), null);

        final Map<String, String> header = new TreeMap<String, String>();
        header.put(BitcasaRESTConstants.HEADER_RANGE, "bytes=" + range + '-');

        Log.d(RESTAdapter.TAG, "download url: " + url);
        HttpsURLConnection connection = null;
        InputStream inputStream = null;
        try {
            connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setDoInput(true);
            this.bitcasaRESTUtility.setRequestHeaders(this.credential, connection, header);

            final BitcasaError error = this.bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {
                inputStream = connection.getInputStream();
            }

        } catch (final MalformedURLException e) {
            throw new BitcasaException(e);
        } catch (final IOException ioe) {
            throw new BitcasaException(ioe);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return inputStream;
    }

    /**
     * Upload a local file to the CloudFS server.
     *
     * @param folder         Item object of the folder.
     * @param sourceFilePath String path of the source file.
     * @param exists         Exists enum which specifies the action to take if the file already
     *                       exists.
     * @param listener       The progress listener to listen to the file upload progress.
     * @return CloudFS file meta data of this successful upload, null if upload failed.
     * @throws IOException      If a network error occurs.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public com.bitcasa.cloudfs.File uploadFile(final Item folder, final String sourceFilePath, final BitcasaRESTConstants.Exists exists,
                                               final BitcasaProgressListener listener)
            throws IOException, BitcasaException {
        if (RESTAdapter.stringParameterNotValid(sourceFilePath)) {
            throw new IllegalArgumentException(RESTAdapter.MISSING_PARAM + "sourceFilePath");
        }

        final File sourceFile = new File(sourceFilePath);
        if (!sourceFile.exists() || !sourceFile.canRead()) {
            throw new BitcasaClientException("Unable to read file: " + sourceFilePath);
        }
        String path = File.separator;
        if ((folder != null) && (folder.getPath() != null && !(folder.getPath().equals(File.separator)))) {
            path = folder.getPath() + File.separator;
        }


        final String urlRequest = this.bitcasaRESTUtility.getRequestUrl(this.credential,
                BitcasaRESTConstants.METHOD_FILES, path, null);
        Log.d(RESTAdapter.TAG, "uploadFile url: " + urlRequest);

        final String fieldValue;

        if (exists == null) {
            fieldValue = BitcasaRESTConstants.EXISTS_FAIL;
        } else {
            switch (exists) {
                case OVERWRITE:
                    fieldValue = BitcasaRESTConstants.EXISTS_OVERWRITE;
                    break;
                case RENAME:
                    fieldValue = BitcasaRESTConstants.EXISTS_RENAME;
                    break;
                case REUSE:
                    fieldValue = BitcasaRESTConstants.EXISTS_REUSE;
                    break;
                case FAIL:
                default:
                    fieldValue = BitcasaRESTConstants.EXISTS_FAIL;
                    break;
            }
        }

        final MultipartUpload mpUpload = new MultipartUpload(this.credential, urlRequest, this.bitcasaRESTUtility);
        mpUpload.addUploadFormField(BitcasaRESTConstants.PARAM_EXISTS, fieldValue);
        mpUpload.addFile(sourceFile, listener);
        return mpUpload.finishUpload(this.clone(), folder.getPath());
    }

    /**
     * Deletes the folder from the CloudFS file system.
     *
     * @param path   Path to the folder to be deleted.
     * @param commit If true, folder is deleted immediately. Otherwise, it is moved to the Trash.
     *               The default is false.
     * @param force  If true, folder is deleted even if it contains sub-items. The default is false.
     * @return Returns true if the folder is deleted successfully, otherwise false.
     * @throws IOException      If response data can not be read due to network errors.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public boolean deleteFolder(final String path, final boolean commit, final boolean force)
            throws IOException, BitcasaException {

        return this.delete(path, Item.FileType.FOLDER, commit, force);
    }

    /**
     * Deletes an existing file from the CloudFS file system.
     *
     * @param path   Path to the file to be deleted.
     * @param commit If true, folder is deleted immediately. Otherwise, it is moved to the Trash.
     *               The default is false.
     * @return Returns true if the file is deleted successfully, otherwise false.
     * @throws IOException      If response data can not be read due to network errors.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public boolean deleteFile(final String path, final boolean commit) throws IOException,
            BitcasaException {
        return this.delete(path, Item.FileType.FILE, commit, false);
    }

    /**
     * Deletes the item from CloudFS at given path.
     *
     * @param path     The path of the item which needs to be deleted.
     * @param itemType The type of the item.
     * @param commit   If true, item is deleted immediately. Otherwise, it is moved to the Trash.
     *                 The default is false.
     * @param force    If true, item is deleted even if it contains sub-items. The default is false.
     * @return Returns true if the item is deleted successfully, otherwise false.
     * @throws IOException      If response data can not be read due to network errors.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    private boolean delete(final String path, final String itemType, final boolean commit, final boolean force)
            throws IOException, BitcasaException {

        final Map<String, String> queryParam = new TreeMap<String, String>();
        queryParam.put(BitcasaRESTConstants.PARAM_COMMIT, commit ?
                BitcasaRESTConstants.PARAM_TRUE : BitcasaRESTConstants.PARAM_FALSE);

        String request = BitcasaRESTConstants.METHOD_FILES;
        if (itemType.equals(Item.FileType.FOLDER)) {
            queryParam.put(BitcasaRESTConstants.PARAM_FORCE, force ? BitcasaRESTConstants.PARAM_TRUE : BitcasaRESTConstants.PARAM_FALSE);

            request = BitcasaRESTConstants.METHOD_FOLDERS;
        }

        final String url = this.bitcasaRESTUtility.getRequestUrl(this.credential, request, path, queryParam);

        Log.d(RESTAdapter.TAG, "delete: URL - " + url);
        HttpsURLConnection connection = null;
        InputStream inputStream = null;

        boolean result = false;
        try {
            connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_DELETE);
            this.bitcasaRESTUtility.setRequestHeaders(this.credential, connection, null);

            final BitcasaError error = this.bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {
                inputStream = connection.getInputStream();
                final String response = this.bitcasaRESTUtility.getResponseFromInputStream(inputStream);
                final BitcasaResponse bitcasaResponse = new Gson().fromJson(response,
                        BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    result = bitcasaResponse.getResult().getAsJsonObject().get("success")
                            .getAsBoolean();
                }
            } else {
                throw new BitcasaException(error);
            }

        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

        return result;
    }

    /**
     * Creates a folder in the CloudFS file system.
     *
     * @param folderName   The name of the folder to be created.
     * @param parentFolder The parent folder under which the new folder is to be created.
     * @param exists       Action to take if the folder to be created already exists.
     * @return An instance of the newly created folder.
     * @throws IOException      If a network error occurs.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public Folder createFolder(final String folderName, final Folder parentFolder, final BitcasaRESTConstants.Exists exists)
            throws IOException, BitcasaException {

        if (RESTAdapter.stringParameterNotValid(folderName)) {
            throw new IllegalArgumentException(RESTAdapter.MISSING_PARAM + "filename");
        }

        final StringBuilder request = new StringBuilder();
        request.append(BitcasaRESTConstants.METHOD_FOLDERS);

        request.append(parentFolder.getPath());

        final Map<String, String> queryParams = new TreeMap<String, String>();
        final Map<String, String> params = new TreeMap<String, String>();

        queryParams.put(BitcasaRESTConstants.PARAM_OPERATION,
                BitcasaRESTConstants.OPERATION_CREATE);
        params.put(BitcasaRESTConstants.BODY_NAME, URLEncoder.encode(folderName,
                BitcasaRESTConstants.UTF_8_ENCODING));

        if (exists != null) {
            switch (exists) {
                case FAIL:
                    params.put(BitcasaRESTConstants.BODY_EXISTS,
                            BitcasaRESTConstants.EXISTS_FAIL);
                    break;
                case OVERWRITE:
                    params.put(BitcasaRESTConstants.BODY_EXISTS,
                            BitcasaRESTConstants.EXISTS_OVERWRITE);
                    break;
                case RENAME:
                default:
                    params.put(BitcasaRESTConstants.BODY_EXISTS,
                            BitcasaRESTConstants.EXISTS_RENAME);
                    break;
            }
        }

        final String body = this.bitcasaRESTUtility.generateParamsString(params);
        final String url = this.bitcasaRESTUtility.getRequestUrl(this.credential, request.toString(), null,
                queryParams);

        HttpsURLConnection connection = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;

        Folder newFolder = null;
        try {
            connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_POST);
            this.bitcasaRESTUtility.setRequestHeaders(this.credential, connection, null);

            if (body != null) {
                connection.setDoOutput(true);
                outputStream = connection.getOutputStream();
                outputStream.write(body.getBytes());
            }

            final BitcasaError error = this.bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {
                inputStream = connection.getInputStream();
                final String response = this.bitcasaRESTUtility.getResponseFromInputStream(inputStream);
                final BitcasaResponse bitcasaResponse = new Gson().fromJson(response,
                        BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    final ItemList items = new Gson().fromJson(bitcasaResponse.getResult(),
                            ItemList.class);
                    if (items.getItems().length > 0) {
                        final ItemMeta meta = items.getItems()[0];
                        newFolder = new Folder(this.clone(), meta, parentFolder.getPath(), BitcasaRESTConstants.ITEM_STATE_NORMAL, null);
                    } else {
                        throw new BitcasaException(0, "Failed to create the folder.");
                    }
                }
            } else {
                throw new BitcasaException(error);
            }
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

        return newFolder;
    }

    /**
     * Copies an item to given destination path.
     *
     * @param item            The item object to be copied.
     * @param destinationPath The destination path which the item should be copied.
     * @param newName         The new name of the item.
     * @param exists          The action to perform if the item already exists at the destination.
     * @return An item which refers to the item at the destination path.
     * @throws IOException      If response data can not be read due to network errors.
     * @throws BitcasaException If the server can not copy the item due to an error.
     */
    public Item copy(final Item item, final String destinationPath, final String newName, final BitcasaRESTConstants.Exists exists)
            throws IOException, BitcasaException {
        if (RESTAdapter.stringParameterNotValid(destinationPath)) {
            throw new IllegalArgumentException(RESTAdapter.MISSING_PARAM + "destinationPath");
        }

        final StringBuilder request = new StringBuilder();
        if (item.getType().equals(Item.FileType.FOLDER)) {
            request.append(BitcasaRESTConstants.METHOD_FOLDERS);
        } else {
            request.append(BitcasaRESTConstants.METHOD_FILES);
        }

        final String parent;
        if ((item != null) && (item.getPath() != null)) {
            parent = item.getPath();
        } else {
            parent = File.separator;
        }
        request.append(parent);

        final Map<String, String> queryParams = new TreeMap<String, String>();
        final Map<String, String> params = new TreeMap<String, String>();

        queryParams.put(BitcasaRESTConstants.PARAM_OPERATION,
                BitcasaRESTConstants.OPERATION_COPY);
        params.put(BitcasaRESTConstants.BODY_TO, URLEncoder.encode(destinationPath,
                BitcasaRESTConstants.UTF_8_ENCODING));
        if ((newName != null) && (!newName.isEmpty())) {
            params.put(BitcasaRESTConstants.BODY_NAME, URLEncoder.encode(newName,
                    BitcasaRESTConstants.UTF_8_ENCODING));
        } else {
            params.put(BitcasaRESTConstants.BODY_NAME, URLEncoder.encode(item.getName(),
                    BitcasaRESTConstants.UTF_8_ENCODING));
        }

        if (exists != null) {
            switch (exists) {
                case FAIL:
                    params.put(BitcasaRESTConstants.BODY_EXISTS, BitcasaRESTConstants.EXISTS_FAIL);
                    break;
                case OVERWRITE:
                    params.put(BitcasaRESTConstants.BODY_EXISTS,
                            BitcasaRESTConstants.EXISTS_OVERWRITE);
                    break;
                case RENAME:
                default:
                    params.put(BitcasaRESTConstants.BODY_EXISTS,
                            BitcasaRESTConstants.EXISTS_RENAME);
                    break;
            }
        }

        final String body = this.bitcasaRESTUtility.generateParamsString(params);

        final String url = this.bitcasaRESTUtility.getRequestUrl(this.credential, request.toString(), null,
                queryParams);
        Log.d(RESTAdapter.TAG, "copy: " + url + " body: " + body);
        HttpsURLConnection connection = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        Item resultItem = null;
        try {
            connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_POST);
            this.bitcasaRESTUtility.setRequestHeaders(this.credential, connection, null);

            if (body != null) {
                connection.setDoOutput(true);
                outputStream = connection.getOutputStream();
                outputStream.write(body.getBytes());
            }

            final BitcasaError error = this.bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {
                inputStream = connection.getInputStream();
                final String response = this.bitcasaRESTUtility.getResponseFromInputStream(inputStream);
                final BitcasaResponse bitcasaResponse = new Gson().fromJson(response,
                        BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    final ItemList itemList = new Gson().fromJson(bitcasaResponse.getResult(),
                            ItemList.class);
                    final ItemMeta meta = itemList.getMeta();
                    if (meta != null) {
                        if (meta.isFolder()) {
                            resultItem = new Folder(this.clone(), meta, destinationPath, BitcasaRESTConstants.ITEM_STATE_NORMAL, null);
                        } else {
                            resultItem = new com.bitcasa.cloudfs.File(this.clone(), meta, destinationPath, BitcasaRESTConstants.ITEM_STATE_NORMAL, null);
                        }
                    } else {
                        throw new BitcasaException(0, "Failed to copy the item.");
                    }
                }
            } else {
                throw new BitcasaException(error);
            }

        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

        return resultItem;
    }

    /**
     * Moves an item to given destination path.
     *
     * @param item            The item object to be moved.
     * @param destinationPath The destination path which the item should be moved.
     * @param newName         The new name of the item.
     * @param exists          The action to perform if the item already exists at the destination.
     * @return An item which refers to the item at the destination path.
     * @throws IOException      If response data can not be read.
     * @throws BitcasaException If the server can not move the item due to an error.
     */
    public Item move(final Item item, final String destinationPath, final String newName,
                     final BitcasaRESTConstants.Exists exists)
            throws IOException, BitcasaException {

        if (RESTAdapter.stringParameterNotValid(destinationPath)) {
            throw new IllegalArgumentException(RESTAdapter.MISSING_PARAM + "destinationPath");
        }

        final StringBuilder request = new StringBuilder();
        if (item.getType().equals(Item.FileType.FOLDER)) {
            request.append(BitcasaRESTConstants.METHOD_FOLDERS);
        } else {
            request.append(BitcasaRESTConstants.METHOD_FILES);
        }

        final String parent;
        if ((item != null) && (item.getPath() != null)) {
            parent = item.getPath();
        } else {
            parent = File.separator;
        }
        request.append(parent);

        final Map<String, String> queryParams = new TreeMap<String, String>();
        final Map<String, String> params = new TreeMap<String, String>();

        queryParams.put(BitcasaRESTConstants.PARAM_OPERATION,
                BitcasaRESTConstants.OPERATION_MOVE);
        params.put(BitcasaRESTConstants.BODY_TO, URLEncoder.encode(destinationPath,
                BitcasaRESTConstants.UTF_8_ENCODING));
        if ((newName != null) && (!newName.isEmpty())) {
            params.put(BitcasaRESTConstants.BODY_NAME, URLEncoder.encode(newName,
                    BitcasaRESTConstants.UTF_8_ENCODING));
        } else {
            params.put(BitcasaRESTConstants.BODY_NAME, URLEncoder.encode(item.getName(),
                    BitcasaRESTConstants.UTF_8_ENCODING));
        }

        if (exists != null) {
            switch (exists) {
                case FAIL:
                    params.put(BitcasaRESTConstants.BODY_EXISTS, BitcasaRESTConstants.EXISTS_FAIL);
                    break;
                case OVERWRITE:
                    params.put(BitcasaRESTConstants.BODY_EXISTS,
                            BitcasaRESTConstants.EXISTS_OVERWRITE);
                    break;
                case RENAME:
                default:
                    params.put(BitcasaRESTConstants.BODY_EXISTS,
                            BitcasaRESTConstants.EXISTS_RENAME);
                    break;
            }
        }

        final String body = this.bitcasaRESTUtility.generateParamsString(params);

        final String url = this.bitcasaRESTUtility.getRequestUrl(this.credential, request.toString(), null,
                queryParams);
        Log.d(RESTAdapter.TAG, "move: " + url + " body: " + body);
        HttpsURLConnection connection = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        Item resultItem = null;
        try {
            connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_POST);
            this.bitcasaRESTUtility.setRequestHeaders(this.credential, connection, null);

            if (body != null) {
                connection.setDoOutput(true);
                outputStream = connection.getOutputStream();
                outputStream.write(body.getBytes());
            }

            final BitcasaError error = this.bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {
                inputStream = connection.getInputStream();
                final String response = this.bitcasaRESTUtility.getResponseFromInputStream(inputStream);
                final BitcasaResponse bitcasaResponse = new Gson().fromJson(response,
                        BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    final ItemList itemList = new Gson().fromJson(bitcasaResponse.getResult(),
                            ItemList.class);
                    final ItemMeta meta = itemList.getMeta();
                    if (meta != null) {
                        if (meta.isFolder()) {
                            resultItem = new Folder(this.clone(), meta, destinationPath, BitcasaRESTConstants.ITEM_STATE_NORMAL, null);
                        } else {
                            resultItem = new com.bitcasa.cloudfs.File(this.clone(), meta, destinationPath, BitcasaRESTConstants.ITEM_STATE_NORMAL, null);
                        }
                    } else {
                        throw new BitcasaException(0, "Failed to move the item.");
                    }
                }
            } else {
                throw new BitcasaException(error);
            }

        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

        return resultItem;
    }


    /**
     * Gets the Meta data of the folder.
     *
     * @param absolutePath Location of the folder whose meta data is to be obtained.
     * @return Folder object at the given path.
     * @throws BitcasaAuthenticationException If user not authenticated
     * @throws BitcasaException               If a CloudFS API error occurs.
     */
    public Folder getFolderMeta(final String absolutePath) throws
            BitcasaException {
        final StringBuilder endpoint = new StringBuilder();
        endpoint.append(BitcasaRESTConstants.METHOD_FOLDERS);

        endpoint.append(absolutePath);
        final String url = this.bitcasaRESTUtility.getRequestUrl(this.credential, endpoint.toString(),
                BitcasaRESTConstants.METHOD_META, null);

        Log.d(RESTAdapter.TAG, "getMeta URL: " + url);
        HttpsURLConnection connection = null;

        Folder meta = null;
        try {
            connection = (HttpsURLConnection) new URL(url)
                    .openConnection();
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_GET);
            this.bitcasaRESTUtility.setRequestHeaders(this.credential, connection, null);
            final BitcasaError error = this.bitcasaRESTUtility.checkRequestResponse(connection);

            if (error == null) {
                final String response = this.bitcasaRESTUtility.getResponseFromInputStream(connection.getInputStream());
                final BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    final ItemList itemList = new Gson().fromJson(bitcasaResponse.getResult(),
                            ItemList.class);
                    String absoluteParentPath = absolutePath.equals("/") ? absolutePath : absolutePath.substring(0, absolutePath.lastIndexOf('/'));
                    meta = new Folder(this.clone(), itemList.getMeta(), absoluteParentPath, BitcasaRESTConstants.ITEM_STATE_NORMAL, null);
                } else {
                    throw new BitcasaException(bitcasaResponse.getError().getCode(),
                            bitcasaResponse.getError().getMessage());
                }
            }

        } catch (final ProtocolException e) {
            throw new BitcasaException(e);
        } catch (final MalformedURLException e) {
            throw new BitcasaException(e);
        } catch (final IOException ioe) {
            if (ioe.getMessage().contains("authentication challenge")) {
                throw new BitcasaAuthenticationException(RESTAdapter.BITCASA_AUTHENTICATION_ERROR_CODE, "Authorization code not recognized");
            } else {
                throw new BitcasaException(ioe);
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }


        return meta;
    }

    /**
     * Gets the meta data of an item.
     *
     * @param absolutePath Location of the item whose meta data is to be obtained.
     * @return Item object at the given path.
     * @throws BitcasaAuthenticationException If user not authenticated.
     * @throws BitcasaException               If a CloudFS API error occurs.
     */
    public Item getItemMeta(final String absolutePath) throws
            BitcasaException {
        final StringBuilder endpoint = new StringBuilder();
        endpoint.append(BitcasaRESTConstants.METHOD_ITEM);
        endpoint.append(absolutePath);
        final String url = this.bitcasaRESTUtility.getRequestUrl(this.credential, endpoint.toString(),
                BitcasaRESTConstants.METHOD_META, null);

        Log.d(RESTAdapter.TAG, "getMeta URL: " + url);
        HttpsURLConnection connection = null;

        Item meta = null;
        try {
            connection = (HttpsURLConnection) new URL(url)
                    .openConnection();
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_GET);
            this.bitcasaRESTUtility.setRequestHeaders(this.credential, connection, null);
            final BitcasaError error = this.bitcasaRESTUtility.checkRequestResponse(connection);

            if (error == null) {
                final String response = this.bitcasaRESTUtility.getResponseFromInputStream(connection.getInputStream());
                final BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    final ItemMeta itemMeta = new Gson().fromJson(bitcasaResponse.getResult(),
                            ItemMeta.class);
                    String absoluteParentPath = absolutePath.equals("/") ? absolutePath : absolutePath.substring(0, absolutePath.lastIndexOf('/'));
                    if (itemMeta.isFolder()) {
                        meta = new Folder(this.clone(), itemMeta, absoluteParentPath, BitcasaRESTConstants.ITEM_STATE_NORMAL, null);
                    } else {
                        meta = new com.bitcasa.cloudfs.File(this.clone(), itemMeta, absoluteParentPath, BitcasaRESTConstants.ITEM_STATE_NORMAL, null);
                    }

                } else {
                    throw new BitcasaException(bitcasaResponse.getError().getCode(),
                            bitcasaResponse.getError().getMessage());
                }
            }

        } catch (final ProtocolException e) {
            throw new BitcasaException(e);
        } catch (final MalformedURLException e) {
            throw new BitcasaException(e);
        } catch (final IOException ioe) {
            if (ioe.getMessage().contains("authentication challenge")) {
                throw new BitcasaAuthenticationException(RESTAdapter.BITCASA_AUTHENTICATION_ERROR_CODE, "Authorization code not recognized");
            } else {
                throw new BitcasaException(ioe);
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return meta;
    }

    /**
     * Changes the specified item's meta data.
     *
     * @param meta          Item object to be changed.
     * @param changes       Meta data to be changed.
     * @param version       Version of the item to be changed.
     * @param versionExists The action to perform if the version exists at the destination.
     * @return Item object with altered meta data.
     * @throws BitcasaAuthenticationException If user not authenticated.
     * @throws BitcasaException               If a CloudFS API error occurs.
     */
    public Item alterMeta(final Item meta, final Map<String, String> changes, final int version,
                          final BitcasaRESTConstants.VersionExists versionExists) throws BitcasaException {
        if (RESTAdapter.objectParameterNotValid(changes)) {
            throw new IllegalArgumentException(RESTAdapter.MISSING_PARAM + "changes");
        }

        final StringBuilder endpoint = new StringBuilder();
        if (meta.getType().equals(Item.FileType.FOLDER)) {
            endpoint.append(BitcasaRESTConstants.METHOD_FOLDERS);
        } else if (meta.getType().equals(Item.FileType.FILE)) {
            endpoint.append(BitcasaRESTConstants.METHOD_FILES);
        }

        endpoint.append(meta.getPath());
        final String url = this.bitcasaRESTUtility.getRequestUrl(this.credential, endpoint.toString(),
                BitcasaRESTConstants.METHOD_META, null);

        String versionConflict = BitcasaRESTConstants.VERSION_FAIL;
        if ((versionExists != null) && (versionExists == BitcasaRESTConstants.VersionExists.IGNORE)) {
            versionConflict = BitcasaRESTConstants.VERSION_IGNORE;
        }

        final Map<String, String> formParams = new TreeMap<String, String>();
        formParams.put(BitcasaRESTConstants.PARAM_VERSION, Integer.toString(version));
        if (versionExists != null) {
            formParams.put(BitcasaRESTConstants.PARAM_VERSION_CONFLICT, versionConflict);
        }

        formParams.putAll(changes);
        Log.d(RESTAdapter.TAG, "alterMeta URL: " + url);
        HttpsURLConnection connection = null;

        Item item = null;
        try {
            connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_POST);
            this.bitcasaRESTUtility.setRequestHeaders(this.credential, connection, null);
            connection.setDoOutput(true);

            final OutputStream outputStream = connection.getOutputStream();
            outputStream.write(this.bitcasaRESTUtility.generateParamsString(formParams).getBytes());
            final BitcasaError error = this.bitcasaRESTUtility.checkRequestResponse(connection);

            if (error == null) {
                final String response = this.bitcasaRESTUtility.getResponseFromInputStream(connection.getInputStream());
                final BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    final ItemList itemList = new Gson().fromJson(bitcasaResponse.getResult(),
                            ItemList.class);
                    final ItemMeta itemMeta = itemList.getMeta();

                    if (itemMeta.isFolder()) {
                        item = new Folder(this.clone(), itemMeta, meta.getAbsoluteParentPath(), BitcasaRESTConstants.ITEM_STATE_NORMAL, null);
                    } else {
                        item = new com.bitcasa.cloudfs.File(this.clone(), itemMeta, meta.getAbsoluteParentPath(), BitcasaRESTConstants.ITEM_STATE_NORMAL, null);
                    }

                } else {
                    throw new BitcasaException(bitcasaResponse.getError().getCode(),
                            bitcasaResponse.getError().getMessage());
                }
            }

        } catch (final ProtocolException e) {
            throw new BitcasaException(e);
        } catch (final MalformedURLException e) {
            throw new BitcasaException(e);
        } catch (final IOException ioe) {
            if (ioe.getMessage().contains("authentication challenge")) {
                throw new BitcasaAuthenticationException(RESTAdapter.BITCASA_AUTHENTICATION_ERROR_CODE, "Authorization code not recognized");
            } else {
                throw new BitcasaException(ioe);
            }
        } catch (final BitcasaException e) {
            throw new BitcasaException(e);
        } catch (final JsonSyntaxException e) {
            throw new BitcasaException(e);
        } catch (final RuntimeException e) {
            throw new BitcasaException(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return item;
    }

    /**
     * Changes the specified file's meta data.
     *
     * @param meta          Item object to be changed.
     * @param changes       Meta data to be changed.
     * @param version       Version of the item to be changed.
     * @param versionExists The action to perform if the version exists at the destination.
     * @return File object with altered meta data.
     * @throws BitcasaAuthenticationException If user not authenticated.
     * @throws BitcasaException               If a CloudFS API error occurs.
     */
    public com.bitcasa.cloudfs.File alterFileMeta(final Item meta, final Map<String, String> changes, final int version,
                                                  final BitcasaRESTConstants.VersionExists versionExists) throws BitcasaException {
        if (RESTAdapter.objectParameterNotValid(changes)) {
            throw new IllegalArgumentException(RESTAdapter.MISSING_PARAM + "changes");
        }

        final StringBuilder endpoint = new StringBuilder();
        endpoint.append(BitcasaRESTConstants.METHOD_FILES);
        endpoint.append(meta.getPath());
        final String url = this.bitcasaRESTUtility.getRequestUrl(this.credential, endpoint.toString(),
                BitcasaRESTConstants.METHOD_META, null);

        String versionConflict = BitcasaRESTConstants.VERSION_FAIL;
        if ((versionExists != null) && (versionExists == BitcasaRESTConstants.VersionExists.IGNORE)) {
            versionConflict = BitcasaRESTConstants.VERSION_IGNORE;
        }

        final Map<String, String> formParams = new TreeMap<String, String>();
        formParams.put(BitcasaRESTConstants.PARAM_VERSION, Integer.toString(version));
        if (versionExists != null) {
            formParams.put(BitcasaRESTConstants.PARAM_VERSION_CONFLICT, versionConflict);
        }

        formParams.putAll(changes);
        Log.d(RESTAdapter.TAG, "alterFileMeta URL: " + url);
        HttpsURLConnection connection = null;

        com.bitcasa.cloudfs.File file = null;
        try {
            connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_POST);
            this.bitcasaRESTUtility.setRequestHeaders(this.credential, connection, null);
            connection.setDoOutput(true);

            final OutputStream outputStream = connection.getOutputStream();
            outputStream.write(this.bitcasaRESTUtility.generateParamsString(formParams).getBytes());
            final BitcasaError error = this.bitcasaRESTUtility.checkRequestResponse(connection);

            if (error == null) {
                final String response = this.bitcasaRESTUtility.getResponseFromInputStream(connection.getInputStream());
                final BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    final ItemMeta itemMeta = new Gson().fromJson(bitcasaResponse.getResult(),
                            ItemMeta.class);
                    file = new com.bitcasa.cloudfs.File(this.clone(), itemMeta, meta.getAbsoluteParentPath(), BitcasaRESTConstants.ITEM_STATE_NORMAL, null);

                } else {
                    throw new BitcasaException(bitcasaResponse.getError().getCode(),
                            bitcasaResponse.getError().getMessage());
                }
            }

        } catch (final ProtocolException e) {
            throw new BitcasaException(e);
        } catch (final MalformedURLException e) {
            throw new BitcasaException(e);
        } catch (final IOException ioe) {
            if (ioe.getMessage().contains("authentication challenge")) {
                throw new BitcasaAuthenticationException(RESTAdapter.BITCASA_AUTHENTICATION_ERROR_CODE, "Authorization code not recognized");
            } else {
                throw new BitcasaException(ioe);
            }
        } catch (final BitcasaException e) {
            throw new BitcasaException(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return file;
    }

    /**
     * Changes the specified folder's meta data.
     *
     * @param meta          Item object to be changed.
     * @param changes       Meta data to be changed.
     * @param version       Version of the item to be changed.
     * @param versionExists The action to perform if the version exists at the destination.
     * @return Folder object with altered meta
     * @throws BitcasaAuthenticationException If user not authenticated.
     * @throws BitcasaException               If a CloudFS API error occurs.
     */
    public Folder alterFolderMeta(final Item meta, final Map<String, String> changes, final int version,
                                  final BitcasaRESTConstants.VersionExists versionExists) throws BitcasaException {
        if (RESTAdapter.objectParameterNotValid(changes)) {
            throw new IllegalArgumentException(RESTAdapter.MISSING_PARAM + "changes");
        }

        final StringBuilder endpoint = new StringBuilder();
        endpoint.append(BitcasaRESTConstants.METHOD_FOLDERS);
        endpoint.append(meta.getPath());
        final String url = this.bitcasaRESTUtility.getRequestUrl(this.credential, endpoint.toString(),
                BitcasaRESTConstants.METHOD_META, null);

        String versionConflict = BitcasaRESTConstants.VERSION_FAIL;
        if ((versionExists != null) && (versionExists == BitcasaRESTConstants.VersionExists.IGNORE)) {
            versionConflict = BitcasaRESTConstants.VERSION_IGNORE;
        }

        final Map<String, String> formParams = new TreeMap<String, String>();
        formParams.put(BitcasaRESTConstants.PARAM_VERSION, Integer.toString(version));
        if (versionExists != null) {
            formParams.put(BitcasaRESTConstants.PARAM_VERSION_CONFLICT, versionConflict);
        }

        formParams.putAll(changes);
        Log.d(RESTAdapter.TAG, "alterFolderMeta URL: " + url);
        HttpsURLConnection connection = null;

        Folder folder = null;
        try {
            connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_POST);
            this.bitcasaRESTUtility.setRequestHeaders(this.credential, connection, null);
            connection.setDoOutput(true);

            final OutputStream outputStream = connection.getOutputStream();
            outputStream.write(this.bitcasaRESTUtility.generateParamsString(formParams).getBytes());
            final BitcasaError error = this.bitcasaRESTUtility.checkRequestResponse(connection);

            if (error == null) {
                final String response = this.bitcasaRESTUtility.getResponseFromInputStream(connection.getInputStream());
                final BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    final ItemList itemList = new Gson().fromJson(bitcasaResponse.getResult(),
                            ItemList.class);
                    final ItemMeta itemMeta = itemList.getMeta();

                    folder = new Folder(this.clone(), itemMeta, meta.getAbsoluteParentPath(), BitcasaRESTConstants.ITEM_STATE_NORMAL, null);

                } else {
                    throw new BitcasaException(bitcasaResponse.getError().getCode(),
                            bitcasaResponse.getError().getMessage());
                }
            }

        } catch (final ProtocolException e) {
            throw new BitcasaException(e);
        } catch (final MalformedURLException e) {
            throw new BitcasaException(e);
        } catch (final IOException ioe) {
            if (ioe.getMessage().contains("authentication challenge")) {
                throw new BitcasaAuthenticationException(RESTAdapter.BITCASA_AUTHENTICATION_ERROR_CODE, "Authorization code not recognized");
            } else {
                throw new BitcasaException(ioe);
            }
        } catch (final BitcasaException e) {
            throw new BitcasaException(e);
        } catch (final JsonSyntaxException e) {
            throw new BitcasaException(e);
        } catch (final RuntimeException e) {
            throw new BitcasaException(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return folder;
    }

    /**
     * List the versions of specified file.
     *
     * @param path         File path of the file whose versions are to be listed.
     * @param startVersion Start version of the version list.
     * @param stopVersion  End version of the version list.
     * @param limit        Limits the number of versions to be listed down in results.
     * @return A list of file meta data results, as they have been recorded in the file version
     * history after successful meta data changes.
     * @throws IOException      If a network error occurs.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public com.bitcasa.cloudfs.File[] listFileVersions(final String path,
                                                       final int startVersion,
                                                       final int stopVersion,
                                                       final int limit)
            throws BitcasaException, IOException {

        final Map<String, String> queryParams = new TreeMap<String, String>();
        if (startVersion >= 0) {
            queryParams.put(URLEncoder.encode(BitcasaRESTConstants.START_VERSION,
                    BitcasaRESTConstants.UTF_8_ENCODING), Integer.toString(startVersion));
        }
        if (stopVersion >= 0) {
            queryParams.put(URLEncoder.encode(BitcasaRESTConstants.STOP_VERSION,
                    BitcasaRESTConstants.UTF_8_ENCODING), Integer.toString(stopVersion));
        }
        if (limit >= 0) {
            queryParams.put(URLEncoder.encode(BitcasaRESTConstants.LIMIT,
                    BitcasaRESTConstants.UTF_8_ENCODING), Integer.toString(limit));
        }

        final String requestSegment = path + BitcasaRESTConstants.PARAM_VERSIONS;
        final String url = this.bitcasaRESTUtility.getRequestUrl(this.credential, BitcasaRESTConstants.METHOD_FILES,
                requestSegment, (!queryParams.isEmpty()) ? queryParams : null);

        Log.d(RESTAdapter.TAG, "listFileVersions: " + url);
        HttpsURLConnection connection = null;

        com.bitcasa.cloudfs.File[] versions = null;
        try {
            connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_GET);
            this.bitcasaRESTUtility.setRequestHeaders(this.credential, connection, null);
            final BitcasaError error = this.bitcasaRESTUtility.checkRequestResponse(connection);

            if (error == null) {

                final String response = this.bitcasaRESTUtility.getResponseFromInputStream(connection.getInputStream());
                final BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    versions = new Gson().fromJson(bitcasaResponse.getResult(),
                            com.bitcasa.cloudfs.File[].class);

                } else {
                    throw new BitcasaException(bitcasaResponse.getError().getCode(),
                            bitcasaResponse.getError().getMessage());
                }
            }
        } catch (final ProtocolException e) {
            throw new BitcasaException(e);
        } catch (final MalformedURLException e) {
            throw new BitcasaException(e);
        } catch (final IOException ioe) {
            if (ioe.getMessage().contains("authentication challenge")) {
                throw new BitcasaAuthenticationException(RESTAdapter.BITCASA_AUTHENTICATION_ERROR_CODE, "Authorization code not recognized");
            } else {
                throw new BitcasaException(ioe);
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return versions;
    }

    /**
     * List the action history associated with current account.
     *
     * @param startVersion Version to start the list from.
     * @param stopVersion  Version to end the list.
     * @return ActionHistory object containing actions associated with current account.
     * @throws IOException      If a network error occurs.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public List<BaseAction> listHistory(final int startVersion, final int stopVersion)
            throws IOException, BitcasaException {
        final List<BaseAction> actions = new ArrayList<BaseAction>();
        final Map<String, String> queryParams = new HashMap<String, String>();
        if (startVersion >= 0) {
            queryParams.put(BitcasaRESTConstants.PARAM_START, Integer.toString(startVersion));
        }
        if (stopVersion >= 0) {
            queryParams.put(BitcasaRESTConstants.PARAM_STOP, Integer.toString(stopVersion));
        }

        final String url = this.bitcasaRESTUtility.getRequestUrl(this.credential,
                BitcasaRESTConstants.METHOD_HISTORY, null, (!queryParams.isEmpty()) ? queryParams : null);

        Log.d(RESTAdapter.TAG, "listHistory URL: " + url);
        HttpsURLConnection connection = null;
        InputStream inputStream = null;

        try {
            connection = (HttpsURLConnection) new URL(url)
                    .openConnection();
            connection.setDoInput(true);
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_GET);
            this.bitcasaRESTUtility.setRequestHeaders(this.credential, connection, null);

            final BitcasaError error = this.bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {
                inputStream = connection.getInputStream();
                final String response = this.bitcasaRESTUtility.getResponseFromInputStream(connection.getInputStream());
                final BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);
                if (!bitcasaResponse.getResult().isJsonNull()) {
                    final List<JsonObject> results = new ArrayList<JsonObject>();
                    for (int i = 0; i < bitcasaResponse.getResult().getAsJsonArray().size(); i++) {
                        results.add(bitcasaResponse.getResult().getAsJsonArray().get(i).getAsJsonObject());
                    }

                    final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                    for (final JsonObject result : results) {
                        final BaseAction action = gson.fromJson(result, BaseAction.class);
                        if (Arrays.asList(BitcasaRESTConstants.DEFAULT_ACTIONS).contains(action.getActionString().toLowerCase())) {
                            action.setData(new ActionData(new Gson().fromJson(result.get(BitcasaRESTConstants.ATTRIBUTE_DATA), ActionDataDefault.class)));
                        } else if (Arrays.asList(BitcasaRESTConstants.ALTER_ACTIONS).contains(action.getActionString().toLowerCase())) {
                            action.setData(new ActionData(new Gson().fromJson(result.get(BitcasaRESTConstants.ATTRIBUTE_DATA), ActionDataAlter.class)));
                        }
                        actions.add(action);
                    }

                } else {
                    throw new BitcasaException(bitcasaResponse.getError().getCode(),
                            bitcasaResponse.getError().getMessage());
                }
            }
        } catch (final ProtocolException e) {
            throw new BitcasaException(e);
        } catch (final MalformedURLException e) {
            throw new BitcasaException(e);
        } catch (final IOException ioe) {
            throw new BitcasaException(ioe);
        } catch (final BitcasaException e) {
            throw new BitcasaException(e);
        } catch (final JsonSyntaxException e) {
            throw new BitcasaException(e);
        } catch (final RuntimeException e) {
            throw new BitcasaException(e);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

        return actions;
    }

    /**
     * Lists the shares the user has created.
     *
     * @return List of shares associated with current account.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public Share[] listShare() throws BitcasaException {
        final ArrayList<Share> listShares = new ArrayList<Share>();
        final String url = this.bitcasaRESTUtility.getRequestUrl(this.credential,
                BitcasaRESTConstants.METHOD_SHARES, null, null);

        Log.d(RESTAdapter.TAG, "listShare URL: " + url);
        HttpsURLConnection connection = null;

        try {
            connection = (HttpsURLConnection) new URL(url)
                    .openConnection();
            connection.setDoInput(true);
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_GET);
            this.bitcasaRESTUtility.setRequestHeaders(this.credential, connection, null);

            final BitcasaError error = this.bitcasaRESTUtility.checkRequestResponse(connection);

            if (error == null) {
                final String response = this.bitcasaRESTUtility.getResponseFromInputStream(connection.getInputStream());
                final BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    final ShareItem[] shareItem = new Gson().fromJson(bitcasaResponse.getResult(),
                            ShareItem[].class);

                    for (final ShareItem item : shareItem) {
                        listShares.add(new Share(this.clone(), item, null));
                    }

                } else {
                    throw new BitcasaException(bitcasaResponse.getError().getCode(),
                            bitcasaResponse.getError().getMessage());
                }
            }

        } catch (final ProtocolException e) {
            throw new BitcasaException(e);
        } catch (final MalformedURLException e) {
            throw new BitcasaException(e);
        } catch (final IOException ioe) {
            throw new BitcasaException(ioe);
        } catch (final BitcasaException e) {
            throw new BitcasaException(e);
        } catch (final JsonSyntaxException e) {
            throw new BitcasaException(e);
        } catch (final RuntimeException e) {
            throw new BitcasaException(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return listShares.toArray(new Share[listShares.size()]);
    }

    /**
     * Creates a share including the item in the path specified.
     *
     * @param path     Path to the item to be shared.
     * @param password Password to access the share to be created.
     * @return The created share object.
     * @throws IOException      If a network error occurs
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public Share createShare(final String path, final String password) throws IOException, BitcasaException {
        if (RESTAdapter.stringParameterNotValid(path)) {
            throw new IllegalArgumentException(RESTAdapter.MISSING_PARAM + "path");
        }
        final String url = this.bitcasaRESTUtility.getRequestUrl(this.credential,
                BitcasaRESTConstants.METHOD_SHARES, null, null);

        Log.d(RESTAdapter.TAG, "createShare URL: " + url);
        final Map<String, String> formParams = new TreeMap<String, String>();
        formParams.put(BitcasaRESTConstants.BODY_PATH, URLEncoder.encode(path,
                BitcasaRESTConstants.UTF_8_ENCODING));
        if (password != null) {
            formParams.put(BitcasaRESTConstants.PARAM_PASSWORD, URLEncoder.encode(password,
                    BitcasaRESTConstants.UTF_8_ENCODING));
        }

        OutputStream outputStream = null;
        InputStream inputStream = null;
        HttpsURLConnection connection = null;
        Share share = null;
        try {
            connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_POST);
            connection.setReadTimeout(BitcasaRESTConstants.CONNECTION_TIME_OUT);
            connection.setConnectTimeout(BitcasaRESTConstants.CONNECTION_TIME_OUT);
            connection.setUseCaches(false);
            this.bitcasaRESTUtility.setRequestHeaders(this.credential, connection, null);
            final String parameters = this.bitcasaRESTUtility.generateParamsString(formParams);
            if (parameters != null) {
                outputStream = connection.getOutputStream();
                Log.d(RESTAdapter.TAG, "POST string: " + parameters);
                outputStream.write(parameters.getBytes());
            }

            final BitcasaError error = this.bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {
                inputStream = connection.getInputStream();
                final String response = this.bitcasaRESTUtility.getResponseFromInputStream(connection.getInputStream());
                final BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    final ShareItem shareItem = new Gson().fromJson(bitcasaResponse.getResult(),
                            ShareItem.class);
                    share = new Share(this.clone(), shareItem, null);
                } else {
                    throw new BitcasaException(bitcasaResponse.getError().getCode(),
                            bitcasaResponse.getError().getMessage());
                }
            }

        } catch (final ProtocolException e) {
            throw new BitcasaException(e);
        } catch (final MalformedURLException e) {
            throw new BitcasaException(e);
        } catch (final IOException ioe) {
            throw new BitcasaException(ioe);
        } catch (final BitcasaException e) {
            throw new BitcasaException(e);
        } catch (final JsonSyntaxException e) {
            throw new BitcasaException(e);
        } catch (final RuntimeException e) {
            throw new BitcasaException(e);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return share;
    }

    /**
     * Creates a share including the item in the path specified.
     *
     * @param paths    Paths to the items to be shared.
     * @param password Password to access the share to be created.
     * @return The created share object.
     * @throws IOException      If a network error occurs
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public Share createShare(final String[] paths, final String password) throws IOException, BitcasaException {
        if (paths.length == 0 || paths == null) {
            throw new IllegalArgumentException(RESTAdapter.MISSING_PARAM + "path");
        }
        final String url = this.bitcasaRESTUtility.getRequestUrl(this.credential,
                BitcasaRESTConstants.METHOD_SHARES, null, null);

        Log.d(RESTAdapter.TAG, "createShare URL: " + url);
        final Map<String, Object> formParams = new TreeMap<String, Object>();
        String[] pathValue = new String[paths.length];
        int count = 0;
        for (String path : paths) {
            pathValue[count] = URLEncoder.encode(path, BitcasaRESTConstants.UTF_8_ENCODING);
            count++;

        }

        formParams.put(BitcasaRESTConstants.BODY_PATH, pathValue);

        if (password != null) {
            formParams.put(BitcasaRESTConstants.PARAM_PASSWORD, URLEncoder.encode(password,
                    BitcasaRESTConstants.UTF_8_ENCODING));
        }

        OutputStream outputStream = null;
        InputStream inputStream = null;
        HttpsURLConnection connection = null;
        Share share = null;
        try {
            connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_POST);
            connection.setReadTimeout(BitcasaRESTConstants.CONNECTION_TIME_OUT);
            connection.setConnectTimeout(BitcasaRESTConstants.CONNECTION_TIME_OUT);
            connection.setUseCaches(false);
            this.bitcasaRESTUtility.setRequestHeaders(this.credential, connection, null);
            final String parameters = this.bitcasaRESTUtility.generateParamsString(formParams);
            if (parameters != null) {
                outputStream = connection.getOutputStream();
                Log.d(RESTAdapter.TAG, "POST string: " + parameters);
                outputStream.write(parameters.getBytes());
            }

            final BitcasaError error = this.bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {
                inputStream = connection.getInputStream();
                final String response = this.bitcasaRESTUtility.getResponseFromInputStream(connection.getInputStream());
                final BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    final ShareItem shareItem = new Gson().fromJson(bitcasaResponse.getResult(),
                            ShareItem.class);
                    share = new Share(this.clone(), shareItem, null);
                } else {
                    throw new BitcasaException(bitcasaResponse.getError().getCode(),
                            bitcasaResponse.getError().getMessage());
                }
            }

        } catch (final ProtocolException e) {
            throw new BitcasaException(e);
        } catch (final MalformedURLException e) {
            throw new BitcasaException(e);
        } catch (final IOException ioe) {
            throw new BitcasaException(ioe);
        } catch (final BitcasaException e) {
            throw new BitcasaException(e);
        } catch (final JsonSyntaxException e) {
            throw new BitcasaException(e);
        } catch (final RuntimeException e) {
            throw new BitcasaException(e);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return share;
    }

    /**
     * Given the shareKey and the path to any folder/file under share, browseShare method will
     * return the item list for that share.
     * Make sure unlockShare is called before browseShare
     *
     * @param shareKey The shareKey of the share to be browsed.
     * @param path     Path to be browsed.
     * @return Items list found at given share path.
     * @throws IOException      If a network error occurs
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public Item[] browseShare(final String shareKey, final String path) throws IOException, BitcasaException {
        if (RESTAdapter.stringParameterNotValid(shareKey)) {
            throw new IllegalArgumentException(RESTAdapter.MISSING_PARAM + "shareKey");
        }
        final ArrayList<Item> items = new ArrayList<Item>();

        final StringBuilder sb = new StringBuilder();
        sb.append(shareKey);
        if (path != null) {
            sb.append(path);
        }
        sb.append(BitcasaRESTConstants.METHOD_META);
        final String url = this.bitcasaRESTUtility.getRequestUrl(this.credential,
                BitcasaRESTConstants.METHOD_SHARES, sb.toString(), null);

        Log.d(RESTAdapter.TAG, "browseShare URL: " + url);
        HttpsURLConnection connection = null;
        InputStream inputStream = null;

        try {
            connection = (HttpsURLConnection) new URL(url)
                    .openConnection();
            connection.setDoInput(true);
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_GET);
            this.bitcasaRESTUtility.setRequestHeaders(this.credential, connection, null);

            final BitcasaError error = this.bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {
                inputStream = connection.getInputStream();
                final String response = this.bitcasaRESTUtility.getResponseFromInputStream(inputStream);
                final BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    final SharedFolder sharedFolder = new Gson().fromJson(bitcasaResponse.getResult(), SharedFolder.class);

                    for (final ItemMeta meta : sharedFolder.getItems()) {
                        if (meta.isFolder()) {
                            items.add(new Folder(this.clone(), meta, null, BitcasaRESTConstants.ITEM_STATE_SHARE, shareKey));
                        } else {
                            items.add(new com.bitcasa.cloudfs.File(this.clone(), meta, null, BitcasaRESTConstants.ITEM_STATE_SHARE, shareKey));
                        }
                    }
                } else {
                    throw new BitcasaException(bitcasaResponse.getError().getCode(),
                            bitcasaResponse.getError().getMessage());
                }
            }
        } catch (final ProtocolException e) {
            throw new BitcasaException(e);
        } catch (final MalformedURLException e) {
            throw new BitcasaException(e);
        } catch (final IOException ioe) {
            throw new BitcasaException(ioe);
        } catch (final BitcasaException e) {
            throw new BitcasaException(e);
        } catch (final JsonSyntaxException e) {
            throw new BitcasaException(e);
        } catch (final RuntimeException e) {
            throw new BitcasaException(e);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return items.toArray(new Item[items.size()]);
    }

    /**
     * Given a valid location in a user's fileSystem, all items found in this share specified by
     * the shareKey will be inserted into the given location.
     * File collisions will be handled with the exist action specified.
     *
     * @param shareKey          The shareKey of the share to be received.
     * @param pathToInsertShare Path to save the received files and folders.
     * @param exists            The action to perform if the version exists at the destination.
     * @return An array of item objects received from share.
     * @throws IOException      If a network error occurs
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public Item[] receiveShare(final String shareKey, final String pathToInsertShare,
                               final BitcasaRESTConstants.Exists exists)
            throws IOException, BitcasaException {
        if (RESTAdapter.stringParameterNotValid(shareKey)) {
            throw new IllegalArgumentException(RESTAdapter.MISSING_PARAM + "shareKey");
        }
        final ArrayList<Item> items = new ArrayList<Item>();
        final String url = this.bitcasaRESTUtility.getRequestUrl(this.credential,
                BitcasaRESTConstants.METHOD_SHARES, shareKey + File.separator, null);

        Log.d(RESTAdapter.TAG, "receiveShare URL: " + url);
        final Map<String, String> formParams = new TreeMap<String, String>();
        formParams.put(BitcasaRESTConstants.BODY_PATH, URLEncoder.encode(pathToInsertShare,
                BitcasaRESTConstants.UTF_8_ENCODING));
        if (exists != null) {
            switch (exists) {
                case FAIL:
                    formParams.put(BitcasaRESTConstants.BODY_EXISTS,
                            BitcasaRESTConstants.EXISTS_FAIL);
                    break;
                case OVERWRITE:
                    formParams.put(BitcasaRESTConstants.BODY_EXISTS,
                            BitcasaRESTConstants.EXISTS_OVERWRITE);
                    break;
                case REUSE:
                    formParams.put(BitcasaRESTConstants.BODY_EXISTS,
                            BitcasaRESTConstants.EXISTS_REUSE);
                    break;
                case RENAME:
                default:
                    formParams.put(BitcasaRESTConstants.BODY_EXISTS,
                            BitcasaRESTConstants.EXISTS_RENAME);
                    break;
            }
        }

        OutputStream outputStream = null;
        InputStream inputStream = null;
        HttpsURLConnection connection = null;
        try {
            connection = (HttpsURLConnection) new URL(url)
                    .openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_POST);
            connection.setReadTimeout(BitcasaRESTConstants.CONNECTION_TIME_OUT);
            connection.setConnectTimeout(BitcasaRESTConstants.CONNECTION_TIME_OUT);
            connection.setUseCaches(false);
            this.bitcasaRESTUtility.setRequestHeaders(this.credential, connection, null);
            final String parameters = this.bitcasaRESTUtility.generateParamsString(formParams);
            if (parameters != null) {
                outputStream = connection.getOutputStream();
                Log.d(RESTAdapter.TAG, "POST string: " + parameters);
                outputStream.write(parameters.getBytes());
            }

            final BitcasaError error = this.bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {
                inputStream = connection.getInputStream();
                final String response = this.bitcasaRESTUtility.getResponseFromInputStream(inputStream);
                final BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    final ItemMeta[] sharedItems = new Gson().fromJson(bitcasaResponse.getResult(), ItemMeta[].class);

                    for (final ItemMeta meta : sharedItems) {
                        if (meta.isFolder()) {
                            items.add(new Folder(this.clone(), meta, pathToInsertShare, BitcasaRESTConstants.ITEM_STATE_NORMAL, null));
                        } else {
                            items.add(new com.bitcasa.cloudfs.File(this.clone(), meta, pathToInsertShare, BitcasaRESTConstants.ITEM_STATE_NORMAL, null));
                        }
                    }
                } else {
                    throw new BitcasaException(bitcasaResponse.getError().getCode(),
                            bitcasaResponse.getError().getMessage());
                }
            }
        } catch (final ProtocolException e) {
            throw new BitcasaException(e);
        } catch (final MalformedURLException e) {
            throw new BitcasaException(e);
        } catch (final IOException ioe) {
            throw new BitcasaException(ioe);
        } catch (final BitcasaException e) {
            throw new BitcasaException(e);
        } catch (final JsonSyntaxException e) {
            throw new BitcasaException(e);
        } catch (final RuntimeException e) {
            throw new BitcasaException(e);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return items.toArray(new Item[items.size()]);
    }

    /**
     * Given a valid share and its password, this entry point will unlock the share for the login
     * session.
     *
     * @param shareKey The shareKey of the share need to be unlocked.
     * @param password Password associated with the share need to be unlocked.
     * @return Returns true if the share is unlocked successfully, otherwise false.
     * @throws IOException      If a network error occurs.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public boolean unlockShare(final String shareKey, final String password)
            throws IOException, BitcasaException {

        final String sb = shareKey + BitcasaRESTConstants.METHOD_UNLOCK;
        final String url = this.bitcasaRESTUtility.getRequestUrl(this.credential,
                BitcasaRESTConstants.METHOD_SHARES, sb, null);


        Log.d(RESTAdapter.TAG, "unlockShare URL: " + url);
        final Map<String, String> formParams = new TreeMap<String, String>();
        formParams.put(BitcasaRESTConstants.PARAM_PASSWORD, URLEncoder.encode(password,
                BitcasaRESTConstants.UTF_8_ENCODING));

        OutputStream outputStream = null;
        InputStream inputStream = null;
        HttpsURLConnection connection = null;
        boolean booleanResult = false;
        try {
            connection = (HttpsURLConnection) new URL(url)
                    .openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_POST);
            connection.setReadTimeout(BitcasaRESTConstants.CONNECTION_TIME_OUT);
            connection.setConnectTimeout(BitcasaRESTConstants.CONNECTION_TIME_OUT);
            connection.setUseCaches(false);
            this.bitcasaRESTUtility.setRequestHeaders(this.credential, connection, null);
            final String parameters = this.bitcasaRESTUtility.generateParamsString(formParams);
            if (parameters != null) {
                outputStream = connection.getOutputStream();
                Log.d(RESTAdapter.TAG, "POST string: " + parameters);
                outputStream.write(parameters.getBytes());
            }

            final BitcasaError error = this.bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {
                inputStream = connection.getInputStream();
                final String response = this.bitcasaRESTUtility.getResponseFromInputStream(connection.getInputStream());
                final BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    booleanResult = true;
                } else {
                    throw new BitcasaException(bitcasaResponse.getError().getCode(),
                            bitcasaResponse.getError().getMessage());
                }
            }

        } catch (final ProtocolException e) {
            throw new BitcasaException(e);
        } catch (final MalformedURLException e) {
            throw new BitcasaException(e);
        } catch (final IOException ioe) {
            throw new BitcasaException(ioe);
        } catch (final BitcasaException e) {
            throw new BitcasaException(e);
        } catch (final JsonSyntaxException e) {
            throw new BitcasaException(e);
        } catch (final RuntimeException e) {
            throw new BitcasaException(e);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return booleanResult;
    }

    /**
     * Alter the share information associated with the given shareKey.
     *
     * @param shareKey        The shareKey of the share which needs to be altered.
     * @param currentPassword Password associated with the share which needs to be altered.
     * @param newPassword     New password to be set to the current share.
     * @return Share object with altered share info.
     * @throws IOException      If a network error occurs.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public Share alterShareInfo(final String shareKey, final String currentPassword, final String newPassword)
            throws IOException, BitcasaException {

        final String sb = shareKey + BitcasaRESTConstants.METHOD_INFO;
        final String url = this.bitcasaRESTUtility.getRequestUrl(this.credential,
                BitcasaRESTConstants.METHOD_SHARES, sb, null);

        Log.d(RESTAdapter.TAG, "alterShareInfo URL: " + url);
        final Map<String, String> formParams = new TreeMap<String, String>();
        formParams.put(BitcasaRESTConstants.PARAM_CURRENT_PASSWORD,
                URLEncoder.encode(currentPassword, BitcasaRESTConstants.UTF_8_ENCODING));
        formParams.put(BitcasaRESTConstants.PARAM_PASSWORD, URLEncoder.encode(newPassword,
                BitcasaRESTConstants.UTF_8_ENCODING));

        OutputStream outputStream = null;
        InputStream inputStream = null;
        HttpsURLConnection connection = null;
        Share share = null;
        try {
            connection = (HttpsURLConnection) new URL(url)
                    .openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_POST);
            connection.setReadTimeout(BitcasaRESTConstants.CONNECTION_TIME_OUT);
            connection.setConnectTimeout(BitcasaRESTConstants.CONNECTION_TIME_OUT);
            connection.setUseCaches(false);
            this.bitcasaRESTUtility.setRequestHeaders(this.credential, connection, null);
            final String parameters = this.bitcasaRESTUtility.generateParamsString(formParams);
            if (parameters != null) {
                outputStream = connection.getOutputStream();
                Log.d(RESTAdapter.TAG, "POST string: " + parameters);
                outputStream.write(parameters.getBytes());
            }

            final BitcasaError error = this.bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {
                inputStream = connection.getInputStream();
                final String response = this.bitcasaRESTUtility.getResponseFromInputStream(inputStream);
                final BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    final ShareItem shareItem = new Gson().fromJson(bitcasaResponse.getResult(),
                            ShareItem.class);
                    share = new Share(this.clone(), shareItem, null);
                } else {
                    throw new BitcasaException(bitcasaResponse.getError().getCode(),
                            bitcasaResponse.getError().getMessage());
                }
            }

        } catch (final ProtocolException e) {
            throw new BitcasaException(e);
        } catch (final MalformedURLException e) {
            throw new BitcasaException(e);
        } catch (final IOException ioe) {
            throw new BitcasaException(ioe);
        } catch (final BitcasaException e) {
            throw new BitcasaException(e);
        } catch (final JsonSyntaxException e) {
            throw new BitcasaException(e);
        } catch (final RuntimeException e) {
            throw new BitcasaException(e);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return share;
    }

    /**
     * Alter the share attributes associated with the given shareKey.
     *
     * @param shareKey        The shareKey of the share to be altered.
     * @param changes         The changes to be updated to the share associated with given shareKey.
     * @param currentPassword Password associated with the share which needs to be altered.
     * @return Share object with altered share attributes.
     * @throws IOException      If a network error occurs.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public Share alterShare(final String shareKey, final Map<String, String> changes, final String currentPassword)
            throws IOException, BitcasaException {
        if (RESTAdapter.objectParameterNotValid(changes)) {
            throw new IllegalArgumentException(RESTAdapter.MISSING_PARAM + "changes");
        }

        final String sb = shareKey + BitcasaRESTConstants.METHOD_INFO;
        final String url = this.bitcasaRESTUtility.getRequestUrl(this.credential,
                BitcasaRESTConstants.METHOD_SHARES, sb, null);

        Log.d(RESTAdapter.TAG, "alterShare URL: " + url);
        final Map<String, String> formParams = new TreeMap<String, String>();
        formParams.put(BitcasaRESTConstants.PARAM_CURRENT_PASSWORD,
                URLEncoder.encode(currentPassword, BitcasaRESTConstants.UTF_8_ENCODING));
        formParams.putAll(changes);
        OutputStream outputStream = null;
        InputStream inputStream = null;
        HttpsURLConnection connection = null;
        Share share = null;
        try {
            connection = (HttpsURLConnection) new URL(url)
                    .openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_POST);
            connection.setReadTimeout(BitcasaRESTConstants.CONNECTION_TIME_OUT);
            connection.setConnectTimeout(BitcasaRESTConstants.CONNECTION_TIME_OUT);
            connection.setUseCaches(false);
            this.bitcasaRESTUtility.setRequestHeaders(this.credential, connection, null);
            final String parameters = this.bitcasaRESTUtility.generateParamsString(formParams);
            if (parameters != null) {
                outputStream = connection.getOutputStream();
                Log.d(RESTAdapter.TAG, "POST string: " + parameters);
                outputStream.write(parameters.getBytes());
            }

            final BitcasaError error = this.bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {
                inputStream = connection.getInputStream();
                final String response = this.bitcasaRESTUtility.getResponseFromInputStream(inputStream);
                final BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    final ShareItem shareItem = new Gson().fromJson(bitcasaResponse.getResult(),
                            ShareItem.class);
                    share = new Share(this.clone(), shareItem, null);
                } else {
                    throw new BitcasaException(bitcasaResponse.getError().getCode(),
                            bitcasaResponse.getError().getMessage());
                }
            }

        } catch (final ProtocolException e) {
            throw new BitcasaException(e);
        } catch (final MalformedURLException e) {
            throw new BitcasaException(e);
        } catch (final IOException ioe) {
            throw new BitcasaException(ioe);
        } catch (final BitcasaException e) {
            throw new BitcasaException(e);
        } catch (final JsonSyntaxException e) {
            throw new BitcasaException(e);
        } catch (final RuntimeException e) {
            throw new BitcasaException(e);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return share;
    }

    /**
     * Delete the share associated with given shareKey.
     *
     * @param shareKey The shareKey of the share which needs to be deleted.
     * @return Returns true if the share is deleted successfully, otherwise false.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public boolean deleteShare(final String shareKey) throws BitcasaException {

        final String sb = shareKey + File.separator;
        final String url = this.bitcasaRESTUtility.getRequestUrl(this.credential,
                BitcasaRESTConstants.METHOD_SHARES, sb, null);

        Log.d(RESTAdapter.TAG, "deleteShare URL: " + url);
        HttpsURLConnection connection = null;
        boolean booleanResult = false;
        try {
            connection = (HttpsURLConnection) new URL(url)
                    .openConnection();
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_DELETE);
            this.bitcasaRESTUtility.setRequestHeaders(this.credential, connection, null);

            final BitcasaError error = this.bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {
                booleanResult = true;
            }

        } catch (final ProtocolException e) {
            throw new BitcasaException(e);
        } catch (final MalformedURLException e) {
            throw new BitcasaException(e);
        } catch (final IOException ioe) {
            throw new BitcasaException(ioe);
        } catch (final RuntimeException e) {
            throw new BitcasaException(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return booleanResult;
    }
    //endregion

    //region Trash Operations

    /**
     * Browse the trash associated with current account.
     *
     * @return An array of trash item objects found.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public Item[] browseTrash(String path) throws BitcasaException {
        final ArrayList<Item> trash = new ArrayList<Item>();
        final String url = this.bitcasaRESTUtility.getRequestUrl(this.credential,
                BitcasaRESTConstants.METHOD_TRASH, path, null);

        Log.d(RESTAdapter.TAG, "browseTrash URL: " + url);
        HttpsURLConnection connection = null;

        try {
            connection = (HttpsURLConnection) new URL(url)
                    .openConnection();
            connection.setDoInput(true);
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_GET);
            this.bitcasaRESTUtility.setRequestHeaders(this.credential, connection, null);
            final BitcasaError error = this.bitcasaRESTUtility.checkRequestResponse(connection);

            if (error == null) {
                final String response = this.bitcasaRESTUtility.getResponseFromInputStream(connection.getInputStream());
                final BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);
                if (!bitcasaResponse.getResult().isJsonNull()) {
                    final ItemList itemList = new Gson().fromJson(bitcasaResponse.getResult(),
                            ItemList.class);

                    for (final ItemMeta meta : itemList.getItems()) {
                        if (meta.isFolder()) {
                            trash.add(new Folder(this.clone(), meta, null, BitcasaRESTConstants.ITEM_STATE_TRASH, null));
                        } else {
                            trash.add(new com.bitcasa.cloudfs.File(this.clone(), meta, null, BitcasaRESTConstants.ITEM_STATE_TRASH, null));
                        }
                    }

                } else {
                    throw new BitcasaException(bitcasaResponse.getError().getCode(),
                            bitcasaResponse.getError().getMessage());
                }
            }


        } catch (final MalformedURLException e) {
            throw new BitcasaException(e);
        } catch (final ProtocolException e) {
            throw new BitcasaException(e);
        } catch (final IOException ioe) {
            throw new BitcasaException(ioe);
        } catch (final BitcasaException e) {
            throw new BitcasaException(e);
        } catch (final JsonSyntaxException e) {
            throw new BitcasaException(e);
        } catch (final RuntimeException e) {
            throw new BitcasaException(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return trash.toArray(new Item[trash.size()]);
    }

    /**
     * Recover Trash Item
     *
     * @param trashItemId          Item id to be recovered from trash.
     * @param restoreMethod        RestoreMethod to be used on the recover process.
     * @param rescueOrRecreatePath Path to rescue or recreate the item to.
     * @return Returns true if the item is recovered successfully, otherwise false.
     * @throws UnsupportedEncodingException If encoding not supported.
     * @throws BitcasaException             If a CloudFS API error occurs.
     */
    public boolean recoverTrashItem(final String trashItemId, final BitcasaRESTConstants.RestoreMethod restoreMethod,
                                    final String rescueOrRecreatePath) throws
            UnsupportedEncodingException, BitcasaException {
        if (RESTAdapter.stringParameterNotValid(trashItemId)) {
            throw new IllegalArgumentException(RESTAdapter.MISSING_PARAM + "path");
        }
        final StringBuilder sb = new StringBuilder();
        if (trashItemId.startsWith(File.separator)) {
            sb.append(trashItemId.substring(1));
        } else {
            sb.append(trashItemId);
        }
        final String url = this.bitcasaRESTUtility.getRequestUrl(this.credential, BitcasaRESTConstants.METHOD_TRASH,
                sb.toString(), null);

        final Map<String, String> formParams = new TreeMap<String, String>();
        switch (restoreMethod) {
            case FAIL:
                formParams.put(BitcasaRESTConstants.BODY_RESTORE,
                        BitcasaRESTConstants.RESTORE_FAIL);
                break;
            case RESCUE:
                formParams.put(BitcasaRESTConstants.BODY_RESTORE,
                        BitcasaRESTConstants.RESTORE_RESCUE);
                formParams.put(BitcasaRESTConstants.BODY_RESCUE_PATH,
                        URLEncoder.encode(rescueOrRecreatePath,
                                BitcasaRESTConstants.UTF_8_ENCODING));
                break;
            case RECREATE:
                formParams.put(BitcasaRESTConstants.BODY_RESTORE,
                        BitcasaRESTConstants.RESTORE_RECREATE);
                formParams.put(BitcasaRESTConstants.BODY_RECREATE_PATH,
                        URLEncoder.encode(rescueOrRecreatePath,
                                BitcasaRESTConstants.UTF_8_ENCODING));
                break;
        }


        Log.d(RESTAdapter.TAG, "recoverTrashItem URL: " + url);
        HttpsURLConnection connection = null;
        boolean booleanResult = false;
        try {
            connection = (HttpsURLConnection) new URL(url)
                    .openConnection();
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_POST);
            this.bitcasaRESTUtility.setRequestHeaders(this.credential, connection, null);
            connection.setDoOutput(true);
            final OutputStream outputStream = connection.getOutputStream();

            final String body = this.bitcasaRESTUtility.generateParamsString(formParams);
            Log.d("recoverTrashItem", "formParams: " + body);
            outputStream.write(body.getBytes());

            final BitcasaError error = this.bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {
                booleanResult = true;
            }

        } catch (final ProtocolException e) {
            throw new BitcasaException(e);
        } catch (final MalformedURLException e) {
            throw new BitcasaException(e);
        } catch (final IOException ioe) {
            throw new BitcasaException(ioe);
        } catch (final RuntimeException e) {
            throw new BitcasaException(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return booleanResult;
    }

    /**
     * Delete the given item from trash.
     *
     * @param trashItemId Item id to be deleted from trash.
     * @return Returns true if the item is deleted successfully, otherwise false.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public boolean deleteTrashItem(final String trashItemId) throws BitcasaException {

        final String url = this.bitcasaRESTUtility.getRequestUrl(this.credential, BitcasaRESTConstants.METHOD_TRASH,
                trashItemId, null);

        Log.d(RESTAdapter.TAG, "deleteTrashItem URL: " + url);
        HttpsURLConnection connection = null;
        boolean booleanResult = false;
        try {
            connection = (HttpsURLConnection) new URL(url)
                    .openConnection();
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_DELETE);
            this.bitcasaRESTUtility.setRequestHeaders(this.credential, connection, null);
            connection.setDoInput(true);

            final BitcasaError error = this.bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {
                booleanResult = true;
            }

        } catch (final ProtocolException e) {
            throw new BitcasaException(e);
        } catch (final MalformedURLException e) {
            throw new BitcasaException(e);
        } catch (final IOException ioe) {
            throw new BitcasaException(ioe);
        } catch (final RuntimeException e) {
            throw new BitcasaException(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return booleanResult;
    }
    //endregion

    //region Admin operations

    /**
     * Creates a new CloudFS user with the supplied data.
     *
     * @param session   Session object.
     * @param username  The username for the new user.
     * @param password  The password for the new user.
     * @param email     The email for the new user.
     * @param firstName The first name of the new user.
     * @param lastName  The last name of the new user.
     * @return The newly created user.
     * @throws IOException              If a network error occurs.
     * @throws IllegalArgumentException If the parameters are invalid or misused.
     * @throws BitcasaException         If a CloudFS API error occurs.
     */
    public User createAccount(final Session session, final String username, final String password, final String email,
                              final String firstName, final String lastName) throws IOException,
            IllegalArgumentException, BitcasaException {
        if (RESTAdapter.stringParameterNotValid(username)) {
            throw new IllegalArgumentException(RESTAdapter.MISSING_PARAM + "username");
        }
        if (RESTAdapter.stringParameterNotValid(password)) {
            throw new IllegalArgumentException(RESTAdapter.MISSING_PARAM + "password");
        }

        final Map<String, String> parameters = new TreeMap<String, String>();
        final Map<String, String> queryParams = new TreeMap<String, String>();

        parameters.put(BitcasaRESTConstants.PARAM_USERNAME, Uri.encode(username, " "));
        parameters.put(BitcasaRESTConstants.PARAM_PASSWORD, Uri.encode(password, " "));

        if ((email != null) && !email.isEmpty()) {
            parameters.put(BitcasaRESTConstants.PARAM_EMAIL, Uri.encode(email));
        }
        if ((firstName != null) && !firstName.isEmpty()) {
            parameters.put(BitcasaRESTConstants.PARAM_FIRSTNAME, Uri.encode(firstName, " "));
        }
        if ((lastName != null) && !lastName.isEmpty()) {
            parameters.put(BitcasaRESTConstants.PARAM_LASTNAME, Uri.encode(lastName, " "));
        }

        final String body = this.bitcasaRESTUtility.generateParamsString(parameters);

        final String endpoint = BitcasaRESTConstants.METHOD_ADMIN + BitcasaRESTConstants.METHOD_CLOUDFS +
                BitcasaRESTConstants.METHOD_CUSTOMERS;
        final String url = this.bitcasaRESTUtility.getRequestUrl(this.credential, endpoint, null, queryParams);
        Log.d(RESTAdapter.TAG, "createAccount: " + url + " body: " + body);
        HttpsURLConnection connection = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;

        User user = null;
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat(BitcasaRESTConstants.DATE_FORMAT,
                    Locale.US);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            String date = sdf.format(Calendar.getInstance(Locale.US).getTime());
            int index = date.lastIndexOf('+');
            if (index == -1) {
                index = date.length();
            }
            date = date.substring(0, index);

            connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_POST);


            final String uri = BitcasaRESTConstants.API_VERSION_2 + BitcasaRESTConstants.METHOD_ADMIN
                    + BitcasaRESTConstants.METHOD_CLOUDFS + BitcasaRESTConstants.METHOD_CUSTOMERS;

            final String authorizationValue = this.bitcasaRESTUtility.generateAdminAuthorizationValue(session,
                    uri, body, date);
            connection.setRequestProperty(BitcasaRESTConstants.HEADER_AUTORIZATION,
                    authorizationValue);
            connection.setRequestProperty(BitcasaRESTConstants.HEADER_CONTENT_TYPE,
                    BitcasaRESTConstants.FORM_URLENCODED);
            connection.setRequestProperty(BitcasaRESTConstants.HEADER_DATE, date);

            if (body != null) {
                connection.setDoOutput(true);
                outputStream = connection.getOutputStream();
                outputStream.write(body.getBytes());
            }

            final BitcasaError error = this.bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {
                inputStream = connection.getInputStream();
                final String response = this.bitcasaRESTUtility.getResponseFromInputStream(inputStream);
                final BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    final UserProfile userProfile = new Gson().fromJson(bitcasaResponse.getResult(),
                            UserProfile.class);
                    user = new User(this.clone(), userProfile);
                } else {
                    throw new BitcasaException(bitcasaResponse.getError().getCode(),
                            bitcasaResponse.getError().getMessage());
                }
            }

        } catch (final MalformedURLException e) {
            throw new BitcasaException(e);
        } catch (final ProtocolException e) {
            throw new BitcasaException(e);
        } catch (final UnsupportedEncodingException e) {
            throw new BitcasaException(e);
        } catch (final IOException ioe) {
            throw new BitcasaException(ioe);
        } catch (final NoSuchAlgorithmException e) {
            throw new BitcasaException(e);
        } catch (final InvalidKeyException e) {
            throw new BitcasaException(e);
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }


        return user;
    }

    /**
     * Creates a new account plan with the supplied data.
     *
     * @param session The session object.
     * @param name    The name of the account plan.
     * @param limit   The limit for the account plan.
     * @return The newly created account plan instance.
     * @throws IOException              If a network error occurs.
     * @throws IllegalArgumentException If the parameters are invalid or misused.
     * @throws BitcasaException         If a CloudFS API error occurs.
     */
    public Plan createPlan(Session session, String name, String limit) throws BitcasaException, IOException,
            IllegalArgumentException {
        if (RESTAdapter.stringParameterNotValid(name)) {
            throw new IllegalArgumentException(RESTAdapter.MISSING_PARAM + "name");
        }
        if (RESTAdapter.stringParameterNotValid(limit)) {
            throw new IllegalArgumentException(RESTAdapter.MISSING_PARAM + "limit");
        }

        final Map<String, String> parameters = new TreeMap<String, String>();
        final Map<String, String> queryParams = new TreeMap<String, String>();

        parameters.put(BitcasaRESTConstants.PARAM_NAME, Uri.encode(name, " "));
        parameters.put(BitcasaRESTConstants.PARAM_LIMIT, Uri.encode(limit, " "));

        final String body = this.bitcasaRESTUtility.generateParamsString(parameters);

        final String endpoint = BitcasaRESTConstants.METHOD_ADMIN + BitcasaRESTConstants.METHOD_CUSTOMERS
                + BitcasaRESTConstants.METHOD_PLAN;
        final String url = this.bitcasaRESTUtility.getRequestUrl(this.credential, endpoint, null, queryParams);
        Log.d(RESTAdapter.TAG, "createPlan: " + url + " body: " + body);
        HttpsURLConnection connection = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;

        Plan plan = null;
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat(BitcasaRESTConstants.DATE_FORMAT,
                    Locale.US);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            String date = sdf.format(Calendar.getInstance(Locale.US).getTime());
            int index = date.lastIndexOf('+');
            if (index == -1) {
                index = date.length();
            }
            date = date.substring(0, index);

            connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_POST);


            final String uri = BitcasaRESTConstants.API_VERSION_2 + BitcasaRESTConstants.METHOD_ADMIN
                    + BitcasaRESTConstants.METHOD_CUSTOMERS + BitcasaRESTConstants.METHOD_PLAN;

            final String authorizationValue = this.bitcasaRESTUtility.generateAdminAuthorizationValue(session,
                    uri, body, date);
            connection.setRequestProperty(BitcasaRESTConstants.HEADER_AUTORIZATION,
                    authorizationValue);
            connection.setRequestProperty(BitcasaRESTConstants.HEADER_CONTENT_TYPE,
                    BitcasaRESTConstants.FORM_URLENCODED);
            connection.setRequestProperty(BitcasaRESTConstants.HEADER_DATE, date);

            if (body != null) {
                connection.setDoOutput(true);
                outputStream = connection.getOutputStream();
                outputStream.write(body.getBytes());
            }

            final BitcasaError error = this.bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {
                inputStream = connection.getInputStream();
                final String response = this.bitcasaRESTUtility.getResponseFromInputStream(inputStream);
                final BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    PlanMeta planMeta = new Gson().fromJson(bitcasaResponse.getResult(),
                            PlanMeta.class);
                    plan = new Plan(planMeta);
                } else {
                    throw new BitcasaException(bitcasaResponse.getError().getCode(),
                            bitcasaResponse.getError().getMessage());
                }
            }

        } catch (final MalformedURLException e) {
            throw new BitcasaException(e);
        } catch (final ProtocolException e) {
            throw new BitcasaException(e);
        } catch (final UnsupportedEncodingException e) {
            throw new BitcasaException(e);
        } catch (final IOException ioe) {
            throw new BitcasaException(ioe);
        } catch (final NoSuchAlgorithmException e) {
            throw new BitcasaException(e);
        } catch (final InvalidKeyException e) {
            throw new BitcasaException(e);
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }


        return plan;
    }

    /**
     * Lists the custom end user account plans.
     *
     * @param session The session object.
     * @return List of custom end user plans.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public Plan[] listPlans(Session session) throws BitcasaException {
        final ArrayList<Plan> listPlans = new ArrayList<Plan>();
        final String url = this.bitcasaRESTUtility.getRequestUrl(this.credential,
                BitcasaRESTConstants.METHOD_ADMIN + BitcasaRESTConstants.METHOD_CUSTOMERS +
                        BitcasaRESTConstants.METHOD_PLAN, null, null);

        Log.d(RESTAdapter.TAG, "listPlans URL: " + url);
        HttpsURLConnection connection = null;

        try {
            connection = (HttpsURLConnection) new URL(url)
                    .openConnection();
            connection.setDoInput(true);
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_GET);

            final SimpleDateFormat sdf = new SimpleDateFormat(BitcasaRESTConstants.DATE_FORMAT,
                    Locale.US);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            String date = sdf.format(Calendar.getInstance(Locale.US).getTime());
            int index = date.lastIndexOf('+');
            if (index == -1) {
                index = date.length();
            }
            date = date.substring(0, index);

            final String uri = BitcasaRESTConstants.API_VERSION_2 + BitcasaRESTConstants.METHOD_ADMIN
                    + BitcasaRESTConstants.METHOD_CUSTOMERS + BitcasaRESTConstants.METHOD_PLAN;

            final String authorizationValue = this.bitcasaRESTUtility.generateAdminAuthorizationValue(BitcasaRESTConstants.REQUEST_METHOD_GET, session,
                    uri, date);
            connection.setRequestProperty(BitcasaRESTConstants.HEADER_AUTORIZATION,
                    authorizationValue);
            connection.setRequestProperty(BitcasaRESTConstants.HEADER_CONTENT_TYPE,
                    BitcasaRESTConstants.FORM_URLENCODED);
            connection.setRequestProperty(BitcasaRESTConstants.HEADER_DATE, date);


            final BitcasaError error = this.bitcasaRESTUtility.checkRequestResponse(connection);

            if (error == null) {
                final String response = this.bitcasaRESTUtility.getResponseFromInputStream(connection.getInputStream());
                final BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    final PlanMeta[] plansMeta = new Gson().fromJson(bitcasaResponse.getResult(),
                            PlanMeta[].class);


                    for (final PlanMeta planMeta : plansMeta) {
                        Plan plan = new Plan(planMeta);
                        listPlans.add(plan);
                    }

                } else {
                    throw new BitcasaException(bitcasaResponse.getError().getCode(),
                            bitcasaResponse.getError().getMessage());
                }
            }

        } catch (final ProtocolException e) {
            throw new BitcasaException(e);
        } catch (final MalformedURLException e) {
            throw new BitcasaException(e);
        } catch (final IOException ioe) {
            throw new BitcasaException(ioe);
        } catch (final BitcasaException e) {
            throw new BitcasaException(e);
        } catch (final JsonSyntaxException e) {
            throw new BitcasaException(e);
        } catch (final RuntimeException e) {
            throw new BitcasaException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new BitcasaException(e);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return listPlans.toArray(new Plan[listPlans.size()]);
    }

    /**
     * Update the user details and account plan for the given the user account code.
     *
     * @param session   The session object.
     * @param id        The account id of the user account.
     * @param username  The username of the account to be updated.
     * @param firstName The firstname of the account to be updated.
     * @param lastName  The lastname of the account to be updated.
     * @param planCode  The plan code of the account to be updated.
     * @return The updated user.
     * @throws BitcasaException If a CloudFS API error occurs.
     * @throws IOException      If response data can not be read due to network errors.
     */
    public User updateUser(Session session, String id, String username, String firstName,
                           String lastName, String planCode)
            throws BitcasaException, IOException {

        if (RESTAdapter.stringParameterNotValid(id)) {
            throw new IllegalArgumentException(RESTAdapter.MISSING_PARAM + "username");
        }

        final Map<String, String> parameters = new TreeMap<String, String>();
        final Map<String, String> queryParams = new TreeMap<String, String>();

        parameters.put(BitcasaRESTConstants.PARAM_ID, Uri.encode(id, " "));

        if ((username != null) && !username.isEmpty()) {
            parameters.put(BitcasaRESTConstants.PARAM_USERNAME, Uri.encode(username, " "));
        }
        if ((firstName != null) && !firstName.isEmpty()) {
            parameters.put(BitcasaRESTConstants.PARAM_FIRSTNAME, Uri.encode(firstName, " "));
        }
        if ((lastName != null) && !lastName.isEmpty()) {
            parameters.put(BitcasaRESTConstants.PARAM_LASTNAME, Uri.encode(lastName, " "));
        }
        if ((planCode != null) && !planCode.isEmpty()) {
            parameters.put(BitcasaRESTConstants.PARAM_PLANCODE, Uri.encode(planCode, " "));
        }

        final String body = this.bitcasaRESTUtility.generateParamsString(parameters);

        final String endpoint = BitcasaRESTConstants.METHOD_ADMIN + BitcasaRESTConstants.METHOD_CUSTOMERS + id;
        final String url = this.bitcasaRESTUtility.getRequestUrl(this.credential, endpoint, null, queryParams);
        Log.d(RESTAdapter.TAG, "createAccount: " + url + " body: " + body);
        HttpsURLConnection connection = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;

        User user = null;
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat(BitcasaRESTConstants.DATE_FORMAT,
                    Locale.US);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            String date = sdf.format(Calendar.getInstance(Locale.US).getTime());
            int index = date.lastIndexOf('+');
            if (index == -1) {
                index = date.length();
            }
            date = date.substring(0, index);

            connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_POST);


            final String uri = BitcasaRESTConstants.API_VERSION_2 + BitcasaRESTConstants.METHOD_ADMIN
                    + BitcasaRESTConstants.METHOD_CUSTOMERS + id;

            final String authorizationValue = this.bitcasaRESTUtility.generateAdminAuthorizationValue(session,
                    uri, body, date);
            connection.setRequestProperty(BitcasaRESTConstants.HEADER_AUTORIZATION,
                    authorizationValue);
            connection.setRequestProperty(BitcasaRESTConstants.HEADER_CONTENT_TYPE,
                    BitcasaRESTConstants.FORM_URLENCODED);
            connection.setRequestProperty(BitcasaRESTConstants.HEADER_DATE, date);

            if (body != null) {
                connection.setDoOutput(true);
                outputStream = connection.getOutputStream();
                outputStream.write(body.getBytes());
            }

            final BitcasaError error = this.bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {
                inputStream = connection.getInputStream();
                final String response = this.bitcasaRESTUtility.getResponseFromInputStream(inputStream);
                final BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    final UserProfile userProfile = new Gson().fromJson(bitcasaResponse.getResult(),
                            UserProfile.class);
                    user = new User(this.clone(), userProfile);
                } else {
                    throw new BitcasaException(bitcasaResponse.getError().getCode(),
                            bitcasaResponse.getError().getMessage());
                }
            }

        } catch (final MalformedURLException e) {
            throw new BitcasaException(e);
        } catch (final ProtocolException e) {
            throw new BitcasaException(e);
        } catch (final UnsupportedEncodingException e) {
            throw new BitcasaException(e);
        } catch (final IOException ioe) {
            throw new BitcasaException(ioe);
        } catch (final NoSuchAlgorithmException e) {
            throw new BitcasaException(e);
        } catch (final InvalidKeyException e) {
            throw new BitcasaException(e);
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }


        return user;
    }

    /**
     * Deletes the account plan from CloudFS for the given plan id.
     *
     * @param session The session object.
     * @param planId  The path of the item which needs to be deleted.
     * @return Returns true if the account plan is deleted successfully, otherwise false.
     * @throws IOException      If response data can not be read due to network errors.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public boolean deletePlan(Session session, String planId)
            throws IOException, BitcasaException {

        final String endpoint = BitcasaRESTConstants.METHOD_ADMIN + BitcasaRESTConstants.METHOD_CUSTOMERS +
                BitcasaRESTConstants.METHOD_PLAN + planId + File.separator;
        final String url = this.bitcasaRESTUtility.getRequestUrl(this.credential, endpoint, null, null);

        Log.d(RESTAdapter.TAG, "deletePlan: URL - " + url);
        HttpsURLConnection connection = null;
        InputStream inputStream = null;

        boolean result = false;
        try {

            connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setDoInput(true);
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_DELETE);

            final SimpleDateFormat sdf = new SimpleDateFormat(BitcasaRESTConstants.DATE_FORMAT,
                    Locale.US);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            String date = sdf.format(Calendar.getInstance(Locale.US).getTime());
            int index = date.lastIndexOf('+');
            if (index == -1) {
                index = date.length();
            }
            date = date.substring(0, index);

            final String uri = BitcasaRESTConstants.API_VERSION_2 + BitcasaRESTConstants.METHOD_ADMIN
                    + BitcasaRESTConstants.METHOD_CUSTOMERS +
                    BitcasaRESTConstants.METHOD_PLAN + planId + File.separator;

            final String authorizationValue = this.bitcasaRESTUtility.generateAdminAuthorizationValue(BitcasaRESTConstants.REQUEST_METHOD_DELETE, session,
                    uri, date);
            connection.setRequestProperty(BitcasaRESTConstants.HEADER_AUTORIZATION,
                    authorizationValue);
            connection.setRequestProperty(BitcasaRESTConstants.HEADER_CONTENT_TYPE,
                    BitcasaRESTConstants.FORM_URLENCODED);
            connection.setRequestProperty(BitcasaRESTConstants.HEADER_DATE, date);

            final BitcasaError error = this.bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {
                inputStream = connection.getInputStream();
                final String response = this.bitcasaRESTUtility.getResponseFromInputStream(inputStream);
                final BitcasaResponse bitcasaResponse = new Gson().fromJson(response,
                        BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    result = Boolean.valueOf(bitcasaResponse.getResult().toString());
                }
            } else {
                result = false;
            }

        } catch (NoSuchAlgorithmException e) {
            throw new BitcasaException(e);
        } catch (InvalidKeyException e) {
            throw new BitcasaException(e);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

        return result;
    }

    //endregion
}
