package com.bitcasa.bitcasasdkfs.test;

import java.io.IOException;

import com.bitcasa_fs.client.Session;
import com.bitcasa_fs.client.exception.BitcasaException;

import android.test.InstrumentationTestCase;

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
		super.tearDown();
	}
	
	public void test2IsLinked() {
		mBitcasaSession = new Session(getInstrumentation().getContext(), ENDPOINT, CLIENT_ID, CLIENT_SECRET);
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
		mBitcasaSession = new Session(getInstrumentation().getContext(), ENDPOINT, CLIENT_ID, CLIENT_SECRET);
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
		mBitcasaSession = new Session(getInstrumentation().getContext(), ENDPOINT, CLIENT_ID, CLIENT_SECRET);
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
		mBitcasaSession = new Session(getInstrumentation().getContext(), ENDPOINT, CLIENT_ID, CLIENT_SECRET);
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
			assertNotNull(mBitcasaSession.getUser());
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (BitcasaException e) {
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
	public void test5Account() {
		mBitcasaSession = new Session(getInstrumentation().getContext(), ENDPOINT, CLIENT_ID, CLIENT_SECRET);
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
			assertNotNull(mBitcasaSession.getAccount());
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (BitcasaException e) {
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
	public void test6FileSystem() {
		mBitcasaSession = new Session(getInstrumentation().getContext(), ENDPOINT, CLIENT_ID, CLIENT_SECRET);
		try {
			mBitcasaSession.authenticate(USER, PW);
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (BitcasaException e) {
			assertTrue(false);
			e.printStackTrace();
		}
		assertNotNull(mBitcasaSession.getFileSystem());
	}
}
