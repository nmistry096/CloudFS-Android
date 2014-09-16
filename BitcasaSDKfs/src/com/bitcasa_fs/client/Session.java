package com.bitcasa_fs.client;

import java.io.IOException;

import android.content.Context;
import android.content.SharedPreferences;

import com.bitcasa_fs.client.api.BitcasaClientApi;
import com.bitcasa_fs.client.datamodel.Credential;
import com.bitcasa_fs.client.exception.BitcasaException;

public class Session {
	private String clientId;
	private String clientSecret;
	
	private Credential credential;
	private BitcasaClientApi bitcasaClientApi;
	
    public Session(Context applicationContext, String endPoint, String clientId, String clientSecret) {
    	this.clientId = clientId;
    	this.clientSecret = clientSecret;
    	
    	this.credential = new Credential(applicationContext);
    	this.credential.setEndPoint(endPoint);
    	
    	this.bitcasaClientApi = new BitcasaClientApi(credential);
    	
	}
    
    /**
     * This method requires a request to network
     * @param username
     * @param password
     * @throws IOException
     * @throws BitcasaException
     */
    public void authenticate(String username, String password) throws IOException, BitcasaException {
		bitcasaClientApi.getAccessToken(this, username, password);
    }
    
    public boolean isLinked() {
    	if (credential.getAccessToken() == null)
    		return false;
    	else
    		return true;
    }
    
    public void unlink() {
    	credential.setAccessToken(null);
    	credential.setTokenType(null);
    }
    
    /**
     * This method requires a request to network
     * @return
     * @throws IOException
     * @throws BitcasaException
     * @return current Bitcasa User information
     */
    public User getUser() throws IOException, BitcasaException {
    	User userInfo = null;
      	userInfo = bitcasaClientApi.getBitcasaAccountDataApi().requestUserInfo();
    	return userInfo;
    }
    
    /**
     * This method requires a request to network
     * @return
     * @throws IOException
     * @throws BitcasaException
     * @return current Bitcasa Account information
     */
    public Account getAccount() throws IOException, BitcasaException {
    	Account accountInfo = null;
		accountInfo = bitcasaClientApi.getBitcasaAccountDataApi().requestAccountInfo();
    	return accountInfo;
    }
    
    public FileSystem getFileSystem() {
    	FileSystem fileSystem = new FileSystem(bitcasaClientApi);
    	return fileSystem;
    }

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public BitcasaClientApi getBitcasaClientApi() {
		return bitcasaClientApi;
	}

	public void setBitcasaClient(BitcasaClientApi bitcasaClientApi) {
		this.bitcasaClientApi = bitcasaClientApi;
	}
}
