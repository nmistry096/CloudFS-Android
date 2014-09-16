package com.bitcasa_fs.client.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.TreeMap;

import javax.net.ssl.HttpsURLConnection;

import android.util.Log;

import com.bitcasa_fs.client.BitcasaError;
import com.bitcasa_fs.client.Item;
import com.bitcasa_fs.client.Session;
import com.bitcasa_fs.client.api.BitcasaRESTConstants;
import com.bitcasa_fs.client.api.BitcasaRESTConstants.ApiMethod;
import com.bitcasa_fs.client.api.BitcasaRESTConstants.RestoreOptions;
import com.bitcasa_fs.client.api.BitcasaRESTUtility;
import com.bitcasa_fs.client.datamodel.Credential;
import com.bitcasa_fs.client.ParseJSON.BitcasaParseJSON;

public class BitcasaTrashApi {
	private static final String TAG = BitcasaTrashApi.class.getSimpleName();
	private BitcasaRESTUtility bitcasaRESTUtility;
	private Credential credential;
	
	public BitcasaTrashApi(Credential credential, BitcasaRESTUtility utility) {
		bitcasaRESTUtility = utility;
		this.credential = credential;
	}
	
	public Item[] browseTrash() throws IOException {
		Item[] trash = null;
		String url = bitcasaRESTUtility.getRequestUrl(credential, BitcasaRESTConstants.METHOD_TRASH, null, null);
		
		Log.d(TAG, "browseTrash URL: " + url);
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
				parser = new BitcasaParseJSON(ApiMethod.GETLIST, null);
				if (parser.readJsonStream(is))
					trash = parser.files;
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
		
		return trash;
	}
	
	public boolean recoverTrashItem(String absoluteParentPath, RestoreOptions restoreOption, String rescueOrrecreatePath) throws UnsupportedEncodingException {
		boolean bResult = false;
		StringBuilder sb = new StringBuilder();
		if (absoluteParentPath.startsWith(File.separator))
			sb.append(absoluteParentPath.substring(1));
		else
			sb.append(absoluteParentPath);
		String url = bitcasaRESTUtility.getRequestUrl(credential, BitcasaRESTConstants.METHOD_TRASH, sb.toString(), null);
		
		TreeMap<String, String> formParams = null;
		
		formParams = new TreeMap<String, String>();
		switch(restoreOption) {
		case FAIL:
			formParams.put(BitcasaRESTConstants.BODY_RESTORE, BitcasaRESTConstants.RESTORE_FAIL);
			break;
		case RESCUE:
			formParams.put(BitcasaRESTConstants.BODY_RESTORE, BitcasaRESTConstants.RESTORE_RESCUE);
			formParams.put(BitcasaRESTConstants.BODY_RESCUE_PATH, URLEncoder.encode(rescueOrrecreatePath, BitcasaRESTConstants.UTF_8_ENCODING));
			break;
		case RECREATE:
			formParams.put(BitcasaRESTConstants.BODY_RESTORE, BitcasaRESTConstants.RESTORE_RECREATE);
			formParams.put(BitcasaRESTConstants.BODY_RECREATE_PATH, URLEncoder.encode(rescueOrrecreatePath,BitcasaRESTConstants.UTF_8_ENCODING));
			break;
		}
		
		
		Log.d(TAG, "recoverTrashItem URL: " + url);
		HttpsURLConnection connection = null;
		OutputStream os = null;
		try {
			connection = (HttpsURLConnection) new URL(url)
					.openConnection();
			connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_POST);
			bitcasaRESTUtility.setRequestHeaders(credential, connection, null);
			
			if (formParams != null) {
				os = connection.getOutputStream();
				String body = bitcasaRESTUtility.generateParamsString(formParams);
				Log.d("recoverTrashItem", "formParams: "+ body);
				os.write(body.getBytes());
			}
						
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
	
	public boolean deleteTrashItem(String absoluteParentPath) {
		boolean bResult = false;
		StringBuilder sb = new StringBuilder();
		sb.append(absoluteParentPath);
		//sb.append(File.separator);
		String url = bitcasaRESTUtility.getRequestUrl(credential, BitcasaRESTConstants.METHOD_TRASH, sb.toString(), null);
		
		Log.d(TAG, "deleteTrashItem URL: " + url);
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
