package com.bitcasa.bitcasasdkfs.test;

import java.io.IOException;

import com.bitcasa_fs.client.Account;
import com.bitcasa_fs.client.Session;
import com.bitcasa_fs.client.exception.BitcasaException;

import android.test.InstrumentationTestCase;

public class TestAccount extends InstrumentationTestCase {
	private final static String CLIENT_ID = "1123456789012345678901234567890123456789012";
	private final static String CLIENT_SECRET = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
	private final static String USER = "youremail@email.com";
	private final static String PW = "password";
	private final static String ENDPOINT = "xxxxxxxxxx.cloudfs.io";
	
	private Session mBitcasaSession;
	private String mNewLocale;
	private String mOriginalLocale;
	
	@Override
	protected void setUp() throws Exception {
		mBitcasaSession = new Session(getInstrumentation().getContext(), ENDPOINT, CLIENT_ID, CLIENT_SECRET);
		if (!mBitcasaSession.isLinked())
			mBitcasaSession.authenticate(USER, PW);
		assertNotNull(mBitcasaSession.isLinked());
		
		mNewLocale = "en-rGB";
		mOriginalLocale = "zh";
	}
	
	@Override
	protected void tearDown() throws Exception {
		//mBitcasaSession.unlink();
		//mBitcasaSession = null;
		super.tearDown();
	}
	
	public void testChangeSessionLocale() throws Throwable {
		Throwable exception = null;
		Account account = null;
		boolean bLinked = mBitcasaSession.isLinked();
		assertTrue(bLinked);
		if (bLinked) {
			try {
				account = mBitcasaSession.getBitcasaClientApi().getBitcasaAccountDataApi().requestAccountInfo();
			} catch (IOException e) {
				e.printStackTrace();
				exception = e;
			} catch (BitcasaException e) {
				e.printStackTrace();
				exception = e;
			} finally {
				assertNotNull(account);
			}	
			
			String expectedLocal = null;
			if (account.getSession_locale().equals(mOriginalLocale))
				expectedLocal = mNewLocale;
			else
				expectedLocal = mOriginalLocale;
	
			assertTrue(mBitcasaSession.getAccount().changeSessionLocale(mBitcasaSession.getBitcasaClientApi(), expectedLocal));
			
			try {
				account = mBitcasaSession.getBitcasaClientApi().getBitcasaAccountDataApi().requestAccountInfo();
			} catch (IOException e) {
				exception = e;
				e.printStackTrace();
			} catch (BitcasaException e) {
				exception = e;
				e.printStackTrace();
			} finally {
				assertEquals(account.getSession_locale(), expectedLocal);
			}
			
			if (exception != null)
				throw exception;
		}
	}
	
	public void testChangeAccountLocale() throws Throwable {
		Throwable exception = null;
		Account account = null;
		boolean bLinked = mBitcasaSession.isLinked();
		assertTrue(bLinked);
		if (bLinked) {
			try {
				account = mBitcasaSession.getBitcasaClientApi().getBitcasaAccountDataApi().requestAccountInfo();
			} catch (IOException e) {
				e.printStackTrace();
				exception = e;
			} catch (BitcasaException e) {
				e.printStackTrace();
				exception = e;
			} finally {
				assertNotNull(account);
			}			
	
			String expectedLocal = null;
			if (account.getLocale().equals(mOriginalLocale))
				expectedLocal = mNewLocale;
			else
				expectedLocal = mOriginalLocale;
			
			assertTrue(mBitcasaSession.getAccount().changeAccountLocale(mBitcasaSession.getBitcasaClientApi(), expectedLocal));
			
			try {
				account = mBitcasaSession.getBitcasaClientApi().getBitcasaAccountDataApi().requestAccountInfo();
			} catch (IOException e) {
				exception = e;
				e.printStackTrace();
			} catch (BitcasaException e) {
				exception = e;
				e.printStackTrace();
			} finally {
				assertEquals(account.getLocale(), expectedLocal);
			}
			
			if (exception != null)
				throw exception;
		}
	}
}
