package com.bitcasa.cloudfs;

import android.test.InstrumentationTestCase;
import android.util.Log;

import com.bitcasa.cloudfs.Item.FileType;
import com.bitcasa.cloudfs.Utils.BitcasaRESTConstants;
import com.bitcasa.cloudfs.Utils.BitcasaRESTConstants.RestoreMethod;
import com.bitcasa.cloudfs.Utils.BitcasaRESTConstants.VersionExists;
import com.bitcasa.cloudfs.exception.BitcasaAuthenticationException;
import com.bitcasa.cloudfs.exception.BitcasaException;
import com.bitcasa.cloudfs.exception.BitcasaRequestErrorException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

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
			result = mBitcasaSession.getRestAdapter().getList(null, -1, 1, null);
			assertNotNull(result);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BitcasaException e) {
			e.printStackTrace();
		}
		
		ArrayList<Item> deleteItems = new ArrayList<Item>();
		//collecting test data
		for (int i=0; i<result.length; i++) {
			if (result[i].getType().equals(FileType.FOLDER)) {
				if (mContainer == null)
					mContainer = (Container) result[i];
				else if (mToContainer == null)
					mToContainer = (Container) result[i];
			}
			else if (result[i].getType().equals(FileType.FILE)) {
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
			resultlist = mBitcasaSession.fileSystem().list(mContainer);
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
			bresult = mBitcasaSession.fileSystem().delete(mFilesToDelete);
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
		
		for (int i=0; i<bresult.length; i++)
			assertTrue(bresult[i]);
	}
	
	public void testMoveToContainer() {
		Item[] items=null;
		try {
			items = mBitcasaSession.fileSystem().move(mFilesToDelete, mContainer, null);
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (BitcasaException e) {
			assertTrue(false);
			e.printStackTrace();
		}
		
		assertNotNull(items);
		assertTrue(items.length>0);
	}
	
	public void testMoveToPath() {
		//test with correct folder destination, this should succeed
		Item[] toContainer=null;
		try {
			toContainer = mBitcasaSession.fileSystem().move(mFilesToDelete, mContainer.getAbsoluteParentPathId(), null);
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
			toContainer = mBitcasaSession.fileSystem().copy(mFilesToDelete, mContainer, null);
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
			toContainer = mBitcasaSession.fileSystem().copy(mFilesToDelete, mToContainer.getAbsoluteParentPathId(), null);
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
			updatedItems = mBitcasaSession.fileSystem().saveItems(mFilesToDelete, VersionExists.IGNORE);
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
			boolean[] results = mBitcasaSession.fileSystem().restore(trashitems, mContainer.getAbsoluteParentPathId(), BitcasaRESTConstants.RestoreMethod.FAIL);
			for (int i=0; i <results.length; i++)
				assertTrue(results[i]);
		} catch (UnsupportedEncodingException e) {
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
	public void testRestoreToContainerDestination() {
		String[] trashitems = new String[1];
		trashitems[0] = mTrashItem1.getAbsoluteParentPathId();
		try {
			boolean[] results = mBitcasaSession.fileSystem().restore(trashitems, mContainer, BitcasaRESTConstants.RestoreMethod.FAIL);
			for (int i=0; i <results.length; i++)
				assertTrue(results[i]);
		} catch (UnsupportedEncodingException e) {
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
	public void testRestoreTrashStrings() {
		String[] trashitems = new String[1];
		trashitems[0] = mTrashItem1.getPath();
		try {
			boolean[] results = mBitcasaSession.fileSystem().restore(trashitems, mContainer.getAbsoluteParentPathId(), BitcasaRESTConstants.RestoreMethod.FAIL);
			for (int i=0; i <results.length; i++)
				assertTrue(results[i]);
		} catch (UnsupportedEncodingException e) {
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
	public void testRestoreTrashItems() {
		String[] trashitems = new String[1];
		trashitems[0] = mTrashItem1.getAbsoluteParentPathId();
		try {
			boolean[] results = mBitcasaSession.fileSystem().restore(trashitems, mContainer, RestoreMethod.FAIL);
			for (int i=0; i <results.length; i++)
				assertTrue(results[i]);
		} catch (UnsupportedEncodingException e) {
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
	public void testFileHistoryWithPath() {
		Item[] versions = null;
		try {
			versions = mBitcasaSession.fileSystem().fileHistory(mFilesToDelete[0].getPath());
			assertTrue(versions.length<=10);
			assertTrue(versions.length>0);
			for (int i=0; i<versions.length; i++)
				assertNotNull(versions[i]);
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
	public void testFileHistoryWithItem() {
		Item[] versions = null;
		int currentVersion = mFilesToDelete[0].getVersion();
		try {
			versions = mBitcasaSession.fileSystem().fileHistory(mFilesToDelete[0]);
			assertTrue(versions.length<=currentVersion);
			assertTrue(versions.length>0);
			for (int i=0; i<versions.length; i++)
				assertNotNull(versions[i]);
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		}
	}

// TODO : Refactor
//	public void testActionHistory() {
//		ActionHistory history = null;
//		try {
//			history = mBitcasaSession.filesystem().actionHistory();
//			assertNotNull(history);
//			assertTrue(history.getSize() > 0);
//			assertTrue(history.getSize() <= 10);
//		} catch (IOException e) {
//			assertTrue(false);
//			e.printStackTrace();
//		}
//
//		//farther check for each action
//		for (Map.Entry<String, BaseAction> entry : history.actions.entrySet()) {
//			BaseAction action = entry.getValue();
//			switch(action.getAction()){
//			case SHARE_CREATE:
//				ActionShareCreate sc = action.new ActionShareCreate(action);
//				assertNotNull(sc);
//				assertNotNull(sc.getAction());
//				assertNotNull(sc.getDataShareKey());
//				assertNotNull(sc.getDataShareUrl());
//				assertNotNull(sc.getVersion());
//				assertNotNull(sc.getDataPaths());
//				break;
//			case SHARE_RECEIVE:
//				ActionShareReceive sr = action.new ActionShareReceive(action);
//				assertNotNull(sr);
//				assertNotNull(sr.getAction());
//				assertNotNull(sr.getVersion());
//				assertNotNull(sr.getDataExists());
//				assertNotNull(sr.getDataPath());
//				break;
//			case DEVICE_CREATE:
//				ActionDeviceCreate dc = action.new ActionDeviceCreate(action);
//				assertNotNull(dc);
//				assertNotNull(dc.getAction());
//				assertNotNull(dc.getVersion());
//				assertNotNull(dc.getDataId());
//				assertNotNull(dc.getDataName());
//				break;
//			case DEVICE_DELETE:
//				ActionDeviceDelete dd = action.new ActionDeviceDelete(action);
//				assertNotNull(dd);
//				assertNotNull(dd.getAction());
//				assertNotNull(dd.getVersion());
//				assertNotNull(dd.getDataId());
//				break;
//			case DEVICE_UPDATE:
//				ActionDeviceUpdate du = action.new ActionDeviceUpdate(action);
//				assertNotNull(du);
//				assertNotNull(du.getAction());
//				assertNotNull(du.getVersion());
//				assertNotNull(du.getDataIdFrom());
//				assertNotNull(du.getDataIdTo());
//				break;
//			case DELETE:
//				ActionDelete d = action.new ActionDelete(action);
//				assertNotNull(d);
//				assertNotNull(d.getAction());
//				assertNotNull(d.getVersion());
//				break;
//			case ALTER_META:
//				ActionAlterMeta am = action.new ActionAlterMeta(action);
//				assertNotNull(am);
//				assertNotNull(am.getAction());
//				assertNotNull(am.getVersion());
//				assertNotNull(am.getDataNameFrom());
//				assertNotNull(am.getDataNameTo());
//				assertNotNull(am.getPath());
//				assertNotNull(am.getType());
//				assertNotNull(am.getDataCreatedTo());
//				assertNotNull(am.getDateCreatedFrom());
//				break;
//			case COPY:
//				ActionCopy copy = action.new ActionCopy(action);
//				assertNotNull(copy);
//				assertNotNull(copy.getAction());
//				assertNotNull(copy.getVersion());
//				assertNotNull(copy.getDataExists());
//				assertNotNull(copy.getDataName());
//				assertNotNull(copy.getDataTo());
//				assertNotNull(copy.getPath());
//				assertNotNull(copy.getType());
//				break;
//			case CREATE:
//				ActionCreate c = action.new ActionCreate(action);
//				assertNotNull(c);
//				assertNotNull(c.getAction());
//				assertNotNull(c.getVersion());
//				assertNotNull(c.getPath());
//				assertNotNull(c.getType());
//				if (c.getType().equals(FileType.FILE)) {
//					assertNotNull(c.getDataExtension());
//					assertNotNull(c.getDataMime());
//					assertNotNull(c.getDataIsMirrored());
//					assertNotNull(c.getDataSize());
//					assertNotNull(c.getDataParentId());
//				}
//				assertNotNull(c.getDataName());
//				assertNotNull(c.getApplicationData());
//				assertNotNull(c.getDataDateContentLastModified());
//				assertNotNull(c.getDataDateCreated());
//				assertNotNull(c.getDataDateMetaLastModified());
//				break;
//			case MOVE:
//				ActionMove m = action.new ActionMove(action);
//				assertNotNull(m);
//				assertNotNull(m.getAction());
//				assertNotNull(m.getVersion());
//				assertNotNull(m.getDataExists());
//				assertNotNull(m.getDataName());
//				assertNotNull(m.getDataTo());
//				assertNotNull(m.getPath());
//				assertNotNull(m.getType());
//				break;
//			case TRASH:
//				ActionTrash t = action.new ActionTrash(action);
//				assertNotNull(t);
//				assertNotNull(t.getAction());
//				assertNotNull(t.getVersion());
//				assertNotNull(t.getPath());
//				assertNotNull(t.getType());
//				break;
//			}
//		}
//	}
}
