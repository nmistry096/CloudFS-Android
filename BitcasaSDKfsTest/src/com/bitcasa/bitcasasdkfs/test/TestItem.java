package com.bitcasa.bitcasasdkfs.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.bitcasa_fs.client.Container;
import com.bitcasa_fs.client.Item;
import com.bitcasa_fs.client.Item.FileType;
import com.bitcasa_fs.client.Session;
import com.bitcasa_fs.client.exception.BitcasaException;
import com.bitcasa_fs.client.exception.BitcasaRequestErrorException;

import android.test.InstrumentationTestCase;
import android.util.Log;

public class TestItem extends InstrumentationTestCase{
	private final static String CLIENT_ID = "1123456789012345678901234567890123456789012";
	private final static String CLIENT_SECRET = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
	private final static String USER = "youremail@email.com";
	private final static String PW = "password";
	private final static String ENDPOINT = "xxxxxxxxxx.cloudfs.io";
	
	private Session mBitcasaSession;
	private Container mContainer;
	private Container mToContainer;
	private Item mFile;
	private Item mTrashItem;
	
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
			if (result[i].getFile_type().equals(FileType.FOLDER)) {
				if (mContainer == null)
					mContainer = (Container) result[i];
				else if (mToContainer == null)
					mToContainer = (Container) result[i];
				else if (mFile != null)
					break;
			}
			else if (result[i].getFile_type().equals(FileType.FILE)) {
				if (mFile == null)
					mFile = result[i];
			}
		}
		
		//collect trash item for test
		Item[] trash = mBitcasaSession.getBitcasaClientApi().getBitcasaTrashApi().browseTrash();
		assertNotNull(trash);
		mTrashItem = trash[0];
	}
	
	@Override
	protected void tearDown() throws Exception {
//		mBitcasaSession.unlink();
//		mBitcasaSession = null;
		super.tearDown();
	}
	
	public void testItem() {
		assertNotNull(mFile);
		assertNotNull(mFile.getFile_type());
		if (mFile.getFile_type().equals(FileType.FILE)) {
			assertNotNull(mFile.getExtension());
			assertNotNull(mFile.getMime());
			assertNotNull(mFile.getSize());
		}
		assertNotNull(mFile.getAbsoluteParentPathId());
		assertNotNull(mFile.getAbsolutePath());	//absolutePath only apply to non-trash item
		assertNotNull(mFile.getBlocklist_id());
		assertNotNull(mFile.getBlocklist_key());
		assertNotNull(mFile.getId());
		assertNotNull(mFile.getName());
		assertNotNull(mFile.getParent_id());
		//only if this item is from trash
		//assertNotNull(mFile.getPathFromTrash());
		assertNotNull(mFile.getDate_content_last_modified());
		assertNotNull(mFile.getDate_created());
		assertNotNull(mFile.getDate_meta_last_modified());
		assertNotNull(mFile.getVersion());
		assertNotNull(mFile.isIs_mirrored());
	}
	
	public void testMove_ToWithContainer() {
		Item moveresult = null;
		try {
			moveresult = mFile.moveTo(mBitcasaSession.getBitcasaClientApi(), mToContainer);
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (BitcasaException e) {
			assertTrue(false);
			e.printStackTrace();
		}
		assertNotNull(moveresult);
	}
	
	public void testMove_ToWithString() {
		Item moveresult = null;
		try {
			moveresult = mFile.moveTo(mBitcasaSession.getBitcasaClientApi(), mToContainer.getAbsolutePath());
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (BitcasaException e) {
			assertTrue(false);
			e.printStackTrace();
		}
		assertNotNull(moveresult);
	}
	
	public void testCopy_ToContainer() {
		Item copyresult = null;
		try {
			copyresult = mFile.copyTo(mBitcasaSession.getBitcasaClientApi(), mToContainer);
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (BitcasaException e) {
			assertTrue(false);
			e.printStackTrace();
		}
		assertNotNull(copyresult);	
	}
	
	public void testCopy_ToWithString() {
		Item copyresult = null;
		try {
			copyresult = mFile.copyTo(mBitcasaSession.getBitcasaClientApi(), mToContainer.getAbsolutePath());
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (BitcasaException e) {
			assertTrue(false);
			e.printStackTrace();
		}
		assertNotNull(copyresult);
	}
	
	public void testDelete() {
		try {
			assertTrue(mFile.delete(mBitcasaSession.getBitcasaClientApi()));
		} catch (BitcasaRequestErrorException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (BitcasaException e) {
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
	public void testRestoreContainer() {
		try {
			assertTrue(mTrashItem.restore(mBitcasaSession.getBitcasaClientApi(), mContainer));
		} catch (UnsupportedEncodingException e) {
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
	public void testRestoreWithString() {
		try {
			assertTrue(mTrashItem.restore(mBitcasaSession.getBitcasaClientApi(), mContainer.getAbsolutePath()));
		} catch (UnsupportedEncodingException e) {
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
	public void testListHistory() {
		
		Item[] allversions = null;
		try {
			allversions = mFile.listHistory(mBitcasaSession.getBitcasaClientApi());
			assertNotNull(allversions);
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		}
		
		//for(int i=0; i< allversions.length; i++)
			//Log.d("testListHistory", allversions[i].toString());
	}
	
	public void testListSingleFileVersion() {
		Item version = null;
		try {
			version = mBitcasaSession.getBitcasaClientApi().getBitcasaFileSystemApi().listSingleFileVersion(mFile.getAbsolutePath(), 0);
			assertNotNull(version);
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		}
		
		Log.d("testListSingleFileVersion", version.toString());
	}
}
