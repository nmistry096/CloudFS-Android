package com.bitcasa_fs.client.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

import android.util.Log;

import com.bitcasa_fs.client.BitcasaError;
import com.bitcasa_fs.client.Session;
import com.bitcasa_fs.client.api.BitcasaRESTConstants;
import com.bitcasa_fs.client.api.BitcasaRESTConstants.ApiMethod;
import com.bitcasa_fs.client.api.BitcasaRESTUtility;
import com.bitcasa_fs.client.ParseJSON.BitcasaParseJSON;
import com.bitcasa_fs.client.datamodel.Credential;
import com.bitcasa_fs.client.datamodel.ShareItem;

public class BitcasaHistoryApi {
	private static final String TAG = BitcasaHistoryApi.class.getSimpleName();
	private BitcasaRESTUtility bitcasaRESTUtility;
	private Credential credential;
	
	public BitcasaHistoryApi(Credential credential, BitcasaRESTUtility utility) {
		bitcasaRESTUtility = utility;
		this.credential = credential;
	}
	
	public void listHistory(int startVersion, int stopVersion) throws IOException {
		ArrayList<ShareItem> result = null;
		HashMap<String, String> queryParams = new HashMap<String, String>();
		queryParams.put(BitcasaRESTConstants.PARAM_START, Integer.toString(startVersion));
		if (stopVersion >= 0)
			queryParams.put(BitcasaRESTConstants.PARAM_STOP, Integer.toString(stopVersion));
		
		String url = bitcasaRESTUtility.getRequestUrl(credential, BitcasaRESTConstants.METHOD_HISTORY, null, queryParams);
		
		Log.d(TAG, "listHistory URL: " + url);
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
				parser = new BitcasaParseJSON(ApiMethod.LISTHISTORY, null);
				if (parser.readJsonStream(is)) {
					//TODO: need to parse listhistory
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
	}
}
