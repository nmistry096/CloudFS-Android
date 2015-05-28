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
import android.util.Base64;
import android.util.Log;

import com.bitcasa.cloudfs.BitcasaError;
import com.bitcasa.cloudfs.Credential;
import com.bitcasa.cloudfs.Session;
import com.bitcasa.cloudfs.Utils.BitcasaRESTConstants;
import com.bitcasa.cloudfs.model.BitcasaResponse;
import com.bitcasa.cloudfs.model.ResponseError;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Set;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;

/**
 * The BitcasaRESTUtility class provides utility methods to communicate to the CloudFS Rest Api.
 */
public class BitcasaRESTUtility {

    /**
     * Class name constant used for logging.
     */
    public static final String TAG = BitcasaRESTUtility.class.getSimpleName();

    /**
     * Get the Request URL
     *
     * @param credential  Application Credentials.
     * @param request     The request information.
     * @param method      The request method.
     * @param queryParams Query parameters.
     * @return The requested url.
     */
    public String getRequestUrl(final Credential credential, final String request, final String method, final Map<String, String> queryParams) {
        final StringBuilder url = new StringBuilder();
        url.append(BitcasaRESTConstants.HTTPS);
        url.append(credential.getEndPoint())
                .append(BitcasaRESTConstants.API_VERSION_2)
                .append(request);

        if (method != null) {
            url.append(method);
        }

        if ((queryParams != null) && !queryParams.isEmpty()) {
            url.append('?');
            url.append(this.generateParamsString(queryParams));
        }

        return url.toString();
    }

    /**
     * Generate parameter string from a map, encoding need to be done before calling
     * generateParamsString.
     *
     * @param params Parameter Strings.
     * @return The generated parameters.
     */
    public String generateParamsString(final Map<String, ?> params) {
        String query = null;
        if ((params != null) && (!params.isEmpty())) {
            final StringBuilder paramsString = new StringBuilder();
            final Set<String> keys = params.keySet();
            for (final String key : keys) {
                final Object value = params.get(key);

                if (value instanceof String) {
                    paramsString
                            .append(key)
                            .append('=')
                            .append(this.replaceSpaceWithPlus(value.toString()))
                            .append('&');
                } else if (value instanceof String[]) {
                    for (String path : (String[]) value) {
                        paramsString
                                .append(key)
                                .append('=')
                                .append(this.replaceSpaceWithPlus(path.toString()))
                                .append('&');
                    }
                }

            }

            if (paramsString.charAt(paramsString.length() - 1) == '&') {
                paramsString.deleteCharAt(paramsString.length() - 1);
            }
            query = paramsString.toString();
        }
        return query;
    }

    /**
     * Sets the request Headers
     *
     * @param credential Application Credentials.
     * @param connection HttpURLConnection object.
     * @param headers    Map of headers.
     */
    public void setRequestHeaders(final Credential credential, final HttpURLConnection connection,
                                  final Map<String, String> headers) {
        if (headers != null) {
            final Set<String> headerFields = headers.keySet();
            for (final String header : headerFields) {
                connection.setRequestProperty(header, headers.get(header));
            }
        }

        final String tokenType = credential.getTokenType().substring(0, 1)
                .toUpperCase()
                + credential.getTokenType().substring(1);

        // tag on default headers
        final String accessToken = tokenType + ' ' + credential.getAccessToken();
        connection.setRequestProperty(BitcasaRESTConstants.HEADER_AUTORIZATION, accessToken);
        Log.d("setRequestHeaders", "we should set the auth header: " + accessToken);
    }

    /**
     * Creates a sha1 encoding.
     *
     * @param s         The encoding value.
     * @param keyString The encoding key.
     * @return The encoded value.
     * @throws UnsupportedEncodingException If encoding not supported.
     * @throws NoSuchAlgorithmException     If the algorithm does not exist.
     * @throws InvalidKeyException          If the key provided is invalid.
     */
    public String sha1(final String s, final String keyString) throws
            UnsupportedEncodingException, NoSuchAlgorithmException,
            InvalidKeyException {

        final Key key = new SecretKeySpec(keyString.getBytes(), "HmacSHA1");
        final Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(key);

        final byte[] bytes = mac.doFinal(s.getBytes("UTF-8"));

        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

    public String replaceSpaceWithPlus(final String s) {
        return s.replace(" ", "+");
    }

    /**
     * Generates the authorization value
     *
     * @param session The current session object.
     * @param params  The uri value.
     * @param date    The date and time.
     * @return String of the authorization value
     * @throws InvalidKeyException          If the key provided is invalid.
     * @throws UnsupportedEncodingException If encoding not supported.
     * @throws NoSuchAlgorithmException     If the algorithm does not exist.
     */
    public String generateAuthorizationValue(final Session session, final String uri, final String params, final String date) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException {
        final StringBuilder stringToSign = new StringBuilder();
        stringToSign
                .append(BitcasaRESTConstants.REQUEST_METHOD_POST)
                .append('&')
                .append(uri)
                .append('&')
                .append(params)
                .append('&')
                .append(this.replaceSpaceWithPlus(Uri.encode(BitcasaRESTConstants.HEADER_CONTENT_TYPE, " "))).append(':')
                .append(this.replaceSpaceWithPlus(Uri.encode(BitcasaRESTConstants.FORM_URLENCODED, " ")))
                .append('&')
                .append(this.replaceSpaceWithPlus(Uri.encode(BitcasaRESTConstants.HEADER_DATE, " "))).append(':')
                .append(this.replaceSpaceWithPlus(Uri.encode(date, " ")));
        Log.d(BitcasaRESTUtility.TAG, stringToSign.toString());

        return "BCS " + session.getClientId() + ':' + this.sha1(stringToSign.toString(), session.getClientSecret());
    }

    /**
     * Generate admin authorization value
     *
     * @param session Session object
     * @param params  String of parameters
     * @param date    String of date
     * @return String of the authorization value
     * @throws InvalidKeyException          If the key provided is invalid
     * @throws UnsupportedEncodingException If encoding not supported
     * @throws NoSuchAlgorithmException     If the algorithm does not exist
     */
    public String generateAdminAuthorizationValue(final Session session, final String uri, final String params, final String date) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException {
        final StringBuilder stringToSign = new StringBuilder();
        stringToSign
                .append(BitcasaRESTConstants.REQUEST_METHOD_POST)
                .append('&')
                .append(uri)
                .append('&')
                .append(params)
                .append('&')
                .append(this.replaceSpaceWithPlus(Uri.encode(BitcasaRESTConstants.HEADER_CONTENT_TYPE, " "))).append(':')
                .append(this.replaceSpaceWithPlus(Uri.encode(BitcasaRESTConstants.FORM_URLENCODED, " ")))
                .append('&')
                .append(this.replaceSpaceWithPlus(Uri.encode(BitcasaRESTConstants.HEADER_DATE, " "))).append(':')
                .append(this.replaceSpaceWithPlus(Uri.encode(date, " ")));
        Log.d(BitcasaRESTUtility.TAG, stringToSign.toString());

        return "BCS " + session.getAdminClientId() + ':' + this.sha1(stringToSign.toString(), session.getAdminClientSecret());
    }

    /**
     * Generate admin authorization value
     *
     * @param session Session object
     * @param uri  String uri
     * @param date    String of date
     * @return String of the authorization value
     * @throws InvalidKeyException          If the key provided is invalid
     * @throws UnsupportedEncodingException If encoding not supported
     * @throws NoSuchAlgorithmException     If the algorithm does not exist
     */
    public String generateAdminAuthorizationValue(final String requestMethod, final Session session, final String uri, final String date)
            throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException {
        final StringBuilder stringToSign = new StringBuilder();
        stringToSign
                .append(requestMethod)
                .append('&')
                .append(uri)
                .append('&')
                .append(this.replaceSpaceWithPlus(Uri.encode(BitcasaRESTConstants.HEADER_CONTENT_TYPE, " "))).append(':')
                .append(this.replaceSpaceWithPlus(Uri.encode(BitcasaRESTConstants.FORM_URLENCODED, " ")))
                .append('&')
                .append(this.replaceSpaceWithPlus(Uri.encode(BitcasaRESTConstants.HEADER_DATE, " "))).append(':')
                .append(this.replaceSpaceWithPlus(Uri.encode(date, " ")));
        Log.d(BitcasaRESTUtility.TAG, stringToSign.toString());

        return "BCS " + session.getAdminClientId() + ':' + this.sha1(stringToSign.toString(), session.getAdminClientSecret());
    }

    /**
     * Creates a BitcasaError if server responds with an error code.
     *
     * @param connection The HttpsURLConnection which contains the status code and error.
     * @throws IOException Occurs if the server response can not be processed.
     */
    public BitcasaError checkRequestResponse(final HttpsURLConnection connection) throws IOException {
        BitcasaError error = null;
        InputStream inputStream = null;

        try {
            final int responseCode = connection.getResponseCode();

            Log.d(BitcasaRESTUtility.TAG, "checkRequestResponse: Response code is: " + responseCode);

            if (!((responseCode == HttpsURLConnection.HTTP_OK) ||
                    (responseCode == HttpsURLConnection.HTTP_PARTIAL))) {
                inputStream = connection.getErrorStream();

                final BitcasaRESTUtility restUtility = new BitcasaRESTUtility();
                final String response = restUtility.getResponseFromInputStream(inputStream);
                final BitcasaResponse bitcasasResponse = new Gson().fromJson(response,
                        BitcasaResponse.class);
                final ResponseError responseError = bitcasasResponse.getError();

                final JsonElement errorDataElement = responseError.getData();
                String errorData = null;
                if (!errorDataElement.isJsonNull()) {
                    errorData = errorDataElement.getAsString();
                }
                error = new BitcasaError(responseError.getCode(), responseError.getMessage(),
                        errorData);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return error;
    }

    /**
     * Gets the JSON response from a given input stream.
     *
     * @param inputStream The input stream to be read.
     * @return The processed JSON string.
     * @throws IOException If a network error occurs.
     */
    public String getResponseFromInputStream(final InputStream inputStream) throws IOException {
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        final StringBuilder responseBuilder = new StringBuilder();
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                responseBuilder.append(line);
            }
        } finally {
            bufferedReader.close();
        }

        return responseBuilder.toString();
    }

}
