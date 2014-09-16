package com.bitcasa.bitcasasdkfs.test;


import java.io.IOException;

import com.bitcasa_fs.client.Container;
import com.bitcasa_fs.client.Folder;
import com.bitcasa_fs.client.Item;
import com.bitcasa_fs.client.Session;
import com.bitcasa_fs.client.Item.FileType;
import com.bitcasa_fs.client.api.BitcasaRESTConstants.Exists;
import com.bitcasa_fs.client.exception.BitcasaException;
import com.bitcasa_fs.client.utility.BitcasaProgressListener;

import android.test.InstrumentationTestCase;
import android.util.Log;

public class TestFolder extends InstrumentationTestCase {
	private final static String CLIENT_ID = "1123456789012345678901234567890123456789012";
	private final static String CLIENT_SECRET = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
	private final static String USER = "youremail@email.com";
	private final static String PW = "password";
	private final static String ENDPOINT = "xxxxxxxxxx.cloudfs.io";
	
	private Session mBitcasaSession;
	private Folder mRootFolder;
	private Item[] mRootList;
	@Override
	protected void setUp() throws Exception {
		mBitcasaSession = new Session(getInstrumentation().getContext(), ENDPOINT, CLIENT_ID, CLIENT_SECRET);
		if (!mBitcasaSession.isLinked())
			mBitcasaSession.authenticate(USER, PW);
		assertNotNull(mBitcasaSession.isLinked());
		
		mRootFolder = new Folder();
		
		mRootList = mBitcasaSession.getFileSystem().list(null);
	}
	
	@Override
	protected void tearDown() throws Exception {
//		mBitcasaSession.unlink();
//		mBitcasaSession = null;
	}
	
	public void testUpload() {
		//upload to root
		BitcasaProgressListener listener = new BitcasaProgressListener() {

			@Override
			public void onProgressUpdate(String file, int percentage,
					ProgressAction action) {
				Log.d("test Upload", file + " percentage: " + percentage);
				
			}

			@Override
			public void canceled(String file, ProgressAction action) {
				// TODO Auto-generated method stub
				
			}
			
		};
		
		Item meta = null;
		try {
			meta = mRootFolder.upload(mBitcasaSession.getBitcasaClientApi(), "/sdcard/Download/New Document.docx", Exists.RENAME, listener);
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (InterruptedException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (BitcasaException e) {
			assertTrue(false);
			e.printStackTrace();
		}
		
		assertNotNull(meta);
		
		//upload to all folders under root
		for (int i=0; i<mRootList.length; i++) {
			if (mRootList[i].getFile_type().equals(FileType.FOLDER)) {
				Log.d("uploadFile", "Folder name: " + mRootList[i].getName());
				Item meta1 = null;
				try {
					meta1 = mBitcasaSession.getBitcasaClientApi().getBitcasaFileSystemApi().uploadFile(mRootList[i], "/sdcard/Pictures/house.jpg", Exists.RENAME, listener);
				} catch (IOException e) {
					assertTrue(false);
					e.printStackTrace();
				} catch (InterruptedException e) {
					assertTrue(false);
					e.printStackTrace();
				} catch (BitcasaException e) {
					assertTrue(false);
					e.printStackTrace();
				}
				
				assertNotNull(meta1);
			}
		}
	}
}
