package com.bitcasa.bitcasasdkfs.test;

import java.io.IOException;

import com.bitcasa_fs.client.Session;
import com.bitcasa_fs.client.User;
import com.bitcasa_fs.client.exception.BitcasaException;

import android.test.InstrumentationTestCase;

public class TestUser extends InstrumentationTestCase{
	private final static String CLIENT_ID = "1123456789012345678901234567890123456789012";
	private final static String CLIENT_SECRET = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
	private final static String USER = "youremail@email.com";
	private final static String PW = "password";
	private final static String ENDPOINT = "xxxxxxxxxx.cloudfs.io";
	
	private Session mBitcasaSession;
	@Override
	protected void setUp() throws Exception {
		mBitcasaSession = new Session(getInstrumentation().getContext(), ENDPOINT, CLIENT_ID, CLIENT_SECRET);
		if (!mBitcasaSession.isLinked())
			mBitcasaSession.authenticate(USER, PW);
		assertNotNull(mBitcasaSession.isLinked());
	}
	
	@Override
	protected void tearDown() throws Exception {
		mBitcasaSession.unlink();
		mBitcasaSession = null;
	}
	
	public void testGetUser() {
		User user = null;
		try {
			user = mBitcasaSession.getBitcasaClientApi().getBitcasaAccountDataApi().requestUserInfo();
		} catch (IOException e) {
			e.printStackTrace();
			assertTrue(false);
		} catch (BitcasaException e) {
			e.printStackTrace();
			assertTrue(false);
		} finally {
			assertNotNull(user);
		}
		
		assertNotNull(user.getEmail());
		assertNotNull(user.getFirst_name());
		assertNotNull(user.getLast_name());
		assertNotNull(user.getUsername());
		assertNotNull(user.getCreated_time());
		assertTrue(user.getCreated_time()>0);
	}
}
