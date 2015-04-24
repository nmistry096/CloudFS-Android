package com.bitcasa.cloudfs;

import android.test.InstrumentationTestCase;

import com.bitcasa.cloudfs.exception.BitcasaException;

import java.io.IOException;

public class TestSession extends InstrumentationTestCase{
	private final static String CLIENT_ID = "1123456789012345678901234567890123456789012";
	private final static String CLIENT_SECRET = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
	private final static String USER = "youremail@email.com";
	private final static String PW = "password";
	private final static String ENDPOINT = "xxxxxxxxxx.cloudfs.io";
	
	Session mBitcasaSession;
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	@Override
	protected void tearDown() throws Exception {
		mBitcasaSession.unlink();
		mBitcasaSession = null;
		super.tearDown();
	}
	
	public void test2IsLinked() {
		mBitcasaSession = new Session(ENDPOINT, CLIENT_ID, CLIENT_SECRET);
		try {
			mBitcasaSession.authenticate(USER, PW);
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (BitcasaException e) {
			assertTrue(false);
			e.printStackTrace();
		}
		assertTrue(mBitcasaSession.isLinked());
	}
	
	public void test1Authenticate() {
		mBitcasaSession = new Session(ENDPOINT, CLIENT_ID, CLIENT_SECRET);
		try {
			mBitcasaSession.authenticate(USER, PW);
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (BitcasaException e) {
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
	public void test3Unlink() {
		mBitcasaSession = new Session(ENDPOINT, CLIENT_ID, CLIENT_SECRET);
		try {
			mBitcasaSession.authenticate(USER, PW);
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (BitcasaException e) {
			assertTrue(false);
			e.printStackTrace();
		}
		mBitcasaSession.unlink();
		assertTrue(!mBitcasaSession.isLinked());
	}
	
	public void test4User() {
		mBitcasaSession = new Session(ENDPOINT, CLIENT_ID, CLIENT_SECRET);
		try {
			mBitcasaSession.authenticate(USER, PW);
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (BitcasaException e) {
			assertTrue(false);
			e.printStackTrace();
		}
		
		try {
			assertNotNull(mBitcasaSession.user());
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (BitcasaException e) {
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
	public void test5Account() {
		mBitcasaSession = new Session(ENDPOINT, CLIENT_ID, CLIENT_SECRET);
		try {
			mBitcasaSession.authenticate(USER, PW);
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (BitcasaException e) {
			assertTrue(false);
			e.printStackTrace();
		}
		try {
			assertNotNull(mBitcasaSession.account());
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (BitcasaException e) {
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
	public void test6FileSystem() {
		mBitcasaSession = new Session(ENDPOINT, CLIENT_ID, CLIENT_SECRET);
		try {
			mBitcasaSession.authenticate(USER, PW);
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (BitcasaException e) {
			assertTrue(false);
			e.printStackTrace();
		}
		assertNotNull(mBitcasaSession.fileSystem());
	}
}
