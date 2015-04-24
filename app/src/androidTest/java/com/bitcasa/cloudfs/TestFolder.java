package com.bitcasa.cloudfs;


import android.test.InstrumentationTestCase;
import android.util.Log;

import com.bitcasa.cloudfs.Item.FileType;
import com.bitcasa.cloudfs.Utils.BitcasaProgressListener;
import com.bitcasa.cloudfs.Utils.BitcasaRESTConstants.Exists;
import com.bitcasa.cloudfs.exception.BitcasaException;

import java.io.IOException;

public class TestFolder extends InstrumentationTestCase {
	private final static String CLIENT_ID = "1123456789012345678901234567890123456789012";
	private final static String CLIENT_SECRET = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
	private final static String USER = "youremail@email.com";
	private final static String PW = "password";
	private final static String ENDPOINT = "xxxxxxxxxx.cloudfs.io";
	
	private Session mBitcasaSession;
	private Folder mRootFolder;
	private Item[] mRootList;
	private Item[] mSecondLevelList;
	@Override
	protected void setUp() throws Exception {
		mBitcasaSession = new Session(getInstrumentation().getContext(), ENDPOINT, CLIENT_ID, CLIENT_SECRET);
		if (!mBitcasaSession.isLinked())
			mBitcasaSession.authenticate(USER, PW);
		assertNotNull(mBitcasaSession.isLinked());
		
		mRootFolder = new Folder();
		
		mRootList = mBitcasaSession.fileSystem().list(mRootFolder);
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
			meta = mRootFolder.upload(mBitcasaSession.getBitcasaClientApi(), "/sdcard/Download/1.docx", "fail", listener);
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
		
		
	}
	
	public void testUploadSecondLevel() {
		
		try {
			for (int i=0; i<mRootList.length; i++) {
				Log.d("ROOT LIST", mRootList[i].getAbsoluteParentPathId());
				if (mRootList[i].getType().equals(FileType.FOLDER)) {
					mSecondLevelList = mBitcasaSession.fileSystem().list(mRootList[i]);
					Log.d("Second LIST", mRootList[i].getAbsoluteParentPathId());
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BitcasaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertNotNull(mSecondLevelList);
		
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
		
		for (int i=0; i<mSecondLevelList.length; i++) {
			if (mSecondLevelList[i].getType().equals(FileType.FOLDER)) {
				Log.d("uploadFile", "Folder name: " + mSecondLevelList[i].getName());
				Item meta1 = null;
				try {
					meta1 = mBitcasaSession.getBitcasaClientApi().getBitcasaFileSystemApi().uploadFile(mRootList[i], "/sdcard/Pictures/DMT.png", Exists.RENAME, listener);
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
