package com.bitcasa.bitcasasdkfs.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import com.bitcasa_fs.client.Container;
import com.bitcasa_fs.client.Item;
import com.bitcasa_fs.client.Session;
import com.bitcasa_fs.client.Item.FileType;
import com.bitcasa_fs.client.api.BitcasaRESTConstants.RestoreOptions;
import com.bitcasa_fs.client.api.BitcasaRESTConstants.VersionExists;
import com.bitcasa_fs.client.exception.BitcasaAuthenticationException;
import com.bitcasa_fs.client.exception.BitcasaException;
import com.bitcasa_fs.client.exception.BitcasaRequestErrorException;

import android.test.InstrumentationTestCase;
import android.util.Log;

public class TestFileSystem extends InstrumentationTestCase{
	private final static String CLIENT_ID = "1123456789012345678901234567890123456789012";
	private final static String CLIENT_SECRET = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
	private final static String USER = "youremail@email.com";
	private final static String PW = "password";
	private final static String ENDPOINT = "xxxxxxxxxx.cloudfs.io";
	
	private Session mBitcasaSession;
	private Container mContainer;
	private Container mToContainer;
	private Item[] mFilesToDelete;
	private Item mTrashItem1;
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
		
		ArrayList<Item> deleteItems = new ArrayList<Item>();
		//collecting test data
		for (int i=0; i<result.length; i++) {
			if (result[i].getFile_type().equals(FileType.FOLDER)) {
				if (mContainer == null)
					mContainer = (Container) result[i];
				else if (mToContainer == null)
					mToContainer = (Container) result[i];
			}
			else if (result[i].getFile_type().equals(FileType.FILE)) {
				if (deleteItems.size() < 2)
					deleteItems.add(result[i]);
			}
		}
		
		mFilesToDelete = deleteItems.toArray(new Item[deleteItems.size()]);
		
		//collect trash item for test
		Item[] trash = mBitcasaSession.getBitcasaClientApi().getBitcasaTrashApi().browseTrash();
		assertNotNull(trash);
		mTrashItem1 = trash[0];
	}
	
	@Override
	protected void tearDown() throws Exception {
		//mBitcasaSession.unlink();
		//mBitcasaSession = null;
	}
	
	public void testListItem() {
		Item[] resultlist = null;
		try {
			resultlist = mBitcasaSession.getFileSystem().list(mContainer);
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (BitcasaException e) {
			assertTrue(false);
			e.printStackTrace();
		}
		assertNotNull(resultlist);
	}
	
	public void testDeleteItems() {
		boolean[] bresult = null;
		
		for (int i=0; i<mFilesToDelete.length; i++)
			Log.d("testDeleteItems", "items to delete: " + mFilesToDelete[i].toString());
		
		try {
			bresult = mBitcasaSession.getFileSystem().delete(mFilesToDelete);
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
		assertNotNull(bresult);
	}
	
	public void testMoveToContainer() {
		Item[] items=null;
		try {
			items = mBitcasaSession.getFileSystem().move(mFilesToDelete, mContainer, null);
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (BitcasaException e) {
			assertTrue(false);
			e.printStackTrace();
		}
		assertNotNull(items);
	}
	
	public void testMoveToPath() {
		//test with correct folder destination, this should succeed
		Item[] toContainer=null;
		try {
			toContainer = mBitcasaSession.getFileSystem().move(mFilesToDelete, mContainer.getAbsolutePath(), null);
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (BitcasaException e) {
			assertTrue(false);
			e.printStackTrace();
		}
		for (int i=0; i<toContainer.length; i++)
			assertNotNull(toContainer[i]);
	}
	
	public void testCopyToContainer() {
		Item[] toContainer = null;
		try {
			toContainer = mBitcasaSession.getFileSystem().copy(mFilesToDelete, mContainer, null);
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (BitcasaException e) {
			assertTrue(false);
			e.printStackTrace();
		}
		
		for (int i=0; i<toContainer.length; i++)
			assertNotNull(toContainer[i]);
	}
	
	public void testCopyToPath() {
		Item[] toContainer=null;
		try {
			toContainer = mBitcasaSession.getFileSystem().copy(mFilesToDelete, mToContainer.getAbsolutePath(), null);
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (BitcasaException e) {
			assertTrue(false);
			e.printStackTrace();
		}
		for (int i=0; i<toContainer.length; i++)
			assertNotNull(toContainer[i]);
	}
	
	public void testSaveItems() {
		Item[] updatedItems=null;
		try {
			updatedItems = mBitcasaSession.getFileSystem().saveItems(mFilesToDelete, VersionExists.IGNORE);
		} catch (BitcasaAuthenticationException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		}
		assertNotNull(updatedItems);
	}
	
	public void testRestoreToStringDestination() {
		Item[] trashitems = new Item[1];
		trashitems[0] = mTrashItem1;
		try {
			boolean[] results = mBitcasaSession.getFileSystem().restore(trashitems, mContainer.getAbsolutePath(), RestoreOptions.FAIL);
			assertTrue(results[0]);
		} catch (UnsupportedEncodingException e) {
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
	public void testRestoreToContainerDestination() {
		String[] trashitems = new String[1];
		trashitems[0] = mTrashItem1.getAbsolutePath();
		try {
			boolean[] results = mBitcasaSession.getFileSystem().restore(trashitems, mContainer, RestoreOptions.FAIL);
			assertTrue(results[0]);
		} catch (UnsupportedEncodingException e) {
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
	public void testRestoreTrashStrings() {
		String[] trashitems = new String[1];
		trashitems[0] = mTrashItem1.getAbsolutePath();
		try {
			boolean[] results = mBitcasaSession.getFileSystem().restore(trashitems, mContainer.getAbsolutePath(), RestoreOptions.FAIL);
			assertTrue(results[0]);
		} catch (UnsupportedEncodingException e) {
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
	public void testRestoreTrashItems() {
		String[] trashitems = new String[1];
		trashitems[0] = mTrashItem1.getAbsolutePath();
		try {
			boolean[] results = mBitcasaSession.getFileSystem().restore(trashitems, mContainer, RestoreOptions.FAIL);
			assertTrue(results[0]);
		} catch (UnsupportedEncodingException e) {
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
}
