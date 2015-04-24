/**
 * Bitcasa Client Android SDK
 * Copyright (C) 2015 Bitcasa, Inc.
 * 215 Castro Street, 2nd Floor
 * Mountain View, CA 94041
 *
 * This file contains an SDK in Java for accessing the Bitcasa infinite drive in Android platform.
 *
 * For support, please send email to support@bitcasa.com.
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
    public String getRequestUrl(Credential credential, String request, String method, Map<String, String> queryParams) {
        StringBuilder url = new StringBuilder();
        url.append(BitcasaRESTConstants.HTTPS);
        url.append(credential.getEndPoint())
                .append(BitcasaRESTConstants.API_VERSION_2)
                .append(request);

        if (method != null) {
            url.append(method);
        }

        if (queryParams != null && !queryParams.isEmpty()) {
            url.append("?");
            url.append(generateParamsString(queryParams));
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
    public String generateParamsString(Map<String, String> params) {
        String query = null;
        if (params != null && params.size() > 0) {
            StringBuilder paramsString = new StringBuilder();
            Set<String> keys = params.keySet();
            for (String key : keys) {
                Object value = params.get(key);
                paramsString
                        .append(key)
                        .append("=")
                        .append(replaceSpaceWithPlus(value.toString()))
                        .append("&");

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
    public void setRequestHeaders(Credential credential, HttpURLConnection connection,
                                  Map<String, String> headers) {
        if (headers != null) {
            Set<String> headerFields = headers.keySet();
            for (String header : headerFields) {
                connection.setRequestProperty(header, headers.get(header));
            }
        }

        final String tokenType = credential.getTokenType().substring(0, 1)
                .toUpperCase()
                + credential.getTokenType().substring(1);

        // tag on default headers
        String accessToken = tokenType + " " + credential.getAccessToken();
        connection.setRequestProperty(BitcasaRESTConstants.HEADER_AUTORIZATION, accessToken);
        Log.d("setRequestHeaders", "we should set the auth header: " + accessToken);
    }

    /**
     * @param s
     * @param keyString
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public String sha1(String s, String keyString) throws
            UnsupportedEncodingException, NoSuchAlgorithmException,
            InvalidKeyException {

        SecretKeySpec key = new SecretKeySpec((keyString).getBytes(), "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(key);

        byte[] bytes = mac.doFinal(s.getBytes("UTF-8"));

        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

    public String replaceSpaceWithPlus(String s) {
        return s.replace(" ", "+");
    }

    /**
     * Generates the authorization value
     *
     * @param session The current session object.
     * @param params  The uri value.
     * @param date    The date and time.
     * @return String of the authorization value
     * @throws InvalidKeyException          If the key provided is invalid
     * @throws UnsupportedEncodingException If encoding not supported
     * @throws NoSuchAlgorithmException     If the algorithm does not exist
     */
    public String generateAuthorizationValue(Session session, String uri, String params, String date) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException {
        final StringBuilder stringToSign = new StringBuilder();
        stringToSign
                .append(BitcasaRESTConstants.REQUEST_METHOD_POST)
                .append("&")
                .append(uri)
                .append("&")
                .append(params)
                .append("&")
                .append(replaceSpaceWithPlus(Uri.encode(BitcasaRESTConstants.HEADER_CONTENT_TYPE, " "))).append(":")
                .append(replaceSpaceWithPlus(Uri.encode(BitcasaRESTConstants.FORM_URLENCODED, " ")))
                .append("&")
                .append(replaceSpaceWithPlus(Uri.encode(BitcasaRESTConstants.HEADER_DATE, " "))).append(":")
                .append(replaceSpaceWithPlus(Uri.encode(date, " ")));
        Log.d(TAG, stringToSign.toString());

        return "BCS " + session.getClientId() + ":" + sha1(stringToSign.toString(), session.getClientSecret());
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
    public String generateAdminAuthorizationValue(Session session, String uri, String params, String date) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException {
        final StringBuilder stringToSign = new StringBuilder();
        stringToSign
                .append(BitcasaRESTConstants.REQUEST_METHOD_POST)
                .append("&")
                .append(uri)
                .append("&")
                .append(params)
                .append("&")
                .append(replaceSpaceWithPlus(Uri.encode(BitcasaRESTConstants.HEADER_CONTENT_TYPE, " "))).append(":")
                .append(replaceSpaceWithPlus(Uri.encode(BitcasaRESTConstants.FORM_URLENCODED, " ")))
                .append("&")
                .append(replaceSpaceWithPlus(Uri.encode(BitcasaRESTConstants.HEADER_DATE, " "))).append(":")
                .append(replaceSpaceWithPlus(Uri.encode(date, " ")));
        Log.d(TAG, stringToSign.toString());

        return "BCS " + session.getAdminClientId() + ":" + sha1(stringToSign.toString(), session.getAdminClientSecret());
    }

    /**
     * Creates a BitcasaError if server responds with an error code.
     *
     * @param connection The HttpsURLConnection which contains the status code and error.
     * @throws IOException Occurs if the server response can not be processed.
     */
    public BitcasaError checkRequestResponse(HttpsURLConnection connection) throws IOException {
        BitcasaError error = null;
        InputStream inputStream = null;

        try {
            final int responseCode = connection.getResponseCode();

            Log.d(TAG, "checkRequestResponse: Response code is: " + responseCode);

            if (!(responseCode == HttpsURLConnection.HTTP_OK ||
                    responseCode == HttpsURLConnection.HTTP_PARTIAL)) {
                inputStream = connection.getErrorStream();

                BitcasaRESTUtility restUtility = new BitcasaRESTUtility();
                String response = restUtility.getResponseFromInputStream(inputStream);
                BitcasaResponse bitcasasResponse = new Gson().fromJson(response,
                        BitcasaResponse.class);
                ResponseError responseError = bitcasasResponse.getError();

                JsonElement errorDataElement = responseError.getData();
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
    public String getResponseFromInputStream(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder responseBuilder = new StringBuilder();
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                responseBuilder.append(line);
            }
        } finally {
            bufferedReader.close();
        }

        return responseBuilder.toString();
    }

}
