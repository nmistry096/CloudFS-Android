package com.bitcasa_fs.client.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

import android.util.Log;

import com.bitcasa_fs.client.BitcasaError;
import com.bitcasa_fs.client.Container;
import com.bitcasa_fs.client.Item;
import com.bitcasa_fs.client.Session;
import com.bitcasa_fs.client.api.BitcasaRESTConstants;
import com.bitcasa_fs.client.api.BitcasaRESTUtility;
import com.bitcasa_fs.client.api.MultipartUpload;
import com.bitcasa_fs.client.ParseJSON.BitcasaParseJSON;
import com.bitcasa_fs.client.api.BitcasaRESTConstants.ApiMethod;
import com.bitcasa_fs.client.datamodel.Credential;
import com.bitcasa_fs.client.exception.BitcasaException;
import com.bitcasa_fs.client.utility.BitcasaProgressListener;
import com.bitcasa_fs.client.utility.BitcasaProgressListener.ProgressAction;

public class MultipartUpload {

	private static final String TAG = MultipartUpload.class.getSimpleName();
	
	private final String LINE_FEED = "\r\n";
	private String boundary;
	private HttpsURLConnection connection = null;
	private OutputStream outputStream;
	private PrintWriter printWriter;
	private long progressUpdateTimer = System.currentTimeMillis();
	private String url;
	private BitcasaRESTUtility mRestUtility;
	
	public MultipartUpload(Credential credential, String url, BitcasaRESTUtility utility) throws MalformedURLException, IOException {
		this.url = url;
		this.mRestUtility = utility;
		
		boundary = "---" + System.currentTimeMillis() + "---";

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put(BitcasaRESTConstants.HEADER_CONTENT_TYPE, "multipart/form-data; boundary="
				+ boundary);

		connection = (HttpsURLConnection) new URL(url).openConnection();
		connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_POST);
		mRestUtility.setRequestHeaders(credential, connection, headers);
		connection.setChunkedStreamingMode(0);
		outputStream = connection.getOutputStream();
		printWriter = new PrintWriter(new OutputStreamWriter(outputStream, BitcasaRESTConstants.UTF_8_ENCODING), true);
	}
	
	public void addUploadFormField(String fieldname, String fieldvalue) {
		printWriter.append("--" + boundary).append(LINE_FEED);
		printWriter.append("Content-Disposition: form-data; name=\"" + fieldname + "\"")
                .append(LINE_FEED);
		printWriter.append("Content-Type: text/plain; charset=" + BitcasaRESTConstants.UTF_8_ENCODING).append(
				LINE_FEED);
		printWriter.append(LINE_FEED);
		printWriter.append(fieldvalue).append(LINE_FEED);
		printWriter.flush();
	}
	
	public void addFile(File uploadFile, BitcasaProgressListener listener) throws IOException {
		long fileTotalSize = uploadFile.length();
		long totalTransferred = 0;
		String fileName = uploadFile.getName();
		printWriter.append("--" + boundary).append(LINE_FEED);
		printWriter.append(
				"Content-Disposition: form-data; name=\"" + BitcasaRESTConstants.BODY_FILE
						+ "\"; filename=\"" + fileName + "\"").append(
								LINE_FEED);
		printWriter.append("Content-Transfer-Encoding: binary")
				.append(LINE_FEED);
		printWriter.append(LINE_FEED);
		printWriter.flush();

		FileInputStream inputStream = new FileInputStream(uploadFile);
		byte[] buffer = new byte[4096];
		int bytesRead = -1;
		try {
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				if (!Thread.currentThread().isInterrupted()) {
					outputStream.write(buffer, 0, bytesRead);
					totalTransferred += bytesRead;
					if (listener != null
							&& (System.currentTimeMillis() - progressUpdateTimer) > BitcasaRESTConstants.PROGRESS_UPDATE_INTERVAL) {
						Log.d(TAG, "file transfered so far: "
								+ totalTransferred + " out of total file size: "
								+ fileTotalSize);
						int percentage = (int) (totalTransferred * 100 / fileTotalSize);
						listener.onProgressUpdate(fileName, percentage == 0 ? 1
								: percentage, ProgressAction.BITCASA_ACTION_UPLOAD);
						progressUpdateTimer = System.currentTimeMillis();
					}
				} else {
					listener.canceled(fileName, ProgressAction.BITCASA_ACTION_UPLOAD);
					throw new IOException("Thread Interrupted");
				}
			}
		} finally {
			if (inputStream != null)
				inputStream.close();
		}
		outputStream.flush();
		inputStream.close();

		printWriter.append(LINE_FEED);
		printWriter.flush();
		
		printWriter.append("--" + boundary + "--").append(LINE_FEED);
		printWriter.close();
	}
	
	public Container finishUpload() {
		Container meta = null;
		InputStream is = null;
		
		int responseCode;
		try {
			responseCode = connection.getResponseCode();
			Log.d(TAG, "upload response code: " + responseCode);
			BitcasaError error = mRestUtility.checkRequestResponse(connection);
			if (error == null) {
				is = connection.getInputStream();					
				BitcasaParseJSON parser = new BitcasaParseJSON(ApiMethod.UPLOAD, null);
				if (is != null) {
					parser.readJsonStream(is);
					meta = parser.meta;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BitcasaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			if (connection != null)
				connection.disconnect();
		}
		
		return meta;
	}
}
