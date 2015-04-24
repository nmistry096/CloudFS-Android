package com.bitcasa.cloudfs.api;

import android.net.Uri;
import android.util.Log;

import com.bitcasa.cloudfs.Account;
import com.bitcasa.cloudfs.BitcasaError;
import com.bitcasa.cloudfs.Credential;
import com.bitcasa.cloudfs.Folder;
import com.bitcasa.cloudfs.Item;
import com.bitcasa.cloudfs.Session;
import com.bitcasa.cloudfs.Share;
import com.bitcasa.cloudfs.User;
import com.bitcasa.cloudfs.Utils.BitcasaProgressListener;
import com.bitcasa.cloudfs.Utils.BitcasaRESTConstants;
import com.bitcasa.cloudfs.Utils.BitcasaRESTConstants.Exists;
import com.bitcasa.cloudfs.Utils.BitcasaRESTConstants.VersionExists;
import com.bitcasa.cloudfs.exception.BitcasaAuthenticationException;
import com.bitcasa.cloudfs.exception.BitcasaClientException;
import com.bitcasa.cloudfs.exception.BitcasaException;
import com.bitcasa.cloudfs.exception.BitcasaFileException;
import com.bitcasa.cloudfs.model.AccessToken;
import com.bitcasa.cloudfs.model.ActionData;
import com.bitcasa.cloudfs.model.ActionDataAlter;
import com.bitcasa.cloudfs.model.ActionDataDefault;
import com.bitcasa.cloudfs.model.BaseAction;
import com.bitcasa.cloudfs.model.BitcasaResponse;
import com.bitcasa.cloudfs.model.ItemList;
import com.bitcasa.cloudfs.model.ItemMeta;
import com.bitcasa.cloudfs.model.ShareItem;
import com.bitcasa.cloudfs.model.SharedFolder;
import com.bitcasa.cloudfs.model.UserProfile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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
public class RESTAdapter {

    private static final String TAG = RESTAdapter.class.getSimpleName();
    private static final String MISSING_PARAM = "Missing required parameter : ";

    private final BitcasaRESTUtility bitcasaRESTUtility;
    private final Credential credential;

    /**
     * Constructor, takes in a credential instance and initialises the RESTAdapter instance.
     *
     * @param credential Application Credentials.
     */
    public RESTAdapter(Credential credential) {
        bitcasaRESTUtility = new BitcasaRESTUtility();
        this.credential = credential;
    }

    /**
     * Validates whether the given string parameter is null or empty.
     *
     * @param parameter The parameter to validate.
     * @return The validate result, if the string is null or empty, return true and if not false.
     */
    private static boolean stringParameterNotValid(String parameter) {
        return (parameter == null || parameter.isEmpty());
    }

    /**
     * Validate whether an object parameter is not null.
     *
     * @param parameter The parameter to validate.
     * @return The validate result, if the object is null return true and if not false.
     */
    private static boolean objectParameterNotValid(Object parameter) {
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
    public void authenticate(Session session, String username, String password)
            throws IOException, BitcasaException, IllegalArgumentException {
        if (stringParameterNotValid(username)) {
            throw new IllegalArgumentException(MISSING_PARAM + "username");
        }

        if (stringParameterNotValid(password)) {
            throw new IllegalArgumentException(MISSING_PARAM + "password");
        }

        HttpsURLConnection connection = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(BitcasaRESTConstants.DATE_FORMAT,
                    Locale.US);
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            String date = dateFormat.format(Calendar.getInstance(Locale.US).getTime());
            int index = date.lastIndexOf("+");
            if (index == -1) {
                index = date.length();
            }
            date = date.substring(0, index);

            TreeMap<String, String> bodyParams = new TreeMap<String, String>();
            bodyParams.put(BitcasaRESTConstants.PARAM_GRANT_TYPE,
                    Uri.encode(BitcasaRESTConstants.PARAM_PASSWORD, " "));
            bodyParams.put(BitcasaRESTConstants.PARAM_USERNAME, Uri.encode(username, " "));
            bodyParams.put(BitcasaRESTConstants.PARAM_PASSWORD, Uri.encode(password, " "));

            String parameters = bitcasaRESTUtility.generateParamsString(bodyParams);
            String url = bitcasaRESTUtility.getRequestUrl(credential,
                    BitcasaRESTConstants.METHOD_OAUTH2, BitcasaRESTConstants.METHOD_TOKEN, null);
            //generate authorization value
            String uri = BitcasaRESTConstants.API_VERSION_2 + BitcasaRESTConstants.METHOD_OAUTH2 +
                    BitcasaRESTConstants.METHOD_TOKEN;
            String authorizationValue = bitcasaRESTUtility.generateAuthorizationValue(session, uri,
                    parameters, date);

            Log.d(TAG, "getAccessToken URL: " + url);
            connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_POST);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setReadTimeout(300000);
            connection.setConnectTimeout(300000);
            connection.setUseCaches(false);
            connection.setRequestProperty(BitcasaRESTConstants.HEADER_CONTENT_TYPE,
                    BitcasaRESTConstants.FORM_URLENCODED);
            connection.setRequestProperty(BitcasaRESTConstants.HEADER_DATE, date);
            connection.setRequestProperty(BitcasaRESTConstants.HEADER_AUTORIZATION,
                    authorizationValue);

            if (parameters != null) {
                outputStream = connection.getOutputStream();
                Log.d(TAG, "POST string: " + parameters);
                outputStream.write(parameters.getBytes());
            }

            // read response code
            final int responseCode = connection.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                inputStream = connection.getInputStream();
            } else if (responseCode == HttpsURLConnection.HTTP_NOT_FOUND || responseCode ==
                    HttpsURLConnection.HTTP_BAD_REQUEST) {
                inputStream = connection.getErrorStream();
            }

            AccessToken accessToken = new AccessToken();
            if (inputStream != null) {
                String response = bitcasaRESTUtility.getResponseFromInputStream(inputStream);
                accessToken = new Gson().fromJson(response, AccessToken.class);
            }
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                if (credential != null) {
                    this.credential.setAccessToken(accessToken.getAccessToken());
                }
                if (credential != null) {
                    this.credential.setTokenType(accessToken.getTokenType());
                }
            } else {
                throw new BitcasaAuthenticationException(Integer.toString(responseCode));
            }


        } catch (InvalidKeyException e) {
            throw new BitcasaException(e);
        } catch (NoSuchAlgorithmException e) {
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
     * @throws IOException      If a network error occurs.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public Account requestAccountInfo() throws IOException, BitcasaException {
        Account account = null;
        String url = bitcasaRESTUtility.getRequestUrl(credential, BitcasaRESTConstants.METHOD_USER,
                BitcasaRESTConstants.METHOD_PROFILE, null);

        Log.d(TAG, "getProfile URL: " + url);
        HttpsURLConnection connection = null;

        try {
            connection = (HttpsURLConnection) new URL(url)
                    .openConnection();
            connection.setDoInput(true);
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_GET);
            bitcasaRESTUtility.setRequestHeaders(credential, connection, null);

            BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);

            if (error == null) {

                String response = bitcasaRESTUtility.getResponseFromInputStream(connection.getInputStream());
                BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    UserProfile userProfile = new Gson().fromJson(bitcasaResponse.getResult(),
                            UserProfile.class);
                    account = new Account(this, userProfile);
                } else {
                    throw new BitcasaException(bitcasaResponse.getError().getCode(),
                            bitcasaResponse.getError().getMessage());
                }
            }

        } catch (IOException ioe) {
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
     * @throws IOException      If a network error occurs.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public User requestUserInfo() throws IOException, BitcasaException {
        User user = null;
        String url = bitcasaRESTUtility.getRequestUrl(credential, BitcasaRESTConstants.METHOD_USER,
                BitcasaRESTConstants.METHOD_PROFILE, null);

        Log.d(TAG, "getProfile URL: " + url);
        HttpsURLConnection connection = null;

        try {
            connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setDoInput(true);
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_GET);
            bitcasaRESTUtility.setRequestHeaders(credential, connection, null);

            BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {

                String response = bitcasaRESTUtility.getResponseFromInputStream(connection.getInputStream());
                BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    UserProfile userProfile = new Gson().fromJson(bitcasaResponse.getResult(),
                            UserProfile.class);
                    user = new User(this, userProfile);
                } else {
                    throw new BitcasaException(bitcasaResponse.getError().getCode(),
                            bitcasaResponse.getError().getMessage());
                }
            }

        } catch (IOException ioe) {
            throw new BitcasaException(ioe);
        } catch (Exception e) {
            throw new BitcasaException(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return user;
    }

    /**
     * Creates a new CloudFS user with the supplied data.
     *
     * @param session   Session object.
     * @param username  String username.
     * @param password  String password.
     * @param email     String email.
     * @param firstName String first name.
     * @param lastName  String last name.
     * @return User object.
     * @throws IOException              If a network error occurs.
     * @throws IllegalArgumentException If the parameters are invalid or misused.
     * @throws BitcasaException         If a CloudFS API error occurs.
     */
    public User createAccount(Session session, String username, String password, String email,
                              String firstName, String lastName) throws IOException,
            IllegalArgumentException, BitcasaException {
        if (stringParameterNotValid(username)) {
            throw new IllegalArgumentException(MISSING_PARAM + "username");
        }
        if (stringParameterNotValid(password)) {
            throw new IllegalArgumentException(MISSING_PARAM + "password");
        }

        User user = null;

        String endpoint = BitcasaRESTConstants.METHOD_ADMIN + BitcasaRESTConstants.METHOD_CLOUDFS +
                BitcasaRESTConstants.METHOD_CUSTOMERS;

        TreeMap<String, String> parameters = new TreeMap<String, String>();
        TreeMap<String, String> queryParams = new TreeMap<String, String>();

        parameters.put(BitcasaRESTConstants.PARAM_USERNAME, Uri.encode(username, " "));
        parameters.put(BitcasaRESTConstants.PARAM_PASSWORD, Uri.encode(password, " "));

        if (email != null && !email.isEmpty()) {
            parameters.put(BitcasaRESTConstants.PARAM_EMAIL, Uri.encode(email));
        }
        if (firstName != null && !firstName.isEmpty()) {
            parameters.put(BitcasaRESTConstants.PARAM_FIRSTNAME, Uri.encode(firstName, " "));
        }
        if (lastName != null && !lastName.isEmpty()) {
            parameters.put(BitcasaRESTConstants.PARAM_LASTNAME, Uri.encode(lastName, " "));
        }

        String body = bitcasaRESTUtility.generateParamsString(parameters);

        String url = bitcasaRESTUtility.getRequestUrl(credential, endpoint, null, queryParams);
        Log.d(TAG, "createAccount: " + url + " body: " + body);
        HttpsURLConnection connection = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(BitcasaRESTConstants.DATE_FORMAT,
                    Locale.US);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            String date = sdf.format(Calendar.getInstance(Locale.US).getTime());
            int index = date.lastIndexOf("+");
            if (index == -1) {
                index = date.length();
            }
            date = date.substring(0, index);

            connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_POST);


            String uri = BitcasaRESTConstants.API_VERSION_2 + BitcasaRESTConstants.METHOD_ADMIN
                    + BitcasaRESTConstants.METHOD_CLOUDFS + BitcasaRESTConstants.METHOD_CUSTOMERS;

            String authorizationValue = bitcasaRESTUtility.generateAdminAuthorizationValue(session,
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

            BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {
                inputStream = connection.getInputStream();
                String response = bitcasaRESTUtility.getResponseFromInputStream(inputStream);
                BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    UserProfile userProfile = new Gson().fromJson(bitcasaResponse.getResult(),
                            UserProfile.class);
                    user = new User(this, userProfile);
                } else {
                    throw new BitcasaException(bitcasaResponse.getError().getCode(),
                            bitcasaResponse.getError().getMessage());
                }
            }

        } catch (MalformedURLException e) {
            throw new BitcasaException(e);
        } catch (IOException ioe) {
            throw new BitcasaException(ioe);
        } catch (NoSuchAlgorithmException e) {
            throw new BitcasaException(e);
        } catch (InvalidKeyException e) {
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
     * Lists all the files and folders under the given folder path.
     *
     * @param folderPath String folder path.
     * @param version    String version.
     * @param depth      Integer depth.
     * @param filter     String filter.
     * @return Item array of files and folders.
     * @throws IOException      If a network error occurs.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public Item[] getList(String folderPath, int version, int depth, String filter) throws
            IOException, BitcasaException {
        ArrayList<Item> items = new ArrayList<Item>();
        StringBuilder endpoint = new StringBuilder();

        endpoint.append(BitcasaRESTConstants.METHOD_FOLDERS);
        if (folderPath != null) {
            endpoint.append(folderPath);
        } else {
            endpoint.append(File.separator);
        }

        TreeMap<String, String> parameters = new TreeMap<String, String>();
        if (version >= 0) {
            parameters.put(BitcasaRESTConstants.PARAM_VERSION, Integer.toString(version));
        }
        if (depth >= 0) {
            parameters.put(BitcasaRESTConstants.PARAM_DEPTH, Integer.toString(depth));
        }
        if (filter != null) {
            parameters.put(BitcasaRESTConstants.PARAM_FILTER, filter);
        }

        String url = bitcasaRESTUtility.getRequestUrl(credential, endpoint.toString(), null,
                parameters.size() > 0 ? parameters : null);

        Log.d(TAG, "getList URL: " + url);
        HttpsURLConnection connection = null;
        InputStream inputStream = null;

        try {
            connection = (HttpsURLConnection) new URL(url)
                    .openConnection();
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_GET);
            bitcasaRESTUtility.setRequestHeaders(credential, connection, null);

            BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {
                inputStream = connection.getInputStream();
                String response = bitcasaRESTUtility.getResponseFromInputStream(connection.getInputStream());
                BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    ItemList itemList = new Gson().fromJson(bitcasaResponse.getResult(),
                            ItemList.class);

                    for (ItemMeta meta : itemList.getItems()) {
                        if (meta.isFolder()) {
                            items.add(new Folder(this, meta, folderPath));
                        } else {
                            items.add(new com.bitcasa.cloudfs.File(this, meta, folderPath));
                        }
                    }
                } else {
                    throw new BitcasaException(bitcasaResponse.getError().getCode(),
                            bitcasaResponse.getError().getMessage());
                }
            }

        } catch (IOException ioe) {
            if (ioe.getMessage().contains("authentication challenge")) {
                throw new BitcasaAuthenticationException(1020, "Authorization code not recognized");
            } else {
                if (ioe != null) {
                    throw new BitcasaException(ioe);
                }
            }
        } catch (Exception e) {
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
     *                         filesize.
     * @param localDestination Device file location with file path and name.
     * @param listener         The progress listener to listen to the file download progress.
     * @throws BitcasaException If a CloudFS API error occurs.
     * @throws IOException      If a network error occurs.
     */
    public void downloadFile(com.bitcasa.cloudfs.File file, long range, String localDestination,
                             BitcasaProgressListener listener) throws BitcasaException,
            IOException {
        if (stringParameterNotValid(localDestination)) {
            throw new IllegalArgumentException(MISSING_PARAM + "localDestination");
        }

        File local = new File(localDestination);
        //create file
        local.createNewFile();

        String url;
        url = bitcasaRESTUtility.getRequestUrl(credential, BitcasaRESTConstants.METHOD_FILES,
                file.getPath(), null);

        HttpsURLConnection connection = null;
        BufferedInputStream bufferedInputStream = null;
        FileOutputStream fileOutputStream = null;
        BufferedOutputStream bufferedOutputStream = null;

        TreeMap<String, String> header = new TreeMap<String, String>();
        header.put(BitcasaRESTConstants.HEADER_RANGE, "bytes=" + range + "-");

        Log.d(TAG, "downloadFile url: " + url);
        try {
            HttpURLConnection.setFollowRedirects(true);
            connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setDoInput(true);
            bitcasaRESTUtility.setRequestHeaders(credential, connection, header);

            // check response code first
            BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {
                // prepare for writing to file
                bufferedInputStream = new BufferedInputStream(connection.getInputStream());
                fileOutputStream = (range == 0) ? new FileOutputStream(local) : new FileOutputStream(local,
                        true);    // if file exists append
                bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

                // start writing
                byte[] data = new byte[1024];
                int x;
                // use for progress update
                BigInteger fileSize = new BigInteger(Long.toString(file.getSize()));
                BigInteger dataReceived = BigInteger.valueOf(range);
                long progressUpdateTimer = System.currentTimeMillis();
                while ((x = bufferedInputStream.read(data, 0, 1024)) >= 0) {
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
                    if (listener != null && (System.currentTimeMillis() - progressUpdateTimer) >
                            BitcasaRESTConstants.PROGRESS_UPDATE_INTERVAL) {
                        int percentage = dataReceived.multiply(BigInteger.valueOf(100))
                                .divide(fileSize).intValue();
                        listener.onProgressUpdate(file.getName(), percentage,
                                BitcasaProgressListener.ProgressAction.BITCASA_ACTION_DOWNLOAD);
                        progressUpdateTimer = System.currentTimeMillis();
                    }

                    // make sure everything is written to the file so we can compare the size later
                    bufferedOutputStream.flush();
                }

                // make sure that we did download the whole file
                Log.d(TAG, "local file size: " + local.length() + ", file size should be: " +
                        fileSize);
            }

        } catch (IOException ioe) {
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
    public String downloadUrl(String path, long size) throws IOException, BitcasaException {

        String newpath = bitcasaRESTUtility.getRequestUrl(credential,
                BitcasaRESTConstants.METHOD_FILES,
                path, null);

        HttpsURLConnection connection = null;

        TreeMap<String, String> header = new TreeMap<String, String>();
        header.put(BitcasaRESTConstants.HEADER_RANGE, "bytes=" + size + "-");

        Log.d(TAG, "downloadFile url: " + newpath);

        connection = (HttpsURLConnection) new URL(newpath).openConnection();
        connection.setInstanceFollowRedirects(false);
        HttpURLConnection.setFollowRedirects(false);
        bitcasaRESTUtility.setRequestHeaders(credential, connection, header);

        boolean redirect = false;

        int status = connection.getResponseCode();
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
    public InputStream download(Item file, long range) throws BitcasaException {
        InputStream inputStream = null;
        String url;
        url = bitcasaRESTUtility.getRequestUrl(credential, BitcasaRESTConstants.METHOD_FILES,
                file.getAbsoluteParentPath(), null);

        HttpsURLConnection connection = null;
        TreeMap<String, String> header = new TreeMap<String, String>();
        header.put(BitcasaRESTConstants.HEADER_RANGE, "bytes=" + range + "-");

        Log.d(TAG, "download url: " + url);
        try {
            connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setDoInput(true);
            bitcasaRESTUtility.setRequestHeaders(credential, connection, header);

            BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {
                inputStream = connection.getInputStream();
            }

        } catch (IOException ioe) {
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
    public com.bitcasa.cloudfs.File uploadFile(Item folder, String sourceFilePath, Exists exists,
                                               BitcasaProgressListener listener)
            throws IOException, BitcasaException {
        if (stringParameterNotValid(sourceFilePath)) {
            throw new IllegalArgumentException(MISSING_PARAM + "sourceFilePath");
        }

        com.bitcasa.cloudfs.File meta;
        File sourceFile = new File(sourceFilePath);
        if (!sourceFile.exists() || !sourceFile.canRead()) {
            throw new BitcasaClientException("Unable to read file: " + sourceFilePath);
        }
        String path = File.separator;
        if (folder != null && folder.getAbsoluteParentPath() != null) {
            path = folder.getAbsoluteParentPath();
        }

        String urlRequest = bitcasaRESTUtility.getRequestUrl(credential,
                BitcasaRESTConstants.METHOD_FILES, path, null);
        Log.d(TAG, "uploadFile url: " + urlRequest);

        String fieldValue;

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

        MultipartUpload mpUpload = new MultipartUpload(credential, urlRequest, bitcasaRESTUtility);
        mpUpload.addUploadFormField(BitcasaRESTConstants.PARAM_EXISTS, fieldValue);
        mpUpload.addFile(sourceFile, listener);
        meta = mpUpload.finishUpload(this);
        return meta;
    }

    /**
     * Deletes the folder at given path.
     *
     * @param commit If true, folder is deleted immediately. Otherwise, it is moved to the Trash.
     *               The default is false.
     * @param force  If true, folder is deleted even if it contains sub-items. The default is false.
     * @return Returns true if the folder is deleted successfully, otherwise false.
     * @throws IOException      If response data can not be read due to network errors.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public boolean deleteFolder(String path, boolean commit, boolean force)
            throws IOException, BitcasaException {

        return delete(path, Item.FileType.FOLDER, commit, force);
    }

    /**
     * Deletes an existing file from the CloudFS file system.
     *
     * @param path   String file path.
     * @param commit if boolean false, transfer file to trash.
     * @param force  if boolean true, deletes file without trashing.
     * @return boolean flag whether the file was successfully deleted or not.
     * @throws IOException      If response data can not be read due to network errors.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public boolean deleteFile(String path, boolean commit, boolean force) throws IOException,
            BitcasaException {
        return delete(path, Item.FileType.FILE, commit, force);
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
    private boolean delete(String path, String itemType, boolean commit, boolean force)
            throws IOException, BitcasaException {
        boolean result = false;

        TreeMap<String, String> queryParam = new TreeMap<String, String>();
        queryParam.put(BitcasaRESTConstants.PARAM_COMMIT, commit ?
                BitcasaRESTConstants.PARAM_TRUE : BitcasaRESTConstants.PARAM_FALSE);

        String request = BitcasaRESTConstants.METHOD_FILES;
        if (itemType.equals(Item.FileType.FOLDER)) {
            queryParam.put(force ? BitcasaRESTConstants.PARAM_TRUE : BitcasaRESTConstants.PARAM_FALSE,
                    BitcasaRESTConstants.PARAM_TRUE);

            request = BitcasaRESTConstants.METHOD_FOLDERS;
        }

        String url = bitcasaRESTUtility.getRequestUrl(credential, request, path, queryParam);

        Log.d(TAG, "delete: URL - " + url);
        HttpsURLConnection connection = null;
        InputStream inputStream = null;

        try {
            connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_DELETE);
            bitcasaRESTUtility.setRequestHeaders(credential, connection, null);

            BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {
                inputStream = connection.getInputStream();
                String response = bitcasaRESTUtility.getResponseFromInputStream(inputStream);
                BitcasaResponse bitcasaResponse = new Gson().fromJson(response,
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
    public Folder createFolder(String folderName, Folder parentFolder, Exists exists)
            throws IOException, BitcasaException {

        if (stringParameterNotValid(folderName)) {
            throw new IllegalArgumentException(MISSING_PARAM + "filename");
        }

        Folder newFolder = null;

        StringBuilder request = new StringBuilder();
        request.append(BitcasaRESTConstants.METHOD_FOLDERS);

        request.append(parentFolder.getPath());

        TreeMap<String, String> queryParams = new TreeMap<String, String>();
        TreeMap<String, String> params = new TreeMap<String, String>();

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

        String body = bitcasaRESTUtility.generateParamsString(params);
        String url = bitcasaRESTUtility.getRequestUrl(credential, request.toString(), null,
                queryParams);

        HttpsURLConnection connection = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;

        try {
            connection = (HttpsURLConnection) (new URL(url).openConnection());
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_POST);
            bitcasaRESTUtility.setRequestHeaders(credential, connection, null);

            if (body != null) {
                connection.setDoOutput(true);
                outputStream = connection.getOutputStream();
                outputStream.write(body.getBytes());
            }

            BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {
                inputStream = connection.getInputStream();
                String response = bitcasaRESTUtility.getResponseFromInputStream(inputStream);
                BitcasaResponse bitcasaResponse = new Gson().fromJson(response,
                        BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    ItemList items = new Gson().fromJson(bitcasaResponse.getResult(),
                            ItemList.class);
                    if (items.getItems().length > 0) {
                        ItemMeta meta = items.getItems()[0];
                        newFolder = new Folder(this, meta, parentFolder.getPath());
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
    public Item copy(Item item, String destinationPath, String newName, Exists exists)
            throws IOException, BitcasaException {
        if (stringParameterNotValid(destinationPath)) {
            throw new IllegalArgumentException(MISSING_PARAM + "destinationPath");
        }

        Item resultItem = null;

        StringBuilder request = new StringBuilder();
        if (item.getType().equals(Item.FileType.FOLDER)) {
            request.append(BitcasaRESTConstants.METHOD_FOLDERS);
        } else {
            request.append(BitcasaRESTConstants.METHOD_FILES);
        }

        String parent;
        if (item != null && item.getPath() != null) {
            parent = item.getPath();
        } else {
            parent = File.separator;
        }
        request.append(parent);

        TreeMap<String, String> queryParams = new TreeMap<String, String>();
        TreeMap<String, String> params = new TreeMap<String, String>();

        queryParams.put(BitcasaRESTConstants.PARAM_OPERATION,
                BitcasaRESTConstants.OPERATION_COPY);
        params.put(BitcasaRESTConstants.BODY_TO, URLEncoder.encode(destinationPath,
                BitcasaRESTConstants.UTF_8_ENCODING));
        if (newName != null && newName.length() > 0) {
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

        String body = bitcasaRESTUtility.generateParamsString(params);

        String url = bitcasaRESTUtility.getRequestUrl(credential, request.toString(), null,
                queryParams);
        Log.d(TAG, "copy: " + url + " body: " + body);
        HttpsURLConnection connection = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_POST);
            bitcasaRESTUtility.setRequestHeaders(credential, connection, null);

            if (body != null) {
                connection.setDoOutput(true);
                outputStream = connection.getOutputStream();
                outputStream.write(body.getBytes());
            }

            BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {
                inputStream = connection.getInputStream();
                String response = bitcasaRESTUtility.getResponseFromInputStream(inputStream);
                BitcasaResponse bitcasaResponse = new Gson().fromJson(response,
                        BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    ItemList itemList = new Gson().fromJson(bitcasaResponse.getResult(),
                            ItemList.class);
                    ItemMeta meta = itemList.getMeta();
                    if (meta != null) {
                        if (meta.isFolder()) {
                            resultItem = new Folder(this, meta, destinationPath);
                        } else {
                            resultItem = new com.bitcasa.cloudfs.File(this, meta, destinationPath);
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
    public Item move(Item item, String destinationPath, String newName,
                     BitcasaRESTConstants.Exists exists)
            throws IOException, BitcasaException {

        if (stringParameterNotValid(destinationPath)) {
            throw new IllegalArgumentException(MISSING_PARAM + "destinationPath");
        }

        Item resultItem = null;

        StringBuilder request = new StringBuilder();
        if (item.getType().equals(Item.FileType.FOLDER)) {
            request.append(BitcasaRESTConstants.METHOD_FOLDERS);
        } else {
            request.append(BitcasaRESTConstants.METHOD_FILES);
        }

        String parent;
        if (item != null && item.getPath() != null) {
            parent = item.getPath();
        } else {
            parent = File.separator;
        }
        request.append(parent);

        TreeMap<String, String> queryParams = new TreeMap<String, String>();
        TreeMap<String, String> params = new TreeMap<String, String>();

        queryParams.put(BitcasaRESTConstants.PARAM_OPERATION,
                BitcasaRESTConstants.OPERATION_MOVE);
        params.put(BitcasaRESTConstants.BODY_TO, URLEncoder.encode(destinationPath,
                BitcasaRESTConstants.UTF_8_ENCODING));
        if (newName != null && newName.length() > 0) {
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

        String body = bitcasaRESTUtility.generateParamsString(params);

        String url = bitcasaRESTUtility.getRequestUrl(credential, request.toString(), null,
                queryParams);
        Log.d(TAG, "move: " + url + " body: " + body);
        HttpsURLConnection connection = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_POST);
            bitcasaRESTUtility.setRequestHeaders(credential, connection, null);

            if (body != null) {
                connection.setDoOutput(true);
                outputStream = connection.getOutputStream();
                outputStream.write(body.getBytes());
            }

            BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {
                inputStream = connection.getInputStream();
                String response = bitcasaRESTUtility.getResponseFromInputStream(inputStream);
                BitcasaResponse bitcasaResponse = new Gson().fromJson(response,
                        BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    ItemList itemList = new Gson().fromJson(bitcasaResponse.getResult(),
                            ItemList.class);
                    ItemMeta meta = itemList.getMeta();
                    if (meta != null) {
                        if (meta.isFolder()) {
                            resultItem = new Folder(this, meta, destinationPath);
                        } else {
                            resultItem = new com.bitcasa.cloudfs.File(this, meta, destinationPath);
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
     * Get Folder Meta.
     *
     * @param absolutePath String file location
     * @return Folder object at the given path.
     * @throws BitcasaAuthenticationException If user not authenticated
     * @throws IOException                    If a network error occurs
     * @throws BitcasaException               If a CloudFS API error occurs.
     */
    public Folder getFolderMeta(String absolutePath) throws
            BitcasaException, IOException {
        Folder meta = null;
        StringBuilder endpoint = new StringBuilder();
        endpoint.append(BitcasaRESTConstants.METHOD_FOLDERS);

        endpoint.append(absolutePath);
        String url = bitcasaRESTUtility.getRequestUrl(credential, endpoint.toString(),
                BitcasaRESTConstants.METHOD_META, null);

        Log.d(TAG, "getMeta URL: " + url);
        HttpsURLConnection connection = null;

        try {
            connection = (HttpsURLConnection) new URL(url)
                    .openConnection();
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_GET);
            bitcasaRESTUtility.setRequestHeaders(credential, connection, null);
            BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);

            if (error == null) {
                String response = bitcasaRESTUtility.getResponseFromInputStream(connection.getInputStream());
                BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    ItemList itemList = new Gson().fromJson(bitcasaResponse.getResult(),
                            ItemList.class);
                    meta = new Folder(this, itemList.getMeta(), absolutePath);
                } else {
                    throw new BitcasaException(bitcasaResponse.getError().getCode(),
                            bitcasaResponse.getError().getMessage());
                }
            }

        } catch (IOException ioe) {
            if (ioe.getMessage().contains("authentication challenge")) {
                throw new BitcasaAuthenticationException(1020, "Authorization code not recognized");
            } else {
                throw new BitcasaException(ioe);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }


        return meta;
    }

    /**
     * Get Item Meta.
     *
     * @param absolutePath String file location.
     * @return Item object at the given path.
     * @throws BitcasaAuthenticationException If user not authenticated.
     * @throws IOException                    If a network error occurs.
     * @throws BitcasaException               If a CloudFS API error occurs.
     */
    public Item getItemMeta(String absolutePath) throws
            BitcasaException, IOException {
        Item meta = null;
        StringBuilder endpoint = new StringBuilder();
        endpoint.append(BitcasaRESTConstants.METHOD_ITEM);
        endpoint.append(absolutePath);
        String url = bitcasaRESTUtility.getRequestUrl(credential, endpoint.toString(),
                BitcasaRESTConstants.METHOD_META, null);

        Log.d(TAG, "getMeta URL: " + url);
        HttpsURLConnection connection = null;

        try {
            connection = (HttpsURLConnection) new URL(url)
                    .openConnection();
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_GET);
            bitcasaRESTUtility.setRequestHeaders(credential, connection, null);
            BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);

            if (error == null) {
                String response = bitcasaRESTUtility.getResponseFromInputStream(connection.getInputStream());
                BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    ItemMeta itemMeta = new Gson().fromJson(bitcasaResponse.getResult(),
                            ItemMeta.class);
                    if (itemMeta.isFolder()) {
                        meta = new Folder(this, itemMeta, absolutePath);
                    } else {
                        meta = new com.bitcasa.cloudfs.File(this, itemMeta, absolutePath);
                    }

                } else {
                    throw new BitcasaException(bitcasaResponse.getError().getCode(),
                            bitcasaResponse.getError().getMessage());
                }
            }

        } catch (IOException ioe) {
            if (ioe.getMessage().contains("authentication challenge")) {
                throw new BitcasaAuthenticationException(1020, "Authorization code not recognized");
            } else {
                throw new BitcasaException(ioe);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return meta;
    }

    /**
     * Alter File Meta
     *
     * @param meta          Item object of file meta
     * @param changes       String map of meta changes
     * @param version       Integer version
     * @param versionExists VersionExists enum
     * @return Item object with altered meta
     * @throws BitcasaAuthenticationException If user not authenticated
     * @throws IOException                    If a network error occurs
     * @throws BitcasaException               If a CloudFS API error occurs.
     */
    public Item alterMeta(Item meta, Map<String, String> changes, int version,
                          VersionExists versionExists) throws BitcasaException, IOException {
        if (objectParameterNotValid(changes)) {
            throw new IllegalArgumentException(MISSING_PARAM + "changes");
        }

        Item item = null;
        StringBuilder endpoint = new StringBuilder();
        if (meta.getType().equals(Item.FileType.FOLDER)) {
            endpoint.append(BitcasaRESTConstants.METHOD_FOLDERS);
        } else if (meta.getType().equals(Item.FileType.FILE)) {
            endpoint.append(BitcasaRESTConstants.METHOD_FILES);
        }

        endpoint.append(meta.getPath());
        String url = bitcasaRESTUtility.getRequestUrl(credential, endpoint.toString(),
                BitcasaRESTConstants.METHOD_META, null);

        String versionConflict = BitcasaRESTConstants.VERSION_FAIL;
        if (versionExists != null && versionExists == BitcasaRESTConstants.VersionExists.IGNORE) {
            versionConflict = BitcasaRESTConstants.VERSION_IGNORE;
        }

        TreeMap<String, String> formParams = new TreeMap<String, String>();
        formParams.put(BitcasaRESTConstants.PARAM_VERSION, Integer.toString(version));
        if (versionExists != null) {
            formParams.put(BitcasaRESTConstants.PARAM_VERSION_CONFLICT, versionConflict);
        }

        formParams.putAll(changes);
        Log.d(TAG, "alterMeta URL: " + url);
        HttpsURLConnection connection = null;
        InputStream inputStream = null;
        OutputStream outputStream;

        try {
            connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_POST);
            bitcasaRESTUtility.setRequestHeaders(credential, connection, null);
            connection.setDoOutput(true);

            outputStream = connection.getOutputStream();
            outputStream.write(bitcasaRESTUtility.generateParamsString(formParams).getBytes());
            BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);

            if (error == null) {
                String response = bitcasaRESTUtility.getResponseFromInputStream(connection.getInputStream());
                BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    ItemList itemList = new Gson().fromJson(bitcasaResponse.getResult(),
                            ItemList.class);
                    ItemMeta itemMeta = itemList.getMeta();

                    if (itemMeta.isFolder()) {
                        item = new Folder(this, itemMeta, meta.getAbsoluteParentPath());
                    } else {
                        item = new com.bitcasa.cloudfs.File(this, itemMeta, meta.getAbsoluteParentPath());
                    }

                } else {
                    throw new BitcasaException(bitcasaResponse.getError().getCode(),
                            bitcasaResponse.getError().getMessage());
                }
            }

        } catch (IOException ioe) {
            if (ioe.getMessage().contains("authentication challenge")) {
                throw new BitcasaAuthenticationException(1020, "Authorization code not recognized");
            } else {
                throw new BitcasaException(ioe);
            }
        } catch (Exception e) {
            throw new BitcasaException(e);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

        return item;
    }

    /**
     * Alter File Meta
     *
     * @param meta          Item object of file meta
     * @param changes       String map of meta changes
     * @param version       Integer version
     * @param versionExists VersionExists enum
     * @return Item object with altered meta
     * @throws BitcasaAuthenticationException If user not authenticated
     * @throws IOException                    If a network error occurs
     * @throws BitcasaException               If a CloudFS API error occurs.
     */
    public com.bitcasa.cloudfs.File alterFileMeta(Item meta, Map<String, String> changes, int version,
                          VersionExists versionExists) throws BitcasaException, IOException {
        if (objectParameterNotValid(changes)) {
            throw new IllegalArgumentException(MISSING_PARAM + "changes");
        }

        com.bitcasa.cloudfs.File file = null;
        StringBuilder endpoint = new StringBuilder();
        endpoint.append(BitcasaRESTConstants.METHOD_FILES);
        endpoint.append(meta.getPath());
        String url = bitcasaRESTUtility.getRequestUrl(credential, endpoint.toString(),
                BitcasaRESTConstants.METHOD_META, null);

        String versionConflict = BitcasaRESTConstants.VERSION_FAIL;
        if (versionExists != null && versionExists == BitcasaRESTConstants.VersionExists.IGNORE) {
            versionConflict = BitcasaRESTConstants.VERSION_IGNORE;
        }

        TreeMap<String, String> formParams = new TreeMap<String, String>();
        formParams.put(BitcasaRESTConstants.PARAM_VERSION, Integer.toString(version));
        if (versionExists != null) {
            formParams.put(BitcasaRESTConstants.PARAM_VERSION_CONFLICT, versionConflict);
        }

        formParams.putAll(changes);
        Log.d(TAG, "alterFileMeta URL: " + url);
        HttpsURLConnection connection = null;
        InputStream inputStream = null;
        OutputStream outputStream;

        try {
            connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_POST);
            bitcasaRESTUtility.setRequestHeaders(credential, connection, null);
            connection.setDoOutput(true);

            outputStream = connection.getOutputStream();
            outputStream.write(bitcasaRESTUtility.generateParamsString(formParams).getBytes());
            BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);

            if (error == null) {
                String response = bitcasaRESTUtility.getResponseFromInputStream(connection.getInputStream());
                BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    ItemMeta itemMeta = new Gson().fromJson(bitcasaResponse.getResult(),
                            ItemMeta.class);
                    file = new com.bitcasa.cloudfs.File(this, itemMeta, meta.getAbsoluteParentPath());

                } else {
                    throw new BitcasaException(bitcasaResponse.getError().getCode(),
                            bitcasaResponse.getError().getMessage());
                }
            }

        } catch (IOException ioe) {
            if (ioe.getMessage().contains("authentication challenge")) {
                throw new BitcasaAuthenticationException(1020, "Authorization code not recognized");
            } else {
                throw new BitcasaException(ioe);
            }
        } catch (Exception e) {
            throw new BitcasaException(e);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

        return file;
    }

    /**
     * Alter Folder Meta
     *
     * @param meta          Item object of folder meta
     * @param changes       String map of meta changes
     * @param version       Integer version
     * @param versionExists VersionExists enum
     * @return Item object with altered meta
     * @throws BitcasaAuthenticationException If user not authenticated
     * @throws IOException                    If a network error occurs
     * @throws BitcasaException               If a CloudFS API error occurs.
     */
    public Folder alterFolderMeta(Item meta, Map<String, String> changes, int version,
                          VersionExists versionExists) throws BitcasaException, IOException {
        if (objectParameterNotValid(changes)) {
            throw new IllegalArgumentException(MISSING_PARAM + "changes");
        }

        Folder folder = null;
        StringBuilder endpoint = new StringBuilder();
        endpoint.append(BitcasaRESTConstants.METHOD_FOLDERS);
        endpoint.append(meta.getPath());
        String url = bitcasaRESTUtility.getRequestUrl(credential, endpoint.toString(),
                BitcasaRESTConstants.METHOD_META, null);

        String versionConflict = BitcasaRESTConstants.VERSION_FAIL;
        if (versionExists != null && versionExists == BitcasaRESTConstants.VersionExists.IGNORE) {
            versionConflict = BitcasaRESTConstants.VERSION_IGNORE;
        }

        TreeMap<String, String> formParams = new TreeMap<String, String>();
        formParams.put(BitcasaRESTConstants.PARAM_VERSION, Integer.toString(version));
        if (versionExists != null) {
            formParams.put(BitcasaRESTConstants.PARAM_VERSION_CONFLICT, versionConflict);
        }

        formParams.putAll(changes);
        Log.d(TAG, "alterFolderMeta URL: " + url);
        HttpsURLConnection connection = null;
        InputStream inputStream = null;
        OutputStream outputStream;

        try {
            connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_POST);
            bitcasaRESTUtility.setRequestHeaders(credential, connection, null);
            connection.setDoOutput(true);

            outputStream = connection.getOutputStream();
            outputStream.write(bitcasaRESTUtility.generateParamsString(formParams).getBytes());
            BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);

            if (error == null) {
                String response = bitcasaRESTUtility.getResponseFromInputStream(connection.getInputStream());
                BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    ItemList itemList = new Gson().fromJson(bitcasaResponse.getResult(),
                            ItemList.class);
                    ItemMeta itemMeta = itemList.getMeta();

                    folder = new Folder(this, itemMeta, meta.getAbsoluteParentPath());

                } else {
                    throw new BitcasaException(bitcasaResponse.getError().getCode(),
                            bitcasaResponse.getError().getMessage());
                }
            }

        } catch (IOException ioe) {
            if (ioe.getMessage().contains("authentication challenge")) {
                throw new BitcasaAuthenticationException(1020, "Authorization code not recognized");
            } else {
                throw new BitcasaException(ioe);
            }
        } catch (Exception e) {
            throw new BitcasaException(e);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

        return folder;
    }

    /**
     * List File Versions
     *
     * @param path         String file path
     * @param startVersion Integer starting file version
     * @param stopVersion  Integer Ending  version
     * @param limit        Integer file Limit
     * @return A list of file meta data results, as they have been recorded in the file version
     *         history after successful meta data changes.
     * @throws IOException      If a network error occurs.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public com.bitcasa.cloudfs.File[] listFileVersions(String path,
                                                       int startVersion,
                                                       int stopVersion,
                                                       int limit)
            throws BitcasaException, IOException {
        com.bitcasa.cloudfs.File[] versions = null;

        TreeMap<String, String> queryParams = new TreeMap<String, String>();
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

        String requestSegment = path + BitcasaRESTConstants.PARAM_VERSIONS;
        String url = bitcasaRESTUtility.getRequestUrl(credential, BitcasaRESTConstants.METHOD_FILES,
                requestSegment, queryParams.size() > 0 ? queryParams : null);

        Log.d(TAG, "listFileVersions: " + url);
        HttpsURLConnection connection = null;
        InputStream inputStream = null;

        try {
            connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_GET);
            bitcasaRESTUtility.setRequestHeaders(credential, connection, null);
            BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);

            if (error == null) {

                String response = bitcasaRESTUtility.getResponseFromInputStream(connection.getInputStream());
                BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    versions = new Gson().fromJson(bitcasaResponse.getResult(),
                            com.bitcasa.cloudfs.File[].class);

                } else {
                    throw new BitcasaException(bitcasaResponse.getError().getCode(),
                            bitcasaResponse.getError().getMessage());
                }
            }
        } catch (IOException ioe) {
            if (ioe.getMessage().contains("authentication challenge")) {
                throw new BitcasaAuthenticationException(1020, "Authorization code not recognized");
            } else {
                throw new BitcasaException(ioe);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

        return versions;
    }

    /**
     * List History
     *
     * @param startVersion Integer starting file version
     * @param stopVersion  Integer Ending  version
     * @return ActionHistory object
     * @throws IOException      If a network error occurs
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public List<BaseAction> listHistory(int startVersion, int stopVersion)
            throws IOException, BitcasaException {
        ArrayList<BaseAction> actions = new ArrayList<BaseAction>();
        HashMap<String, String> queryParams = new HashMap<String, String>();
        if (startVersion >= 0) {
            queryParams.put(BitcasaRESTConstants.PARAM_START, Integer.toString(startVersion));
        }
        if (stopVersion >= 0) {
            queryParams.put(BitcasaRESTConstants.PARAM_STOP, Integer.toString(stopVersion));
        }

        String url = bitcasaRESTUtility.getRequestUrl(credential,
                BitcasaRESTConstants.METHOD_HISTORY, null, (queryParams.size() > 0) ?
                        queryParams : null);

        Log.d(TAG, "listHistory URL: " + url);
        HttpsURLConnection connection = null;
        InputStream inputStream = null;

        try {
            connection = (HttpsURLConnection) new URL(url)
                    .openConnection();
            connection.setDoInput(true);
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_GET);
            bitcasaRESTUtility.setRequestHeaders(credential, connection, null);

            BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {
                inputStream = connection.getInputStream();
                String response = bitcasaRESTUtility.getResponseFromInputStream(connection.getInputStream());
                BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);
                if (!bitcasaResponse.getResult().isJsonNull()) {
                    ArrayList<JsonObject> results = new ArrayList<JsonObject>();
                    for (int i = 0; i < bitcasaResponse.getResult().getAsJsonArray().size(); i++) {
                        results.add(bitcasaResponse.getResult().getAsJsonArray().get(i).getAsJsonObject());
                    }

                    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                    for (JsonObject result : results) {
                        BaseAction action = gson.fromJson(result, BaseAction.class);
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
        } catch (IOException ioe) {
            throw new BitcasaException(ioe);
        } catch (Exception e) {
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
     * @return list of ShareItem
     * @throws IOException      If a network error occurs
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public Share[] listShare() throws IOException, BitcasaException {
        ArrayList<Share> listShares = new ArrayList<Share>();
        String url = bitcasaRESTUtility.getRequestUrl(credential,
                BitcasaRESTConstants.METHOD_SHARES, null, null);

        Log.d(TAG, "listShare URL: " + url);
        HttpsURLConnection connection = null;

        try {
            connection = (HttpsURLConnection) new URL(url)
                    .openConnection();
            connection.setDoInput(true);
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_GET);
            bitcasaRESTUtility.setRequestHeaders(credential, connection, null);

            BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);

            if (error == null) {
                String response = bitcasaRESTUtility.getResponseFromInputStream(connection.getInputStream());
                BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    ShareItem[] shareItem = new Gson().fromJson(bitcasaResponse.getResult(),
                            ShareItem[].class);

                    for (ShareItem item : shareItem) {
                        listShares.add(new Share(this, item, null));
                    }

                } else {
                    throw new BitcasaException(bitcasaResponse.getError().getCode(),
                            bitcasaResponse.getError().getMessage());
                }
            }

        } catch (IOException ioe) {
            throw new BitcasaException(ioe);
        } catch (Exception e) {
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
     * @param path     String file path
     * @param password String password
     * @return Share object created
     * @throws IOException      If a network error occurs
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public Share createShare(String path, String password) throws IOException, BitcasaException {
        if (stringParameterNotValid(path)) {
            throw new IllegalArgumentException(MISSING_PARAM + "path");
        }
        Share share = null;
        String url = bitcasaRESTUtility.getRequestUrl(credential,
                BitcasaRESTConstants.METHOD_SHARES, null, null);

        Log.d(TAG, "createShare URL: " + url);
        HttpsURLConnection connection = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        TreeMap<String, String> formParams = new TreeMap<String, String>();
        formParams.put(BitcasaRESTConstants.BODY_PATH, URLEncoder.encode(path,
                BitcasaRESTConstants.UTF_8_ENCODING));
        if (password != null) {
            formParams.put(BitcasaRESTConstants.PARAM_PASSWORD, URLEncoder.encode(password,
                    BitcasaRESTConstants.UTF_8_ENCODING));
        }

        try {
            connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_POST);
            connection.setReadTimeout(300000); // Constants
            connection.setConnectTimeout(300000);
            connection.setUseCaches(false);
            bitcasaRESTUtility.setRequestHeaders(credential, connection, null);
            String parameters = bitcasaRESTUtility.generateParamsString(formParams);
            if (parameters != null) {
                outputStream = connection.getOutputStream();
                Log.d(TAG, "POST string: " + parameters);
                outputStream.write(parameters.getBytes());
            }

            BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {
                inputStream = connection.getInputStream();
                String response = bitcasaRESTUtility.getResponseFromInputStream(connection.getInputStream());
                BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    ShareItem shareItem = new Gson().fromJson(bitcasaResponse.getResult(),
                            ShareItem.class);
                    share = new Share(this, shareItem, null);
                } else {
                    throw new BitcasaException(bitcasaResponse.getError().getCode(),
                            bitcasaResponse.getError().getMessage());
                }
            }

        } catch (IOException ioe) {
            throw new BitcasaException(ioe);
        } catch (Exception e) {
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
     * Given the sharekey and the path to any folder/file under share, browseShare method will
     * return the item list for that share.
     * Make sure unlockShare is called before browseShare
     *
     * @param shareKey String share key of the share
     * @param path     String file path
     * @return Share object
     * @throws IOException      If a network error occurs
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public Item[] browseShare(String shareKey, String path) throws IOException, BitcasaException {
        if (stringParameterNotValid(shareKey)) {
            throw new IllegalArgumentException(MISSING_PARAM + "shareKey");
        }
        SharedFolder sharedFolder = null;
        ArrayList<Item> items = new ArrayList<Item>();

        StringBuilder sb = new StringBuilder();
        sb.append(shareKey);
        if (path != null) {
            sb.append(path);
        }
        sb.append(BitcasaRESTConstants.METHOD_META);
        String url = bitcasaRESTUtility.getRequestUrl(credential,
                BitcasaRESTConstants.METHOD_SHARES, sb.toString(), null);

        Log.d(TAG, "browseShare URL: " + url);
        HttpsURLConnection connection = null;
        InputStream inputStream = null;

        try {
            connection = (HttpsURLConnection) new URL(url)
                    .openConnection();
            connection.setDoInput(true);
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_GET);
            bitcasaRESTUtility.setRequestHeaders(credential, connection, null);

            BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {
                inputStream = connection.getInputStream();
                String response = bitcasaRESTUtility.getResponseFromInputStream(inputStream);
                BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    sharedFolder = new Gson().fromJson(bitcasaResponse.getResult(), SharedFolder.class);

                    for (ItemMeta meta : sharedFolder.getItems()) {
                        if (meta.isFolder()) {
                            items.add(new Folder(this, meta, null));
                        } else {
                            items.add(new com.bitcasa.cloudfs.File(this, meta, null));
                        }
                    }
                } else {
                    throw new BitcasaException(bitcasaResponse.getError().getCode(),
                            bitcasaResponse.getError().getMessage());
                }
            }
        } catch (IOException ioe) {
            throw new BitcasaException(ioe);
        } catch (Exception e) {
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
     * the sharekey will be inserted into the given location.
     * File collisions will be handled with the exist action specified.
     *
     * @param shareKey          String share key of the share
     * @param pathToInsertShare String path to insert share
     * @param exists            Exists enum
     * @return An array of container object
     * @throws IOException      If a network error occurs
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public Item[] receiveShare(String shareKey, String pathToInsertShare,
                               BitcasaRESTConstants.Exists exists)
            throws IOException, BitcasaException {
        if (stringParameterNotValid(shareKey)) {
            throw new IllegalArgumentException(MISSING_PARAM + "shareKey");
        }
        SharedFolder sharedFolder = null;
        ArrayList<Item> items = new ArrayList<Item>();
        String url = bitcasaRESTUtility.getRequestUrl(credential,
                BitcasaRESTConstants.METHOD_SHARES, shareKey + File.separator, null);

        Log.d(TAG, "receiveShare URL: " + url);
        HttpsURLConnection connection = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        TreeMap<String, String> formParams = new TreeMap<String, String>();
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
                case RENAME:
                default:
                    formParams.put(BitcasaRESTConstants.BODY_EXISTS,
                            BitcasaRESTConstants.EXISTS_RENAME);
                    break;
            }
        }

        try {
            connection = (HttpsURLConnection) new URL(url)
                    .openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_POST);
            connection.setReadTimeout(300000);
            connection.setConnectTimeout(300000);
            connection.setUseCaches(false);
            bitcasaRESTUtility.setRequestHeaders(credential, connection, null);
            String parameters = bitcasaRESTUtility.generateParamsString(formParams);
            if (parameters != null) {
                outputStream = connection.getOutputStream();
                Log.d(TAG, "POST string: " + parameters);
                outputStream.write(parameters.getBytes());
            }

            BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {
                inputStream = connection.getInputStream();
                String response = bitcasaRESTUtility.getResponseFromInputStream(inputStream);
                BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    sharedFolder = new Gson().fromJson(bitcasaResponse.getResult(), SharedFolder.class);

                    for (ItemMeta meta : sharedFolder.getItems()) {
                        if (meta.isFolder()) {
                            items.add(new Folder(this, meta, pathToInsertShare));
                        } else {
                            items.add(new com.bitcasa.cloudfs.File(this, meta, pathToInsertShare));
                        }
                    }
                } else {
                    throw new BitcasaException(bitcasaResponse.getError().getCode(),
                            bitcasaResponse.getError().getMessage());
                }
            }
        } catch (IOException ioe) {
            throw new BitcasaException(ioe);
        } catch (Exception e) {
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
     * Given a valid share and its password, this entrypoint will unlock the share for the login
     * session.
     *
     * @param shareKey String share key of the share
     * @param password String password
     * @return boolean of whether the share was unlocked or not
     * @throws IOException      If a network error occurs
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public boolean unlockShare(String shareKey, String password)
            throws IOException, BitcasaException {
        boolean booleanResult = false;

        String sb = shareKey + BitcasaRESTConstants.METHOD_UNLOCK;
        String url = bitcasaRESTUtility.getRequestUrl(credential,
                BitcasaRESTConstants.METHOD_SHARES, sb, null);


        Log.d(TAG, "unlockShare URL: " + url);
        HttpsURLConnection connection = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        TreeMap<String, String> formParams = new TreeMap<String, String>();
        formParams.put(BitcasaRESTConstants.PARAM_PASSWORD, URLEncoder.encode(password,
                BitcasaRESTConstants.UTF_8_ENCODING));

        try {
            connection = (HttpsURLConnection) new URL(url)
                    .openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_POST);
            connection.setReadTimeout(300000);
            connection.setConnectTimeout(300000);
            connection.setUseCaches(false);
            bitcasaRESTUtility.setRequestHeaders(credential, connection, null);
            String parameters = bitcasaRESTUtility.generateParamsString(formParams);
            if (parameters != null) {
                outputStream = connection.getOutputStream();
                Log.d(TAG, "POST string: " + parameters);
                outputStream.write(parameters.getBytes());
            }

            BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {
                inputStream = connection.getInputStream();
                String response = bitcasaRESTUtility.getResponseFromInputStream(connection.getInputStream());
                BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    booleanResult = true;
                } else {
                    throw new BitcasaException(bitcasaResponse.getError().getCode(),
                            bitcasaResponse.getError().getMessage());
                }
            }

        } catch (IOException ioe) {
            throw new BitcasaException(ioe);
        } catch (Exception e) {
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
     * Alter Share Info
     *
     * @param shareKey        String share key of the share
     * @param currentPassword The current password of the share
     * @param newPassword     The new password of the share
     * @return Share object of the altered share info
     * @throws IOException      If a network error occurs
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public Share alterShareInfo(String shareKey, String currentPassword, String newPassword)
            throws IOException, BitcasaException {
        Share share = null;

        String sb = shareKey + BitcasaRESTConstants.METHOD_INFO;
        String url = bitcasaRESTUtility.getRequestUrl(credential,
                BitcasaRESTConstants.METHOD_SHARES, sb, null);

        Log.d(TAG, "alterShareInfo URL: " + url);
        HttpsURLConnection connection = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        TreeMap<String, String> formParams = new TreeMap<String, String>();
        formParams.put(BitcasaRESTConstants.PARAM_CURRENT_PASSWORD,
                URLEncoder.encode(currentPassword, BitcasaRESTConstants.UTF_8_ENCODING));
        formParams.put(BitcasaRESTConstants.PARAM_PASSWORD, URLEncoder.encode(newPassword,
                BitcasaRESTConstants.UTF_8_ENCODING));

        try {
            connection = (HttpsURLConnection) new URL(url)
                    .openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_POST);
            connection.setReadTimeout(300000);
            connection.setConnectTimeout(300000);
            connection.setUseCaches(false);
            bitcasaRESTUtility.setRequestHeaders(credential, connection, null);
            String parameters = bitcasaRESTUtility.generateParamsString(formParams);
            if (parameters != null) {
                outputStream = connection.getOutputStream();
                Log.d(TAG, "POST string: " + parameters);
                outputStream.write(parameters.getBytes());
            }

            BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {
                inputStream = connection.getInputStream();
                String response = bitcasaRESTUtility.getResponseFromInputStream(inputStream);
                BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    ShareItem shareItem = new Gson().fromJson(bitcasaResponse.getResult(),
                            ShareItem.class);
                    share = new Share(this, shareItem, null);
                } else {
                    throw new BitcasaException(bitcasaResponse.getError().getCode(),
                            bitcasaResponse.getError().getMessage());
                }
            }

        } catch (IOException ioe) {
            throw new BitcasaException(ioe);
        } catch (Exception e) {
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
     * Alters the Shares attributes.
     *
     * @param shareKey        The share key of the specified share.
     * @param changes         The changes to be updated.
     * @param currentPassword The current password of the share.
     * @return Share object of the altered share
     * @throws IOException      If a network error occurs
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public Share alterShare(String shareKey, Map<String, String> changes, String currentPassword)
            throws IOException, BitcasaException {
        if (objectParameterNotValid(changes)) {
            throw new IllegalArgumentException(MISSING_PARAM + "changes");
        }
        Share share = null;

        String sb = shareKey + BitcasaRESTConstants.METHOD_INFO;
        String url = bitcasaRESTUtility.getRequestUrl(credential,
                BitcasaRESTConstants.METHOD_SHARES, sb, null);

        Log.d(TAG, "alterShare URL: " + url);
        HttpsURLConnection connection = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        TreeMap<String, String> formParams = new TreeMap<String, String>();
        formParams.put(BitcasaRESTConstants.PARAM_CURRENT_PASSWORD,
                URLEncoder.encode(currentPassword, BitcasaRESTConstants.UTF_8_ENCODING));
        formParams.putAll(changes);
        try {
            connection = (HttpsURLConnection) new URL(url)
                    .openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_POST);
            connection.setReadTimeout(300000);
            connection.setConnectTimeout(300000);
            connection.setUseCaches(false);
            bitcasaRESTUtility.setRequestHeaders(credential, connection, null);
            String parameters = bitcasaRESTUtility.generateParamsString(formParams);
            if (parameters != null) {
                outputStream = connection.getOutputStream();
                Log.d(TAG, "POST string: " + parameters);
                outputStream.write(parameters.getBytes());
            }

            BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {
                inputStream = connection.getInputStream();
                String response = bitcasaRESTUtility.getResponseFromInputStream(inputStream);
                BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);

                if (!bitcasaResponse.getResult().isJsonNull()) {
                    ShareItem shareItem = new Gson().fromJson(bitcasaResponse.getResult(),
                            ShareItem.class);
                    share = new Share(this, shareItem, null);
                } else {
                    throw new BitcasaException(bitcasaResponse.getError().getCode(),
                            bitcasaResponse.getError().getMessage());
                }
            }

        } catch (IOException ioe) {
            throw new BitcasaException(ioe);
        } catch (Exception e) {
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
     * Delete Share
     *
     * @param shareKey String share key of the share
     * @return Boolean if the share was deleted or not
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public boolean deleteShare(String shareKey) throws BitcasaException {
        boolean booleanResult = false;

        String sb = shareKey + File.separator;
        String url = bitcasaRESTUtility.getRequestUrl(credential,
                BitcasaRESTConstants.METHOD_SHARES, sb, null);

        Log.d(TAG, "deleteShare URL: " + url);
        HttpsURLConnection connection = null;
        try {
            connection = (HttpsURLConnection) new URL(url)
                    .openConnection();
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_DELETE);
            bitcasaRESTUtility.setRequestHeaders(credential, connection, null);

            BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {
                booleanResult = true;
            }

        } catch (IOException ioe) {
            throw new BitcasaException(ioe);
        } catch (Exception e) {
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
     * Browse Trash
     *
     * @return An array of item objects
     * @throws IOException      If a network error occurs
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public Item[] browseTrash() throws IOException, BitcasaException {
        ArrayList<Item> trash = new ArrayList<Item>();
        String url = bitcasaRESTUtility.getRequestUrl(credential,
                BitcasaRESTConstants.METHOD_TRASH, null, null);

        Log.d(TAG, "browseTrash URL: " + url);
        HttpsURLConnection connection = null;

        try {
            connection = (HttpsURLConnection) new URL(url)
                    .openConnection();
            connection.setDoInput(true);
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_GET);
            bitcasaRESTUtility.setRequestHeaders(credential, connection, null);
            BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);

            if (error == null) {
                String response = bitcasaRESTUtility.getResponseFromInputStream(connection.getInputStream());
                BitcasaResponse bitcasaResponse = new Gson().fromJson(response, BitcasaResponse.class);
                if (!bitcasaResponse.getResult().isJsonNull()) {
                    ItemList itemList = new Gson().fromJson(bitcasaResponse.getResult(),
                            ItemList.class);

                    for (ItemMeta meta : itemList.getItems()) {
                        if (meta.isFolder()) {
                            trash.add(new Folder(this, meta, null));
                        } else {
                            trash.add(new com.bitcasa.cloudfs.File(this, meta, null));
                        }
                    }

                } else {
                    throw new BitcasaException(bitcasaResponse.getError().getCode(),
                            bitcasaResponse.getError().getMessage());
                }
            }


        } catch (IOException ioe) {
            throw new BitcasaException(ioe);
        } catch (Exception e) {
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
     * @param path                 String file path
     * @param restoreMethod        RestoreMethod enum
     * @param rescueOrRecreatePath String path to rescue or recreate file
     * @return Boolean whether the item was recovered from trash or not
     * @throws UnsupportedEncodingException If encoding not supported
     * @throws BitcasaException             If a CloudFS API error occurs.
     */
    public boolean recoverTrashItem(String path, BitcasaRESTConstants.RestoreMethod restoreMethod,
                                    String rescueOrRecreatePath) throws
            UnsupportedEncodingException, BitcasaException {
        if (stringParameterNotValid(path)) {
            throw new IllegalArgumentException(MISSING_PARAM + "path");
        }
        boolean booleanResult = false;
        StringBuilder sb = new StringBuilder();
        if (path.startsWith(File.separator)) {
            sb.append(path.substring(1));
        } else {
            sb.append(path);
        }
        String url = bitcasaRESTUtility.getRequestUrl(credential, BitcasaRESTConstants.METHOD_TRASH,
                sb.toString(), null);

        TreeMap<String, String> formParams;

        formParams = new TreeMap<String, String>();
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


        Log.d(TAG, "recoverTrashItem URL: " + url);
        HttpsURLConnection connection = null;
        OutputStream outputStream;
        try {
            connection = (HttpsURLConnection) new URL(url)
                    .openConnection();
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_POST);
            bitcasaRESTUtility.setRequestHeaders(credential, connection, null);
            connection.setDoOutput(true);
            outputStream = connection.getOutputStream();

            String body = bitcasaRESTUtility.generateParamsString(formParams);
            Log.d("recoverTrashItem", "formParams: " + body);
            outputStream.write(body.getBytes());

            BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {
                booleanResult = true;
            }

        } catch (IOException ioe) {
            throw new BitcasaException(ioe);
        } catch (Exception e) {
            throw new BitcasaException(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return booleanResult;
    }

    /**
     * Delete Trash Item
     *
     * @param path String file path
     * @return Boolean whether the item in trash was deleted or not
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public boolean deleteTrashItem(String path) throws BitcasaException {
        boolean booleanResult = false;

        String url = bitcasaRESTUtility.getRequestUrl(credential, BitcasaRESTConstants.METHOD_TRASH,
                path, null);

        Log.d(TAG, "deleteTrashItem URL: " + url);
        HttpsURLConnection connection = null;
        try {
            connection = (HttpsURLConnection) new URL(url)
                    .openConnection();
            connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_DELETE);
            bitcasaRESTUtility.setRequestHeaders(credential, connection, null);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);
            if (error == null) {
                booleanResult = true;
            }

        } catch (IOException ioe) {
            throw new BitcasaException(ioe);
        } catch (Exception e) {
            throw new BitcasaException(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return booleanResult;
    }
    //endregion
}
