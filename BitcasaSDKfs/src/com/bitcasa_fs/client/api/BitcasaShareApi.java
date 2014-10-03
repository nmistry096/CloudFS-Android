package com.bitcasa_fs.client.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.TreeMap;

import javax.net.ssl.HttpsURLConnection;

import android.util.Log;

import com.bitcasa_fs.client.BitcasaError;
import com.bitcasa_fs.client.Container;
import com.bitcasa_fs.client.Session;
import com.bitcasa_fs.client.api.BitcasaRESTConstants;
import com.bitcasa_fs.client.api.BitcasaRESTConstants.ApiMethod;
import com.bitcasa_fs.client.api.BitcasaRESTConstants.Exists;
import com.bitcasa_fs.client.api.BitcasaRESTUtility;
import com.bitcasa_fs.client.ParseJSON.BitcasaParseJSON;
import com.bitcasa_fs.client.datamodel.BrowseShare;
import com.bitcasa_fs.client.datamodel.Credential;
import com.bitcasa_fs.client.datamodel.ShareItem;

/**
 * Share related api requests
 * @author Valina Li
 *
 */
public class BitcasaShareApi {
	private static final String TAG = BitcasaShareApi.class.getSimpleName();
	private BitcasaRESTUtility bitcasaRESTUtility;
	private Credential credential;
	
	/**
	 * Constructor
	 * @param credential
	 * @param utility
	 */
	public BitcasaShareApi(Credential credential, BitcasaRESTUtility utility) {
		bitcasaRESTUtility = utility;
		this.credential = credential;
	}
	
	/**
	 * Lists the shares the user has created.
	 * @return list of ShareItem
	 * @throws IOException
	 */
	public ShareItem[] listShare() throws IOException {
		ShareItem[] result = null;
		String url = bitcasaRESTUtility.getRequestUrl(credential, BitcasaRESTConstants.METHOD_SHARES, null, null);
		
		Log.d(TAG, "listShare URL: " + url);
		HttpsURLConnection connection = null;
		InputStream is = null;
		BitcasaParseJSON parser = null;
		
		try {
			connection = (HttpsURLConnection) new URL(url)
					.openConnection();
			connection.setDoInput(true);
			connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_GET);
			bitcasaRESTUtility.setRequestHeaders(credential, connection, null);
			
			BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);
			if (error == null) {
				is = connection.getInputStream();
				parser = new BitcasaParseJSON(ApiMethod.LISTSHARE, null);
				if (parser.readJsonStream(is))
					result = parser.listShares;
			}
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			if (e != null)
				e.printStackTrace();
		} finally {
			
			if (is != null) 
				is.close();
			
			if (connection != null)
				connection.disconnect();
		}
		
		return result;
	}
	
	/**
	 * Shares allow users to create public windows into their filesystem. Only one item can be shared at a time (a file or a folder). 
	 * If a folder is shared, the share that points to that folder will always show what is currently in that folder 
	 * in the user’s file system (it’s a “live” view).
	 * @param path
	 * @param password
	 * @return
	 * @throws IOException
	 */
	public ShareItem createShare(String path, String password) throws IOException {
		ShareItem share= null;
		String url = bitcasaRESTUtility.getRequestUrl(credential, BitcasaRESTConstants.METHOD_SHARES, null, null);
		
		Log.d(TAG, "createShare URL: " + url);
		HttpsURLConnection connection = null;
		InputStream is = null;
		OutputStream os = null;
		BitcasaParseJSON parser = null;
		TreeMap<String, String> formParams = new TreeMap<String, String>();
		formParams.put(BitcasaRESTConstants.BODY_PATH, URLEncoder.encode(path, BitcasaRESTConstants.UTF_8_ENCODING));
		if (password != null)
			formParams.put(BitcasaRESTConstants.PARAM_PASSWORD, URLEncoder.encode(password, BitcasaRESTConstants.UTF_8_ENCODING));
		
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
				os = connection.getOutputStream();
				Log.d(TAG, "POST string: " + parameters.toString());
				os.write(parameters.getBytes());
			}
			
			BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);
			if (error == null) {
				is = connection.getInputStream();
				parser = new BitcasaParseJSON(ApiMethod.SHARE, null);
				if (parser.readJsonStream(is))
					share = parser.share;
			}
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			if (e != null)
				e.printStackTrace();
		} finally {
			
			if (is != null) 
				is.close();
			
			if (os != null)
				os.close();
			
			if (connection != null)
				connection.disconnect();
		}
		return share;
	}
	
	/**
	 * Given sharekey and the path to any foder/file under share, browseShare method will return BrowseShare related information
	 * make sure unlockShare is called before browseShare
	 * @param shareKey
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public BrowseShare browseShare(String shareKey, String path) throws IOException {
		BrowseShare bsResult = null;
		
		StringBuilder sb = new StringBuilder();
		sb.append(shareKey);
		if (path != null)
			sb.append(path);
		sb.append(BitcasaRESTConstants.METHOD_META);
		String url = bitcasaRESTUtility.getRequestUrl(credential, BitcasaRESTConstants.METHOD_SHARES, sb.toString(), null);
		
		Log.d(TAG, "browseShare URL: " + url);
		HttpsURLConnection connection = null;
		InputStream is = null;
		BitcasaParseJSON parser = null;
		try {
			connection = (HttpsURLConnection) new URL(url)
					.openConnection();
			connection.setDoInput(true);
			connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_GET);
			bitcasaRESTUtility.setRequestHeaders(credential, connection, null);
			
			BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);
			if (error == null) {
				is = connection.getInputStream();
				parser = new BitcasaParseJSON(ApiMethod.BROWSE_SHARE, null);
				if (parser.readJsonStream(is)) {
					bsResult = new BrowseShare();
					bsResult.setMeta(parser.meta);
					bsResult.setShare(parser.share);
					bsResult.setSharedItems(parser.files);
				}
			}
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			if (e != null)
				e.printStackTrace();
		} finally {
			
			if (is != null) 
				is.close();
			
			if (connection != null)
				connection.disconnect();
		}
		return bsResult;
	}
	
	/**
	 * Given a valid location in a user’s filesystem, all items found in this share will be inserted into the given location. 
	 * File collisions will be handled as specified.
	 * @param shareKey
	 * @param pathToInsertShare
	 * @param cr
	 * @return
	 * @throws IOException
	 */
	public Container[] receiveShare(String shareKey, String pathToInsertShare, Exists cr) throws IOException {
		Container[] result = null;
		String url = bitcasaRESTUtility.getRequestUrl(credential, BitcasaRESTConstants.METHOD_SHARES, shareKey+File.separator, null);
		
		Log.d(TAG, "receiveShare URL: " + url);
		HttpsURLConnection connection = null;
		InputStream is = null;
		OutputStream os = null;
		BitcasaParseJSON parser = null;
		TreeMap<String, String> formParams = new TreeMap<String, String>();
		formParams.put(BitcasaRESTConstants.BODY_PATH, URLEncoder.encode(pathToInsertShare, BitcasaRESTConstants.UTF_8_ENCODING));
		if (cr != null) {
			switch(cr) {
			case FAIL:
				formParams.put(BitcasaRESTConstants.BODY_EXISTS, BitcasaRESTConstants.EXISTS_FAIL);
				break;
			case OVERWRITE:
				formParams.put(BitcasaRESTConstants.BODY_EXISTS, BitcasaRESTConstants.EXISTS_OVERWRITE);
				break;
			case RENAME:
				default:
					formParams.put(BitcasaRESTConstants.BODY_EXISTS, BitcasaRESTConstants.EXISTS_RENAME);
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
				os = connection.getOutputStream();
				Log.d(TAG, "POST string: " + parameters.toString());
				os.write(parameters.getBytes());
			}
			
			BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);
			if (error == null) {
				is = connection.getInputStream();
				parser = new BitcasaParseJSON(ApiMethod.RECEIVE_SHARE, null);
				if (parser.readJsonStream(is))
					result = parser.files;
			}
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			if (e != null)
				e.printStackTrace();
		} finally {
			
			if (is != null) 
				is.close();
			
			if (os != null)
				os.close();
			
			if (connection != null)
				connection.disconnect();
		}
		return result;
	}
	
	/**
	 * Given a valid share and its password, this entrypoint will unlock the share for the login session.
	 * @param shareKey
	 * @param password
	 * @return
	 * @throws IOException
	 */
	public boolean unlockShare(String shareKey, String password) throws IOException {
		boolean bResult = false;
		
		StringBuilder sb = new StringBuilder();
		sb.append(shareKey);
		sb.append(BitcasaRESTConstants.METHOD_UNLOCK);
		String url = bitcasaRESTUtility.getRequestUrl(credential, BitcasaRESTConstants.METHOD_SHARES, sb.toString(), null);
		
		Log.d(TAG, "unlockShare URL: " + url);
		HttpsURLConnection connection = null;
		InputStream is = null;
		OutputStream os = null;
		BitcasaParseJSON parser = null;
		TreeMap<String, String> formParams = new TreeMap<String, String>();
		formParams.put(BitcasaRESTConstants.PARAM_PASSWORD, URLEncoder.encode(password, BitcasaRESTConstants.UTF_8_ENCODING));
				
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
				os = connection.getOutputStream();
				Log.d(TAG, "POST string: " + parameters.toString());
				os.write(parameters.getBytes());
			}
			
			BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);
			if (error == null) {
				is = connection.getInputStream();
				parser = new BitcasaParseJSON(ApiMethod.GENERAL, null);
				if (parser.readJsonStream(is))
					bResult = parser.bSuccessResult;
			}
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			if (e != null)
				e.printStackTrace();
		} finally {
			
			if (is != null) 
				is.close();
			
			if (os != null)
				os.close();
			
			if (connection != null)
				connection.disconnect();
		}
		return bResult;
	}
	
	/**
	 * Alter Share Info
	 * @param shareKey
	 * @param currentPassword
	 * @param newPassword
	 * @return
	 * @throws IOException
	 */
	public ShareItem alterShareInfo(String shareKey, String currentPassword, String newPassword) throws IOException {
		ShareItem share = null;
		
		StringBuilder sb = new StringBuilder();
		sb.append(shareKey);
		sb.append(BitcasaRESTConstants.METHOD_INFO);
		String url = bitcasaRESTUtility.getRequestUrl(credential, BitcasaRESTConstants.METHOD_SHARES, sb.toString(), null);
		
		Log.d(TAG, "alterShareInfo URL: " + url);
		HttpsURLConnection connection = null;
		InputStream is = null;
		OutputStream os = null;
		BitcasaParseJSON parser = null;
		TreeMap<String, String> formParams = new TreeMap<String, String>();
		formParams.put(BitcasaRESTConstants.PARAM_CURRENT_PASSWORD, URLEncoder.encode(currentPassword, BitcasaRESTConstants.UTF_8_ENCODING));
		formParams.put(BitcasaRESTConstants.PARAM_PASSWORD, URLEncoder.encode(newPassword, BitcasaRESTConstants.UTF_8_ENCODING));
				
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
				os = connection.getOutputStream();
				Log.d(TAG, "POST string: " + parameters.toString());
				os.write(parameters.getBytes());
			}
			
			BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);
			if (error == null) {
				is = connection.getInputStream();
				parser = new BitcasaParseJSON(ApiMethod.SHARE, null);
				if (parser.readJsonStream(is))
					share = parser.share;
			}
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			if (e != null)
				e.printStackTrace();
		} finally {
			
			if (is != null) 
				is.close();
			
			if (os != null)
				os.close();
			
			if (connection != null)
				connection.disconnect();
		}
		return share;
	}
	
	/**
	 * Delete Share
	 * @param shareKey
	 * @return
	 */
	public boolean deleteShare(String shareKey) {
		boolean bResult = false;
		StringBuilder sb = new StringBuilder();
		sb.append(shareKey);
		sb.append(File.separator);
		String url = bitcasaRESTUtility.getRequestUrl(credential, BitcasaRESTConstants.METHOD_SHARES, sb.toString(), null);
		
		Log.d(TAG, "deleteShare URL: " + url);
		HttpsURLConnection connection = null;
		try {
			connection = (HttpsURLConnection) new URL(url)
					.openConnection();
			connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_DELETE);
			bitcasaRESTUtility.setRequestHeaders(credential, connection, null);
						
			BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);
			if (error == null) 
				bResult = true;	
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			if (e != null)
				e.printStackTrace();
		} finally {
			if (connection != null)
				connection.disconnect();
		}
		return bResult;
	}

}
