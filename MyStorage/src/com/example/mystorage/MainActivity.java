package com.example.mystorage;

import java.io.File;
import java.io.IOException;

import com.bitcasa_fs.client.Container;
import com.bitcasa_fs.client.Item;
import com.bitcasa_fs.client.Item.FileType;
import com.bitcasa_fs.client.exception.BitcasaException;
import com.bitcasa_fs.client.utility.BitcasaProgressListener;

import android.support.v7.app.ActionBarActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	private final static String USERNAME = "you@email.com";
	private final static String PASSWORD = "xxxxxxxxxx";
	
	private static final String TAG = MainActivity.class.getSimpleName();
	private TextView mAuthenticateStatus;
	private Button mUpload;
	private Button mDownload;
	private EditText mUserInput;
	private Button mGo;
	private EditText mResult;
	
	private boolean mIsUpload = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mAuthenticateStatus = (TextView) findViewById(R.id.status);
		mUpload = (Button) findViewById(R.id.upload);
		mDownload = (Button) findViewById(R.id.download);
		mUserInput = (EditText) findViewById(R.id.userinput);
		mGo = (Button) findViewById(R.id.set_to_go);
		mResult = (EditText) findViewById(R.id.returnValue);
		
		//authenticate
		AuthorizationBitcasa bitcasaAuthorization = new AuthorizationBitcasa();
		bitcasaAuthorization.execute();
		
		mUpload.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!((MyStorageApplication) getApplication()).getBitcasaSession().isLinked()) {
					Toast.makeText(MainActivity.this, R.string.please_authorize, Toast.LENGTH_SHORT).show();
					return;
				}
				mUserInput.setHint(R.string.upload_file_path);
				mUserInput.setVisibility(View.VISIBLE);
				mGo.setVisibility(View.VISIBLE);
				mIsUpload = true;
			}
		});
		
		mDownload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!((MyStorageApplication) getApplication()).getBitcasaSession().isLinked()) {
					Toast.makeText(MainActivity.this, R.string.please_authorize, Toast.LENGTH_SHORT).show();
					return;
				}
				mUserInput.setHint(R.string.download_destination);
				mUserInput.setVisibility(View.VISIBLE);
				mGo.setVisibility(View.VISIBLE);
				mIsUpload = false;
			}
			
		});
		
		mGo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String path = mUserInput.getText().toString();
				if (path.length() <= 0) {
					Toast.makeText(MainActivity.this, R.string.please_enter_path, Toast.LENGTH_SHORT).show();
					return;
				}
				BitcasaDownloadUpload bitcasaActivity = new BitcasaDownloadUpload(mIsUpload, path);
				bitcasaActivity.execute();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private class AuthorizationBitcasa extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			try {
				((MyStorageApplication) getApplication()).getBitcasaSession().authenticate(USERNAME, PASSWORD);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BitcasaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ((MyStorageApplication) getApplication()).getBitcasaSession().isLinked();
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			if (result)
				mAuthenticateStatus.setText(R.string.authorized);
			else
				mAuthenticateStatus.setText(R.string.not_authorized);
		}
	}
	
	private class BitcasaDownloadUpload extends AsyncTask<Void, Void, Item> {
		private boolean mbForUpload;
		private String mPath;
		
		public BitcasaDownloadUpload(Boolean bForUpload, String path) {
			mbForUpload = bForUpload;
			mPath = path;
		}
		
		@Override
		protected Item doInBackground(Void... params) {
			
			BitcasaProgressListener listener = new BitcasaProgressListener() {

				@Override
				public void onProgressUpdate(String file, int percentage,
						ProgressAction action) {
					Log.d(TAG, "Action: " + ((action==ProgressAction.BITCASA_ACTION_UPLOAD)?"Upload ":"Download ") + file + " percentage: " + percentage);
				}

				@Override
				public void canceled(String file, ProgressAction action) {
					Log.d(TAG, ((action==ProgressAction.BITCASA_ACTION_UPLOAD)?"Upload ":"Download ") + file + " Cancelled");
				}
				
			};
			
			Item file = null;
			if (mbForUpload) {
				try {
					//upload to MyDrive
					file = ((MyStorageApplication) getApplication()).getBitcasaSession().getBitcasaClientApi().getBitcasaFileSystemApi().uploadFile(null, mPath, null, listener);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BitcasaException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				Item firstFile = null;
				//get directory list of MyDrive
				Container[] items = null;
				try {
					items = ((MyStorageApplication) getApplication()).getBitcasaSession().getBitcasaClientApi().getBitcasaFileSystemApi().getList(null, -1, 1, null);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (BitcasaException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if (items != null) {
					Log.d(TAG, "Number of items in MyDrive directory: " + items.length);
					
					for (int i=0; i<items.length; i++){
						if (items[i].getFile_type().equals(FileType.FILE)) {
							firstFile = items[i];
							break;
						}
					}
					
					//download first file on MyDrive
					if (firstFile != null) {
						String destinationPath = mPath + firstFile.getName();
						try {
							((MyStorageApplication) getApplication()).getBitcasaSession().getBitcasaClientApi().getBitcasaFileSystemApi().downloadFile(firstFile, 0, destinationPath, listener);
						} catch (BitcasaException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						file = firstFile;
					}
					else {
						Log.d(TAG, "no file found at this level");
					}
				}
			}	
			
			return file;
		}
		
		@Override
		protected void onPostExecute(Item result) {
			if (result != null) {
				if (mbForUpload) {
					Toast.makeText(MainActivity.this, R.string.file_uploaded, Toast.LENGTH_SHORT).show();
					mResult.setText(result.toString());
				}
				else
				{
					Toast.makeText(MainActivity.this, R.string.file_downloaded, Toast.LENGTH_SHORT).show();
					mResult.setText(result.toString());
				}
			}
			else {
				Toast.makeText(MainActivity.this, R.string.upload_failed, Toast.LENGTH_SHORT).show();
			}
			//
		}
		
	}
}
