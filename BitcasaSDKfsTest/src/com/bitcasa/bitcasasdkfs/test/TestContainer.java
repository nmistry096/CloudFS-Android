package com.bitcasa.bitcasasdkfs.test;

import java.io.IOException;

import com.bitcasa_fs.client.Container;
import com.bitcasa_fs.client.Item;
import com.bitcasa_fs.client.Item.FileType;
import com.bitcasa_fs.client.Session;
import com.bitcasa_fs.client.exception.BitcasaAuthenticationException;
import com.bitcasa_fs.client.exception.BitcasaException;
import com.bitcasa_fs.client.exception.BitcasaRequestErrorException;

import android.test.InstrumentationTestCase;

public class TestContainer extends InstrumentationTestCase{
	private final static String CLIENT_ID = "1123456789012345678901234567890123456789012";
	private final static String CLIENT_SECRET = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
	private final static String USER = "youremail@email.com";
	private final static String PW = "password";
	private final static String ENDPOINT = "xxxxxxxxxx.cloudfs.io";
	
	private Session mBitcasaSession;
	private Container mTestContainer;
	private Container mRootContainer;
	
	@Override
	protected void setUp() throws Exception {
		mBitcasaSession = new Session(getInstrumentation().getContext(), ENDPOINT, CLIENT_ID, CLIENT_SECRET);
		if (!mBitcasaSession.isLinked())
			mBitcasaSession.authenticate(USER, PW);
		assertNotNull(mBitcasaSession.isLinked());
		mRootContainer = new Container();
	}
	
	@Override
	protected void tearDown() throws Exception {
		//mBitcasaSession.unlink();
		//mBitcasaSession = null;
	}
	
	public void testList() {
		//test get root
		Item[] result = null;
		try {
			result = mRootContainer.list(mBitcasaSession.getBitcasaClientApi());
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (BitcasaException e) {
			assertTrue(false);
			e.printStackTrace();
		}
		assertNotNull(result);
		
		for (int i=0; i<result.length; i++) {
			if (result[0].getFile_type().equals(FileType.FOLDER)) {
				mTestContainer = (Container) result[0];
				break;
			}
		}
		//test get a folder in the root
		Item[] folder = null;
		try {
			folder = mTestContainer.list(mBitcasaSession.getBitcasaClientApi());
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (BitcasaException e) {
			assertTrue(false);
			e.printStackTrace();
		}
		assertNotNull(folder);
	}
	
	public void testCreateFolder() {
		Container newfolder = new Container();
		newfolder.setName("v2");
		Item folder = null;
		try {
			folder = mRootContainer.createFolder(mBitcasaSession.getBitcasaClientApi(), newfolder);
		} catch (BitcasaRequestErrorException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (BitcasaAuthenticationException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		}
		assertNotNull(folder);
	}
}
