/**
 * Bitcasa Client Android SDK
 * Copyright (C) 2013 Bitcasa, Inc.
 * 215 Castro Street, 2nd Floor
 * Mountain View, CA 94041
 *
 * This file contains an SDK in Java for accessing the Bitcasa infinite drive in Android platform.
 *
 * For support, please send email to support@bitcasa.com.
 */
package com.bitcasa_fs.client.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Set;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;

import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import com.bitcasa_fs.client.BitcasaError;
import com.bitcasa_fs.client.Session;
import com.bitcasa_fs.client.api.BitcasaRESTConstants;
import com.bitcasa_fs.client.api.BitcasaRESTUtility;
import com.bitcasa_fs.client.ParseJSON.BitcasaParseJSON;
import com.bitcasa_fs.client.api.BitcasaRESTConstants.ApiMethod;
import com.bitcasa_fs.client.api.BitcasaRESTConstants.FileOperation;
import com.bitcasa_fs.client.datamodel.Credential;
import com.bitcasa_fs.client.exception.BitcasaException;
import com.bitcasa_fs.client.exception.BitcasaRequestErrorException;

public class BitcasaRESTUtility {
	
	public static final String TAG = BitcasaRESTUtility.class.getSimpleName();
	
	public String getRequestUrl(Credential credential, String request, String method, Map<String, String> queryParams) {
		StringBuilder url = new StringBuilder();
		url.append("https://");
		url.append(credential.getEndPoint())
		.append(BitcasaRESTConstants.API_VERSION_2)
		.append(request);
		
		if (method != null)
			url.append(method);
		
		if (queryParams != null) {
			url.append("?");
			url.append(generateParamsString(queryParams));
		}
		
		return url.toString();
	}
	
	//encoding need to be done before calling generateParamsString 
  	public String generateParamsString(Map params) {
  		String query = null;
  		if (params != null) {
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

  			// remove the last '&'
  			if (paramsString.charAt(paramsString.length() - 1) == '&')
  				paramsString.deleteCharAt(paramsString.length() - 1);
  			query = paramsString.toString();
  		}
  		return query;
  	}
  	
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
  		if (accessToken != null) {
  			connection.setRequestProperty(BitcasaRESTConstants.HEADER_AUTORIZATION, accessToken);
  			Log.d("setRequestHeaders", "we should set the auth header: " + accessToken);
  		}
  	}
	
	public String read(InputStream in) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader r = new BufferedReader(new InputStreamReader(in));
		for (String line = r.readLine(); line != null; line = r.readLine()) {
			sb.append(line);
		}
		in.close();
		return sb.toString();
	}
	
	public String urlEncodeSegments(String path) {
		String encodedPath = null;
		StringBuilder sb = new StringBuilder();
		try {
			if (path.equals(BitcasaRESTConstants.FORESLASH)) {
				encodedPath = BitcasaRESTConstants.FORESLASH;
			} else {
				String[] segments = path.split(BitcasaRESTConstants.FORESLASH);
				final int count = segments.length;
				for (int i = 0; i < count; i++) {
					sb.append(URLEncoder.encode(segments[i], BitcasaRESTConstants.UTF_8_ENCODING).replace("+", "%20")).append(BitcasaRESTConstants.FORESLASH);;
				}
				encodedPath = sb.toString();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encodedPath;
	}
	
	public ApiMethod getApiMethodFromFileOperation(FileOperation op) {
		switch (op) {
		case COPY:
			return ApiMethod.COPY;
		case MOVE:
			return ApiMethod.MOVE;
		case ADDFOLDER:
			return ApiMethod.ADD_FOLDER;
			default:
				return null;
		}
	}
	
	public String sha1(String s, String keyString) throws 
		UnsupportedEncodingException, NoSuchAlgorithmException, 
			InvalidKeyException {
		
		SecretKeySpec key = new SecretKeySpec((keyString).getBytes(), "HmacSHA1");
		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(key);
			
		byte[] bytes = mac.doFinal(s.getBytes("UTF-8"));
		
		return new String(Base64.encodeToString(bytes, Base64.DEFAULT));
	}
	
	public String replaceSpaceWithPlus(String s) {
		return s.replace(" ", "+");
	}
	
	public String generateAuthorizationValue(Session session, String params, String date) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException {
		final StringBuilder stringToSign = new StringBuilder();
		stringToSign
				.append(BitcasaRESTConstants.REQUEST_METHOD_POST)
				.append("&")
				.append(BitcasaRESTConstants.API_VERSION_2)
				.append(BitcasaRESTConstants.METHOD_OAUTH2)
				.append(BitcasaRESTConstants.METHOD_TOKEN)
				.append("&")
				.append(params)
				.append("&")
				.append(replaceSpaceWithPlus(Uri.encode(BitcasaRESTConstants.HEADER_CONTENT_TYPE, " "))).append(":")
				.append(replaceSpaceWithPlus(Uri.encode(BitcasaRESTConstants.FORM_URLENCODED, " ")))
				.append("&")
				.append(replaceSpaceWithPlus(Uri.encode(BitcasaRESTConstants.HEADER_DATE, " "))).append(":")
				.append(replaceSpaceWithPlus(Uri.encode(date, " ")));
		Log.d(TAG, stringToSign.toString());
		final StringBuilder authorizationValue = new StringBuilder();
		authorizationValue.append("BCS ").append(session.getClientId()).append(":")
				.append(sha1(stringToSign.toString(), session.getClientSecret()));
		
		return authorizationValue.toString();
	}
	
	public BitcasaError checkRequestResponse(HttpsURLConnection connection) {
    	BitcasaError error = null;
    	InputStream is = null;
		try {
			final int responseCode = connection.getResponseCode();
			
			Log.d(TAG, "response code is: "
					+ responseCode);
			
			if (!(responseCode == HttpsURLConnection.HTTP_OK || responseCode == HttpsURLConnection.HTTP_PARTIAL)) {
				is = connection.getErrorStream();
				//TODO: create parse error code class
				BitcasaParseJSON parser = new BitcasaParseJSON(null, null);
				parser.readJsonStream(is);
				error = parser.bitcasaError;
				int errorcode = parser.bitcasaError.getCode();
				if (errorcode < 0)
					throw new BitcasaRequestErrorException(connection.getResponseMessage());
				else
					throw new BitcasaException(error);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BitcasaException e) {
			e.printStackTrace();
		} finally {
			
				try {
					if (is != null)
						is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		return error;
	}
	
}
