package com.bitcasa.bitcasasdkfs.test;

import java.io.IOException;
import java.io.InputStream;

import com.bitcasa_fs.client.Container;
import com.bitcasa_fs.client.File;
import com.bitcasa_fs.client.Item;
import com.bitcasa_fs.client.Item.FileType;
import com.bitcasa_fs.client.Session;
import com.bitcasa_fs.client.exception.BitcasaException;
import com.bitcasa_fs.client.utility.BitcasaProgressListener;

import android.os.Environment;
import android.test.InstrumentationTestCase;
import android.util.Log;

public class TestFile extends InstrumentationTestCase{
	private final static String CLIENT_ID = "1123456789012345678901234567890123456789012";
	private final static String CLIENT_SECRET = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
	private final static String USER = "youremail@email.com";
	private final static String PW = "password";
	private final static String ENDPOINT = "xxxxxxxxxx.cloudfs.io";
	
	private Session mBitcasaSession;
	private File mFileToBeDownloaded;
	@Override
	protected void setUp() throws Exception {
		mBitcasaSession = new Session(getInstrumentation().getContext(), ENDPOINT, CLIENT_ID, CLIENT_SECRET);
		if (!mBitcasaSession.isLinked())
			mBitcasaSession.authenticate(USER, PW);
		assertNotNull(mBitcasaSession.isLinked());
		Item[] result = null;
		try {
			result = mBitcasaSession.getBitcasaClientApi().getBitcasaFileSystemApi().getList(null, 0, 1, null);
			assertNotNull(result);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BitcasaException e) {
			e.printStackTrace();
		}
		
		for (int i =6; i<result.length; i++) {
			if (result[i].getFile_type().equals(FileType.FILE)) {
				mFileToBeDownloaded = new File(result[i]);
				break;
			}
		}
	}
	
	@Override
	protected void tearDown() throws Exception {
		//mBitcasaSession.unlink();
		//mBitcasaSession = null;
	}
	
	public void testRead() throws BitcasaException, InterruptedException, IOException {
		InputStream is = mFileToBeDownloaded.read(mBitcasaSession.getBitcasaClientApi());
		if (is != null)
			is.close();
		assertNotNull(is);
	}
	
	public void testDownload() throws BitcasaException, InterruptedException, IOException {
		BitcasaProgressListener listener = new BitcasaProgressListener() {

			@Override
			public void onProgressUpdate(String file, int percentage,
					ProgressAction action) {
				
				Log.d("testDownload", file + " and percentage: "+ percentage);
			}

			@Override
			public void canceled(String file, ProgressAction action) {
				Log.d("testDownload", file + " cancelled");
			}
			
		};
		
		Log.d("testDownload", mFileToBeDownloaded.getName());
		mFileToBeDownloaded.download(mBitcasaSession.getBitcasaClientApi(), "sdcard/Bitcasa/"+ mFileToBeDownloaded.getName(), listener);
	}
	
}
