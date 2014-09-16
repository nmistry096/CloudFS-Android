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
package com.bitcasa_fs.client.datamodel;

import com.bitcasa_fs.client.Account;
import com.bitcasa_fs.client.User;
import com.bitcasa_fs.client.api.BitcasaClientApi;

public class Profile {

	Account accountData;
	User userData;
	
	public Profile(){
		accountData = new Account();
		userData = new User();
	}

	public Account getAccountData() {
		return accountData;
	}

	public void setAccountData(Account accountData) {
		this.accountData = accountData;
	}

	public User getUserData() {
		return userData;
	}

	public void setUserData(User userData) {
		this.userData = userData;
	}
}
