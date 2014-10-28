package com.bitcasa_fs.client.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.net.ssl.HttpsURLConnection;

import android.net.Uri;
import android.util.Log;

import com.bitcasa_fs.client.BitcasaError;
import com.bitcasa_fs.client.Session;
import com.bitcasa_fs.client.api.BitcasaRESTConstants;
import com.bitcasa_fs.client.api.BitcasaRESTConstants.ApiMethod;
import com.bitcasa_fs.client.api.BitcasaRESTUtility;
import com.bitcasa_fs.client.ParseJSON.BitcasaParseJSON;
import com.bitcasa_fs.client.datamodel.Credential;
import com.bitcasa_fs.client.datamodel.Profile;
import com.bitcasa_fs.client.exception.BitcasaAuthenticationException;
import com.bitcasa_fs.client.exception.BitcasaException;

/**
 * Entry to all Bitcasa api requests, network access is required to proceed any api request.
 * @author Valina Li
 *
 */
public class BitcasaClientApi {
	private static final String TAG = BitcasaClientApi.class.getSimpleName();
	
	private BitcasaRESTUtility bitcasaRESTUtility;
	private Credential credential;
	
	private BitcasaAccountDataApi accountApi;
	private BitcasaFileSystemApi fileSystemApi;
	private BitcasaShareApi shareApi;
	private BitcasaTrashApi trashApi;
	private BitcasaHistoryApi historyApi;

	/**
	 * Constructor, takes in credential, initialize groups of api requests: Bitcasa account and user request api, Bitcasa file system request api, 
	 * Bitcasa share api, Bitcasa history api, and Bitcasa trash api
	 * @param credential
	 */
	public BitcasaClientApi(Credential credential) {
		bitcasaRESTUtility = new BitcasaRESTUtility();
		this.credential = credential;
		accountApi = new BitcasaAccountDataApi(credential, bitcasaRESTUtility);
		fileSystemApi = new BitcasaFileSystemApi(credential, bitcasaRESTUtility);
		shareApi = new BitcasaShareApi(credential, bitcasaRESTUtility);
		historyApi = new BitcasaHistoryApi(credential, bitcasaRESTUtility);
		trashApi = new BitcasaTrashApi(credential, bitcasaRESTUtility);
	}
	
	/**
	 * Getter, get Bitcasa account and user request api class
	 * @return
	 */
	public BitcasaAccountDataApi getBitcasaAccountDataApi() {
		return accountApi;
	}
	
	/**
	 * Getter, get Bitcasa file system related request api class
	 * @return
	 */
	public BitcasaFileSystemApi getBitcasaFileSystemApi() {
		return fileSystemApi;
	}
	
	/**
	 * Getter, get Bitcasa trash request api class
	 * @return
	 */
	public BitcasaTrashApi getBitcasaTrashApi() {
		return trashApi;
	}
	
	/**
	 * Getter, get Bitcasa history request api class
	 * @return
	 */
	public BitcasaHistoryApi getBitcasaHistoryApi() {
		return historyApi;
	}
	
	/**
	 * Getter, get Bitcasa share request api class
	 * @return
	 */
	public BitcasaShareApi getBitcasaShareApi() {
		return shareApi;
	}
	
	/**
	 * an api request to CloudFS server to get access token
	 * 
	 * @param session
	 * @param username
	 * @param password
	 * @throws IOException
	 * @throws BitcasaException
	 */
    public void getAccessToken(Session session, String username, String password) throws IOException, BitcasaException {
    	if (credential != null && credential.getAccessToken() != null && credential.getTokenType() != null)
    		return;
    	
		HttpsURLConnection connection = null;
		OutputStream os = null;
		InputStream is = null;
		String accesstoken = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(BitcasaRESTConstants.DATE_FORMAT, Locale.US);
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
			String date = sdf.format(Calendar.getInstance(Locale.US).getTime());
			int index = date.lastIndexOf("+");
			if (index == -1)
				index = date.length();
			date = date.substring(0, index);
			
			TreeMap<String, Object> bodyparams = new TreeMap<String, Object>();
			bodyparams.put(BitcasaRESTConstants.PARAM_GRANT_TYPE, Uri.encode(BitcasaRESTConstants.PARAM_PASSWORD, " "));
			bodyparams.put(BitcasaRESTConstants.PARAM_USERNAME, Uri.encode(username, " "));
			bodyparams.put(BitcasaRESTConstants.PARAM_PASSWORD, Uri.encode(password, " "));
			
			String parameters = bitcasaRESTUtility.generateParamsString(bodyparams);
			String url = bitcasaRESTUtility.getRequestUrl(credential, BitcasaRESTConstants.METHOD_OAUTH2, BitcasaRESTConstants.METHOD_TOKEN, null);
			//generate authorization value
			String uri = BitcasaRESTConstants.API_VERSION_2 + BitcasaRESTConstants.METHOD_OAUTH2 + BitcasaRESTConstants.METHOD_TOKEN;
			String authorizationValue = bitcasaRESTUtility.generateAuthorizationValue(session, uri, parameters, date);
			
			Log.d(TAG, "getAccessToken URL: " + url);
			connection = (HttpsURLConnection) new URL(url)
					.openConnection();
			connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_POST);
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setReadTimeout(300000);
			connection.setConnectTimeout(300000);
			connection.setUseCaches(false);
			connection.setRequestProperty(BitcasaRESTConstants.HEADER_CONTENT_TYPE, BitcasaRESTConstants.FORM_URLENCODED);
			connection.setRequestProperty(BitcasaRESTConstants.HEADER_DATE, date);
			connection.setRequestProperty(BitcasaRESTConstants.HEADER_AUTORIZATION, authorizationValue.toString());
			
			if (parameters != null) {
				os = connection.getOutputStream();
				Log.d(TAG, "POST string: " + parameters.toString());
				os.write(parameters.getBytes());
			}
						
			// read response code
			final int responseCode = connection.getResponseCode();			
			
			if (responseCode == HttpsURLConnection.HTTP_OK)			
				is = connection.getInputStream();
			else if (responseCode == HttpsURLConnection.HTTP_NOT_FOUND || responseCode == HttpsURLConnection.HTTP_BAD_REQUEST)
				is = connection.getErrorStream();
			
			BitcasaParseJSON parser = new BitcasaParseJSON(ApiMethod.GENERAL, null);
			if (is != null)
				parser.readJsonStream(is);
			if (responseCode == HttpsURLConnection.HTTP_OK) {
				credential.setAccessToken(parser.accessToken);
				credential.setTokenType(parser.tokenType);
			}
			else
			if (parser.bitcasaError.getError() != null)
				throw new BitcasaAuthenticationException(parser.bitcasaError);
			else
				throw new BitcasaAuthenticationException(Integer.toString(responseCode));
			
		}catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (os != null) {
				os.close();
			}
			if (is != null) {
				is.close();
			}
			if (connection != null)
				connection.disconnect();
		}
	}
}
