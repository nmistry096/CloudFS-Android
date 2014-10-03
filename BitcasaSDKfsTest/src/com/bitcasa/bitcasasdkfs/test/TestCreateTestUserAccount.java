package com.bitcasa.bitcasasdkfs.test;

import java.io.IOException;

import com.bitcasa_fs.client.Session;
import com.bitcasa_fs.client.datamodel.Profile;
import com.bitcasa_fs.client.exception.BitcasaException;

import android.test.InstrumentationTestCase;

public class TestCreateTestUserAccount extends InstrumentationTestCase{
	private final static String ADMIN_CLIENT_ID = "abcdefghijklmnopqrstuvwxyz1234567890abcdefg";
	private final static String ADMIN_CLIENT_SECRET = "abcdefghijklmnopqrstuvwxyz1234567890abcdefghijklmnopqrstuvwxyz1234567890abcdefghijklmn";
	private final static String ADMIN_URI = "/v2/admin/cloudfs/customers/";
	private final static String ADMIN_ENDPOINT = "access.bitcasa.com";
	
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
	}
	
	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}
	
	public void testCreateTestUserAccount() {
		Profile profile=null;
		Session session = new Session(getInstrumentation().getContext(), ADMIN_ENDPOINT, ADMIN_CLIENT_ID, ADMIN_CLIENT_SECRET);
		try {
			profile = session.getBitcasaClientApi().createTestUserAccount(session, ADMIN_URI, "test@email.com", "xxxxxxx", "test@email.com", "testFirstName", "testLastName");
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (BitcasaException e) {
			assertTrue(false);
			e.printStackTrace();
		}
		assertNotNull(profile);
		assertNotNull(profile.getAccountData());
		assertNotNull(profile.getUserData());
		assertNotNull(profile.getAccountData().getAccount_id());
		assertNotNull(profile.getAccountData().getId());
		assertNotNull(profile.getAccountData().getLocale());
		assertNotNull(profile.getAccountData().getPlan());
		assertNotNull(profile.getAccountData().getPlan_id());
		//assertNotNull(profile.getAccountData().getSession_locale());
		assertNotNull(profile.getAccountData().getState_display_name());
		assertNotNull(profile.getAccountData().getState_id());
		assertNotNull(profile.getAccountData().getLast_login());
		assertNotNull(profile.getAccountData().getQuota());
		assertNotNull(profile.getAccountData().getUsage());
		assertNotNull(profile.getUserData().getEmail());
		assertNotNull(profile.getUserData().getFirst_name());
		assertNotNull(profile.getUserData().getLast_name());
		assertNotNull(profile.getUserData().getUsername());
		assertNotNull(profile.getUserData().getCreated_time());
	}
}
