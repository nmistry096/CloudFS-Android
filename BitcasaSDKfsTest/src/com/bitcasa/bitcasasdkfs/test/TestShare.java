package com.bitcasa.bitcasasdkfs.test;

import java.io.IOException;

import com.bitcasa_fs.client.Container;
import com.bitcasa_fs.client.Item;
import com.bitcasa_fs.client.Session;
import com.bitcasa_fs.client.Item.FileType;
import com.bitcasa_fs.client.datamodel.BrowseShare;
import com.bitcasa_fs.client.datamodel.ShareItem;
import com.bitcasa_fs.client.exception.BitcasaException;

import android.test.InstrumentationTestCase;

public class TestShare extends InstrumentationTestCase {
	private final static String CLIENT_ID = "1123456789012345678901234567890123456789012";
	private final static String CLIENT_SECRET = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
	private final static String USER = "youremail@email.com";
	private final static String PW = "password";
	private final static String ENDPOINT = "xxxxxxxxxx.cloudfs.io";
	
	private final static String SHARE_PW = "hello";
	private final static String NEW_SHARE_PW = "world";
	private Session mBitcasaSession;
	private Item mFile;
	private Container mContainer;
	private ShareItem[] mSharedItems;
	@Override
	protected void setUp() throws Exception {
		mBitcasaSession = new Session(getInstrumentation().getContext(), ENDPOINT, CLIENT_ID, CLIENT_SECRET);
		if (!mBitcasaSession.isLinked())
			mBitcasaSession.authenticate(USER, PW);
		assertNotNull(mBitcasaSession.isLinked());
		Item[] result = null;
		try {
			result = mBitcasaSession.getBitcasaClientApi().getBitcasaFileSystemApi().getList(null, -1, 1, null);
			assertNotNull(result);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BitcasaException e) {
			e.printStackTrace();
		}
		
		//collecting test data
		for (int i=0; i<result.length; i++) {
			if (result[i].getFile_type().equals(FileType.FILE)) {
				if (mFile == null) {
					mFile = result[i];
					if (mContainer != null)
						break;
				}
			}
			else if (result[i].getFile_type().equals(FileType.FOLDER)) {
				if (mContainer == null) {
					mContainer = (Container) result[i];
					if (mFile != null)
						break;
				}
			}
		}
		
		//sharekey
		try {
			mSharedItems = mBitcasaSession.getBitcasaClientApi().getBitcasaShareApi().listShare();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	protected void tearDown() throws Exception {
		//mBitcasaSession.unlink();
		//mBitcasaSession = null;
	}
	
	public void testCreateShare() {
		ShareItem sItem, sItemFolder = null;
		try {
			sItem = mBitcasaSession.getBitcasaClientApi().getBitcasaShareApi().createShare(mFile.getAbsolutePath(), SHARE_PW);
			assertNotNull(sItem);
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		}
		
		//create share folder
		try {
			sItemFolder = mBitcasaSession.getBitcasaClientApi().getBitcasaShareApi().createShare(mContainer.getAbsolutePath(), SHARE_PW);
			assertNotNull(sItemFolder);
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		}
		
	}
	
	public void testListShare() {
		ShareItem[] sItems = null;
		try {
			sItems = mBitcasaSession.getBitcasaClientApi().getBitcasaShareApi().listShare();
			assertNotNull(sItems);
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
	public void testBrowseShare() {
		BrowseShare browseshare = null;
		try {
			browseshare = mBitcasaSession.getBitcasaClientApi().getBitcasaShareApi().browseShare(mSharedItems[0].getShare_key(), mFile.getAbsolutePath());
			assertNotNull(browseshare);
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
	public void testReceiveShare() {
		Container[] items = null;
		try {
			items = mBitcasaSession.getBitcasaClientApi().getBitcasaShareApi().receiveShare(mSharedItems[0].getShare_key(), mContainer.getAbsolutePath(), null);
			assertNotNull(items);
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
	public void testUnlockShare() {
		try {
			assertTrue(mBitcasaSession.getBitcasaClientApi().getBitcasaShareApi().unlockShare(mSharedItems[0].getShare_key(), NEW_SHARE_PW));
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
	public void testAlterShareInfo() {
		ShareItem sItem = null;
		try {
			sItem = mBitcasaSession.getBitcasaClientApi().getBitcasaShareApi().alterShareInfo(mSharedItems[0].getShare_key(), SHARE_PW, NEW_SHARE_PW);
			assertNotNull(sItem);
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
	public void testDeleteShare() {
		assertTrue(mBitcasaSession.getBitcasaClientApi().getBitcasaShareApi().deleteShare(mSharedItems[0].getShare_key()));
	}
}
