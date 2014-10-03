package com.bitcasa_fs.client.api;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;

import javax.net.ssl.HttpsURLConnection;

import android.util.Log;

import com.bitcasa_fs.client.BitcasaError;
import com.bitcasa_fs.client.Container;
import com.bitcasa_fs.client.Item;
import com.bitcasa_fs.client.Item.FileType;
import com.bitcasa_fs.client.Session;
import com.bitcasa_fs.client.api.BitcasaRESTConstants;
import com.bitcasa_fs.client.api.BitcasaRESTConstants.ApiMethod;
import com.bitcasa_fs.client.api.BitcasaRESTConstants.Exists;
import com.bitcasa_fs.client.api.BitcasaRESTConstants.FileOperation;
import com.bitcasa_fs.client.api.BitcasaRESTConstants.VersionExists;
import com.bitcasa_fs.client.api.BitcasaRESTUtility;
import com.bitcasa_fs.client.api.MultipartUpload;
import com.bitcasa_fs.client.ParseJSON.BitcasaParseJSON;
import com.bitcasa_fs.client.datamodel.Credential;
import com.bitcasa_fs.client.exception.BitcasaAuthenticationException;
import com.bitcasa_fs.client.exception.BitcasaClientException;
import com.bitcasa_fs.client.exception.BitcasaException;
import com.bitcasa_fs.client.exception.BitcasaFileException;
import com.bitcasa_fs.client.exception.BitcasaRequestErrorException;
import com.bitcasa_fs.client.utility.BitcasaProgressListener;
import com.bitcasa_fs.client.utility.BitcasaProgressListener.ProgressAction;

/**
 * File system related api requests
 * @author Valina Li
 *
 */
public class BitcasaFileSystemApi {
	private static final String TAG = BitcasaFileSystemApi.class.getSimpleName();
	private BitcasaRESTUtility bitcasaRESTUtility;
	private Credential credential;
	
	/**
	 * Constructor
	 * @param credential
	 * @param utility
	 */
	public BitcasaFileSystemApi(Credential credential, BitcasaRESTUtility utility) {
		this.bitcasaRESTUtility = utility;
		this.credential = credential;
	}
	
	/**
     * list all files and folders under given folder path   
     * @param folderPath
     * @param version
     * @param depth
     * @param filter
     * @return
     * @throws IOException
     * @throws BitcasaException
     */
    public Container[] getList(String folderPath, int version, int depth, String filter) throws IOException, BitcasaException {    	
    	Container[] files = null;
    	StringBuilder endpoint = new StringBuilder();
    	
    	endpoint.append(BitcasaRESTConstants.METHOD_FOLDERS);
    	if (folderPath != null)
    		endpoint.append(folderPath);
    	else
    		endpoint.append(File.separator);
    	
		TreeMap<String, String> parameters = new TreeMap<String, String>();
		if (version >= 0)
			parameters.put(BitcasaRESTConstants.PARAM_VERSION, Integer.toString(version));
		if (depth >=0 )
			parameters.put(BitcasaRESTConstants.PARAM_DEPTH, Integer.toString(depth));
		if (filter != null)
			parameters.put(BitcasaRESTConstants.PARAM_FILTER, filter);
		
		String url = bitcasaRESTUtility.getRequestUrl(credential, endpoint.toString(), null, parameters.size()>0?parameters:null);
					
		Log.d(TAG, "getList URL: " + url);
		HttpsURLConnection connection = null;
		InputStream is = null;
		BitcasaParseJSON parser = null;
		
		try {
			connection = (HttpsURLConnection) new URL(url)
					.openConnection();
			connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_GET);
			bitcasaRESTUtility.setRequestHeaders(credential, connection, null);
			
			BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);
			if (error == null) {
				is = connection.getInputStream();
				parser = new BitcasaParseJSON(ApiMethod.GETLIST, folderPath==null?File.separator:folderPath);
				if (parser.readJsonStream(is))
					files = parser.files;
			}
			
		} catch (IOException ioe) {
			if (ioe.getMessage().contains("authentication challenge"))
				throw new BitcasaAuthenticationException(1020, "Authorization code not recognized");
			else if (ioe != null)
				ioe.printStackTrace();
		} catch (Exception e) {
			if (e != null)
				e.printStackTrace();
		} finally {
			if (is != null)
				is.close();
			
			if (connection != null)
				connection.disconnect();
		}
		
		return files;
	}
    
    /**
     * Download a file from Bitcasa file system
     * @param file - Bitcasa Item with valid bitcasa file path and file name
     * @param range - Any valid content range. No less than 0, no greater than the filesize.
     * @param localDestination - device file location with file path and name
     * @param listener - to listen to the file download progress
     * @throws BitcasaException
     * @throws IOException
     */
    public void downloadFile(Item file, long range, String localDestination, BitcasaProgressListener listener) throws BitcasaException, InterruptedException, IOException {
    	File local = new File(localDestination);
    	if (!local.exists()) {
			if (!local.getParentFile().mkdirs() && !local.getParentFile().exists())
				throw new BitcasaException(9007, "Appliation not authorized to perform this action");
		}
    	else {
    		throw new BitcasaFileException("File already existed.");
    	}
    	
    	//create file
    	local.createNewFile();
  
		String url;
		url = bitcasaRESTUtility.getRequestUrl(credential, BitcasaRESTConstants.METHOD_FILES, file.getAbsoluteParentPathId(), null);
		
		HttpsURLConnection connection = null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		
		TreeMap<String, String> header = new TreeMap<String, String>();
		header.put(BitcasaRESTConstants.HEADER_RANGE, "bytes=" + range + "-");
		
			Log.d(TAG, "downloadFile url: " + url);
			try {
				connection = (HttpsURLConnection) new URL(url).openConnection();			
				connection.setDoInput(true);
				bitcasaRESTUtility.setRequestHeaders(credential, connection, header);
				
				// check response code first
				BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);
				if (error == null) {
					// prepare for writing to file
					bis = new BufferedInputStream(connection.getInputStream());
					fos = (range == 0) ? new FileOutputStream(local) : new FileOutputStream(local, true);	// if file exists append
					bos = new BufferedOutputStream(fos);
					
					// start writing
					byte[] data = new byte[1024];
					int x = 0;
					// use for progress update
					BigInteger fileSize = new BigInteger(Long.toString(file.getSize()));
					BigInteger dataReceived = BigInteger.valueOf(range);
					long progressUpdateTimer = System.currentTimeMillis();
					while ((x = bis.read(data, 0, 1024)) >= 0) {
						if (Thread.currentThread().isInterrupted()) {
							listener.canceled(file.getName(), ProgressAction.BITCASA_ACTION_DOWNLOAD);
							break;
						}
						bos.write(data, 0, x);
						dataReceived = dataReceived.add(BigInteger.valueOf(x));
						// update progress
						if (listener != null && (System.currentTimeMillis() - progressUpdateTimer) > BitcasaRESTConstants.PROGRESS_UPDATE_INTERVAL) {
							int percentage = dataReceived.multiply(BigInteger.valueOf(100)).divide(fileSize).intValue();
							listener.onProgressUpdate(file.getName(), percentage, ProgressAction.BITCASA_ACTION_DOWNLOAD);
							progressUpdateTimer = System.currentTimeMillis();
						}
						
						// make sure everything is written to the file so we can compare the size later
						bos.flush();
					}
					
					// make sure that we did download the whole file
					Log.d(TAG, "local file size: " + local.length() + ", file size should be: " + fileSize);
				}
				
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			finally {
				
				if (bos != null)
					bos.close();
				if (fos != null)
					fos.close();
				if (bis != null)
					bis.close();
				
				if(Thread.interrupted()) {
					local.delete();
				}
				
				if (connection != null)
					connection.disconnect();
			}
		
    }
    
    /**
     * Download a file from Bitcasa file system
     * @param file
     * @param range
     * @return InputStream
     * @throws BitcasaException
     * @throws InterruptedException
     * @throws IOException
     */
    public InputStream download(Item file, long range) throws BitcasaException, InterruptedException, IOException {
    	InputStream is = null;
		String url;
		url = bitcasaRESTUtility.getRequestUrl(credential, BitcasaRESTConstants.METHOD_FILES, file.getAbsoluteParentPathId(), null);
		
		HttpsURLConnection connection = null;
		TreeMap<String, String> header = new TreeMap<String, String>();
		header.put(BitcasaRESTConstants.HEADER_RANGE, "bytes=" + range + "-");
		
			Log.d(TAG, "download url: " + url);
			try {
				connection = (HttpsURLConnection) new URL(url).openConnection();			
				connection.setDoInput(true);
				bitcasaRESTUtility.setRequestHeaders(credential, connection, header);
				
				BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);
				if (error == null)
					is = connection.getInputStream();
				
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			finally {
				if (connection != null)
					connection.disconnect();
			}
			
		return is;
    }
    
    /**
     * Upload a local file to Bitcasa CloudFS server
     * @param destination folder
     * @param sourcefile
     * @param cr
     * @param listener
     * @return Bitcasa file meta data of this successful upload, null if upload failed 
     * @throws IOException
     * @throws InterruptedException
     * @throws BitcasaException
     */
    public Container uploadFile(Item folder, String sourcefile, Exists cr, BitcasaProgressListener listener) throws IOException, InterruptedException, BitcasaException {
    	Container meta = null;
    	File sourceFile = new File(sourcefile); 
    	if (!sourceFile.exists() || !sourceFile.canRead()) {
    		throw new BitcasaClientException("Unable to read file: " + sourcefile);
		}
    	
    	String path = File.separator;
    	if (folder != null && folder.getAbsoluteParentPathId() != null)
    		path = folder.getAbsoluteParentPathId();
    	
		String urlRequest = bitcasaRESTUtility.getRequestUrl(credential, BitcasaRESTConstants.METHOD_FILES, path, null);
		Log.d(TAG, "uploadFile url: " + urlRequest);
		
		String fieldValue;
		if (cr == null)
			fieldValue = BitcasaRESTConstants.EXISTS_RENAME;
		else {
			switch(cr) {
			case FAIL:
				fieldValue =BitcasaRESTConstants.EXISTS_FAIL;
				break;
			case OVERWRITE:
				fieldValue = BitcasaRESTConstants.EXISTS_OVERWRITE;
				break;
			case RENAME:
				default:
					fieldValue = BitcasaRESTConstants.EXISTS_RENAME;
					break;
			}
		}
		
		MultipartUpload mpUpload = new MultipartUpload(credential, urlRequest, bitcasaRESTUtility);
		mpUpload.addUploadFormField(BitcasaRESTConstants.PARAM_EXISTS, fieldValue);
		mpUpload.addFile(sourceFile, listener);
		meta = mpUpload.finishUpload();
		return meta;
    }
    
    /**
     * Delete an existing folder from Bitcasa file system, including all directories and files under this folder.
     * @param path
     * @param bCommit
     * @return
     * @throws BitcasaRequestErrorException
     * @throws IOException
     * @throws BitcasaException
     */
    public boolean deleteFolder(String path, boolean bCommit) throws BitcasaRequestErrorException, IOException, BitcasaException {
    	return delete(path, FileType.FOLDER.toString(), bCommit);
    }
    
    /**
     * Delete an existing file from Bitcasa file system
     * @param path
     * @param bCommit
     * @return
     * @throws BitcasaRequestErrorException
     * @throws IOException
     * @throws BitcasaException
     */
    public boolean deleteFile(String path, boolean bCommit) throws BitcasaRequestErrorException, IOException, BitcasaException {
    	return delete(path, FileType.FILE.toString(), bCommit);
    }
    
    /**
     * Delete an item from Bitcasa CloudFS
     * @param path
     * @param fileType (file or folder)
     * @param bCommit
     * @return
     * @throws BitcasaRequestErrorException
     * @throws IOException
     * @throws BitcasaException
     */
    private boolean delete(String path, String fileType, boolean bCommit) throws BitcasaRequestErrorException, IOException, BitcasaException { 
    	boolean bResult = false;
    	
    	TreeMap<String, String> queryParam = new TreeMap<String, String>();
    	queryParam.put(BitcasaRESTConstants.PARAM_COMMIT, bCommit?BitcasaRESTConstants.PARAM_TRUE:BitcasaRESTConstants.PARAM_FALSE);
    	
    	String request = BitcasaRESTConstants.METHOD_FILES;
    	if (fileType.equals(FileType.FOLDER)) {
    		request = BitcasaRESTConstants.METHOD_FOLDERS;
    		queryParam.put(BitcasaRESTConstants.PARAM_FORCE, BitcasaRESTConstants.PARAM_TRUE);
    	}
    	
    	String url = bitcasaRESTUtility.getRequestUrl(credential, request, path, queryParam);
    	
    	Log.d(TAG, "deleteFile URL: " + url);
		HttpsURLConnection connection = null;
		InputStream is = null;
		BitcasaParseJSON parser = null;
		
		try {
			connection = (HttpsURLConnection) new URL(url)
					.openConnection();
			connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_DELETE);
			bitcasaRESTUtility.setRequestHeaders(credential, connection, null);
			
			BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);
			if (error == null) {
				is = connection.getInputStream();			
				parser = new BitcasaParseJSON(ApiMethod.DELETE, path);
				if (parser.readJsonStream(is))
					bResult = parser.bSuccessResult;
			}
			
		} catch (IOException ioe) {
			if (ioe.getMessage().contains("authentication challenge"))
				throw new BitcasaAuthenticationException(1020, "Authorization code not recognized");
			else if (ioe != null)
				ioe.printStackTrace();
		} catch (Exception e) {
			if (e != null)
				e.printStackTrace();
		} finally {
			if (is != null)
				is.close();
			
			if (connection != null)
				connection.disconnect();			
		}
		return bResult;
    }  
    
    /**
     * Create a folder in Bitcasa file system
     * 
     * @param newfolderName
     * @param parentFolder
     * @return
     * @throws BitcasaRequestErrorException
     * @throws IOException
     */
    public Container createFolder(String newfolderName, Item parentFolder) throws BitcasaRequestErrorException, IOException {
    	return copyMoveCreate(FileOperation.ADDFOLDER, parentFolder, null, newfolderName, Exists.RENAME);
    }
    
    /**
     * Copy a file or a folder from one location to another within Bitcasa file system
     * @param from
     * @param toFolder
     * @param cr
     * @return null or Bitcasa Item of copied file when success.
     * @throws IOException
     * @throws BitcasaException
     */
    public Container copy(Item from, String toFolderPath, String newName, Exists cr) throws IOException, BitcasaException {
    	return copyMoveCreate(FileOperation.COPY, from, toFolderPath, newName, Exists.RENAME);
    }
    
    /**
     * Move a file or a folder from one location to another within Bitcasa file system
     * @param from
     * @param toFolder
     * @param cr
     * @return null or Bitcasa Item of moved file when success.
     * @throws IOException
     * @throws BitcasaException
     */
    public Container move(Item from, String toFolderPath, String newName, Exists cr) throws IOException, BitcasaException {
    	return copyMoveCreate(FileOperation.MOVE, from, toFolderPath, newName, Exists.RENAME);
    }
    
    /**
     * Get File Meta
     * @param absolutepath
     * @param fileType
     * @return
     * @throws BitcasaAuthenticationException
     * @throws IOException
     */
    public Container getMeta(String absolutepath, String fileType) throws BitcasaAuthenticationException, IOException {
    	Container meta = null;
    	StringBuilder endpoint = new StringBuilder();
    	if (fileType.equals(FileType.FOLDER))
    		endpoint.append(BitcasaRESTConstants.METHOD_FOLDERS);
    	else if (fileType.equals(FileType.FILE))
    		endpoint.append(BitcasaRESTConstants.METHOD_FILES);
    	
    	endpoint.append(absolutepath);
		String url = bitcasaRESTUtility.getRequestUrl(credential, endpoint.toString(), BitcasaRESTConstants.METHOD_META, null);
					
		Log.d(TAG, "getMeta URL: " + url);
		HttpsURLConnection connection = null;
		InputStream is = null;
		BitcasaParseJSON parser = null;
		
		try {
			connection = (HttpsURLConnection) new URL(url)
					.openConnection();
			connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_GET);
			bitcasaRESTUtility.setRequestHeaders(credential, connection, null);
			
			BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);
			if (error == null) {
				is = connection.getInputStream();			
				parser = new BitcasaParseJSON(ApiMethod.META, absolutepath);
				if (parser.readJsonStream(is))
					meta = parser.meta;
			}
			
		} catch (IOException ioe) {
			if (ioe.getMessage().contains("authentication challenge"))
					throw new BitcasaAuthenticationException(1020, "Authorization code not recognized");
			else if (ioe != null)
				ioe.printStackTrace();
		} catch (Exception e) {
			if (e != null)
				e.printStackTrace();
		} finally {
			if (is != null)
				is.close();
			if (connection != null)
				connection.disconnect();
		}
		
		
		return meta;
    }
    
    /**
     * Alter File Meta
     * @param meta
     * @param changes
     * @param version
     * @param vcr
     * @return
     * @throws BitcasaAuthenticationException
     * @throws IOException
     */
    public Container alterMeta(Item meta, Map<String, String> changes, int version, VersionExists vcr) throws BitcasaAuthenticationException, IOException {
    	Container metaResult = null;
    	StringBuilder endpoint = new StringBuilder();
    	if (meta.getFile_type().equals(FileType.FOLDER))
    		endpoint.append(BitcasaRESTConstants.METHOD_FOLDERS);
    	else if (meta.getFile_type().equals(FileType.FILE))
    		endpoint.append(BitcasaRESTConstants.METHOD_FILES);
    	
    	endpoint.append(meta.getAbsoluteParentPathId());
		String url = bitcasaRESTUtility.getRequestUrl(credential, endpoint.toString(), BitcasaRESTConstants.METHOD_META, null);
		
		String versionConflict = BitcasaRESTConstants.VERSION_FAIL;
		if (vcr != null && vcr==VersionExists.IGNORE)
			versionConflict = BitcasaRESTConstants.VERSION_IGNORE;
		
		TreeMap<String, String> formParams = new TreeMap<String, String>();
		formParams.put(BitcasaRESTConstants.PARAM_VERSION, Integer.toString(version));
		if (vcr != null)
			formParams.put(BitcasaRESTConstants.PARAM_VERSION_CONFLICT, vcr.toString());
		formParams.putAll(changes);
				
		Log.d(TAG, "alterMeta URL: " + url);
		HttpsURLConnection connection = null;
		InputStream is = null;
		BitcasaParseJSON parser = null;
		OutputStream os = null;
		try {
			connection = (HttpsURLConnection) new URL(url)
					.openConnection();
			connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_POST);
			bitcasaRESTUtility.setRequestHeaders(credential, connection, null);
			
			os = connection.getOutputStream();
			os.write(bitcasaRESTUtility.generateParamsString(formParams).getBytes());
			
			BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);
			if (error == null) {
				is = connection.getInputStream();
				parser = new BitcasaParseJSON(ApiMethod.META, meta.getAbsoluteParentPathId());
				if (parser.readJsonStream(is))
					metaResult = parser.meta;
			}
			
		} catch (IOException ioe) {
			if (ioe.getMessage().contains("authentication challenge"))
					throw new BitcasaAuthenticationException(1020, "Authorization code not recognized");
			else if (ioe != null)
				ioe.printStackTrace();
		} catch (Exception e) {
			if (e != null)
				e.printStackTrace();
		} finally {
			if (is != null) 
				is.close();		
			if (connection != null)
				connection.disconnect();
		}
		
    	return metaResult;
    }
    
    /**
     * File operation: copy, move, or create Bitcasa file system item (file or folder)
     * @param operationType
     * @param fileFrom
     * @param fileToPath
     * @param filename
     * @param cr
     * @return updated file meta data after file operation
     * @throws BitcasaRequestErrorException
     * @throws IOException
     */
    private Container copyMoveCreate(FileOperation operationType, Item fileFrom, String fileToPath, String filename, Exists cr) throws BitcasaRequestErrorException, IOException {
    	
    	Container result = null;
    	
    	StringBuilder request = new StringBuilder();    	
    	if (operationType == FileOperation.ADDFOLDER || fileFrom.getFile_type().equals(Item.FileType.FOLDER))
    		request.append(BitcasaRESTConstants.METHOD_FOLDERS);
    	else
    		request.append(BitcasaRESTConstants.METHOD_FILES);
    	
    	String parent = null;
    	if (fileFrom != null && fileFrom.getAbsoluteParentPathId() != null)   		
    		parent = fileFrom.getAbsoluteParentPathId();
    	else
    		parent = File.separator;
    	request.append(parent);
    	
    	TreeMap<String, String> queryParams = new TreeMap<String, String>();
		TreeMap<String, String> params = new TreeMap<String, String>();	
		switch (operationType) {
    	case COPY:
	    	{
	    		queryParams.put(BitcasaRESTConstants.PARAM_OPERATION, BitcasaRESTConstants.OPERATION_COPY);
	    		params.put(BitcasaRESTConstants.BODY_TO, URLEncoder.encode(fileToPath, BitcasaRESTConstants.UTF_8_ENCODING));
	    		if (filename != null && filename.length() > 0)
	    			params.put(BitcasaRESTConstants.BODY_NAME, URLEncoder.encode(filename, BitcasaRESTConstants.UTF_8_ENCODING));
	    	}
    		break;
    	case MOVE:
	    	{
	    		queryParams.put(BitcasaRESTConstants.PARAM_OPERATION, BitcasaRESTConstants.OPERATION_MOVE);
	    		params.put(BitcasaRESTConstants.BODY_TO, URLEncoder.encode(fileToPath, BitcasaRESTConstants.UTF_8_ENCODING));
	    		if (filename != null && filename.length() > 0)
	    			params.put(BitcasaRESTConstants.BODY_NAME, URLEncoder.encode(filename, BitcasaRESTConstants.UTF_8_ENCODING));
	    		else
	    			params.put(BitcasaRESTConstants.BODY_NAME, URLEncoder.encode(fileFrom.getName(), BitcasaRESTConstants.UTF_8_ENCODING));
	    	}
    		break;
    	case ADDFOLDER:
	    	{
	    		queryParams.put(BitcasaRESTConstants.PARAM_OPERATION, BitcasaRESTConstants.OPERATION_CREATE);
	    		params.put(BitcasaRESTConstants.BODY_NAME, URLEncoder.encode(filename, BitcasaRESTConstants.UTF_8_ENCODING));
	    	}
    		break;
    		default:
    			return null;
    	}
		
		if (cr != null) {
			switch(cr) {
				case FAIL:
					params.put(BitcasaRESTConstants.BODY_EXISTS, BitcasaRESTConstants.EXISTS_FAIL);
					break;
				case OVERWRITE:
					params.put(BitcasaRESTConstants.BODY_EXISTS, BitcasaRESTConstants.EXISTS_OVERWRITE);
					break;
				case RENAME:
					default:
						params.put(BitcasaRESTConstants.BODY_EXISTS, BitcasaRESTConstants.EXISTS_RENAME);
					break;
			}
		}
		
		String body = bitcasaRESTUtility.generateParamsString(params);
		
		String url = bitcasaRESTUtility.getRequestUrl(credential, request.toString(), null, queryParams);
		Log.d(TAG, "copyMoveCreate: " + url + " body: " + body);
		HttpsURLConnection connection = null;
		OutputStream os = null;
		InputStream is = null;
		try {
			connection = (HttpsURLConnection) new URL(url.toString())
					.openConnection();
			connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_POST);
			bitcasaRESTUtility.setRequestHeaders(credential, connection, null);
			
			if (body != null) {
				os = connection.getOutputStream();
				os.write(body.getBytes());
			}
			
			BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);
			if (error == null) {
				is = connection.getInputStream();	
				BitcasaParseJSON parser = new BitcasaParseJSON(bitcasaRESTUtility.getApiMethodFromFileOperation(operationType), parent);
				if (parser.readJsonStream(is)) {
					
					switch (operationType) {
			    	case COPY:
			    		result = parser.meta;
			    		break;
			    	case MOVE:
			    		result = parser.meta;
			    		break;
			    	case ADDFOLDER:
				    	Container[] resultList = parser.files;
				    	result = resultList[0];
			    		break;
			    	}
				}
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (BitcasaException e) {
			e.printStackTrace();
		} finally {
			if (os != null)
				os.close();
			if (is != null)
				is.close();
			if (connection != null)
				connection.disconnect();
		}

		return result;
    }
    
    /**
     * List File Versions
     * @param path
     * @param startVersion
     * @param stopVersion
     * @param limit
     * @return This returns a list of file meta data reults, as they have been recorded in the file version history after successful meta data changes.
     * @throws IOException
     */
    public Container[] listFileVersions(String path, int startVersion, int stopVersion, int limit) throws IOException {
    	Container[] versions = null;
    	
    	TreeMap<String, String> queryParams = new TreeMap<String, String>();
    	if (startVersion >= 0)
    		queryParams.put(URLEncoder.encode(BitcasaRESTConstants.START_VERSION, BitcasaRESTConstants.UTF_8_ENCODING), Integer.toString(startVersion));
    	if (stopVersion >= 0)
    		queryParams.put(URLEncoder.encode(BitcasaRESTConstants.STOP_VERSION, BitcasaRESTConstants.UTF_8_ENCODING), Integer.toString(stopVersion));
    	if (limit >= 0)
    		queryParams.put(URLEncoder.encode(BitcasaRESTConstants.LIMIT, BitcasaRESTConstants.UTF_8_ENCODING), Integer.toString(limit));
    	
    	StringBuilder sb = new StringBuilder();
    	sb.append(path);
    	sb.append(BitcasaRESTConstants.PARAM_VERSIONS);
    	String url = bitcasaRESTUtility.getRequestUrl(credential, BitcasaRESTConstants.METHOD_FILES, sb.toString(), queryParams.size()>0?queryParams:null);
    	Log.d(TAG, "listFileVersions: " + url);
		HttpsURLConnection connection = null;
		InputStream is = null;
		try {
			connection = (HttpsURLConnection) new URL(url).openConnection();
			connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_GET);
			bitcasaRESTUtility.setRequestHeaders(credential, connection, null);
			BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);
			if (error == null) {
				is = connection.getInputStream();	
				BitcasaParseJSON parser = new BitcasaParseJSON(ApiMethod.LIST_FILE_VERSIONS , null);
				if (parser.readJsonStream(is))
					versions = parser.files;
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (BitcasaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (is != null)
				is.close();
			if (connection != null)
				connection.disconnect();
		}
    	return versions;
    }
    
    /**
     * List Single File Version
     * @param path
     * @param version
     * @return Returns the file meta data for the given version of the file.
     * @throws IOException
     */
    public Container listSingleFileVersion(String path, int version) throws IOException {
    	Container file = null;    	

    	StringBuilder sb = new StringBuilder();
    	sb.append(path);
    	sb.append(BitcasaRESTConstants.PARAM_VERSIONS);
    	sb.append(File.separator);
    	sb.append(Integer.toString(version));
    	String url = bitcasaRESTUtility.getRequestUrl(credential, BitcasaRESTConstants.METHOD_FILES, sb.toString(), null);
    	Log.d(TAG, "listSingleFileVersion: " + url);
		HttpsURLConnection connection = null;
		InputStream is = null;
		try {
			connection = (HttpsURLConnection) new URL(url).openConnection();
			connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_GET);
			bitcasaRESTUtility.setRequestHeaders(credential, connection, null);
			BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);
			if (error == null) {
				is = connection.getInputStream();	
				BitcasaParseJSON parser = new BitcasaParseJSON(ApiMethod.LIST_SINGLE_FILE_VERSION , null);
				if (parser.readJsonStream(is))
					file = parser.meta;
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (BitcasaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (is != null)
				is.close();
			if (connection != null)
				connection.disconnect();
		}
    	return file;
    }
    
    /**
     * Given a specified version of a file, set that version’s meta data as the current set of meta data on the file, creating a new version in the process.
     * @param path
     * @param version
     * @return Returns the newest meta data with the new version number as well.
     * @throws IOException
     */
    public Container PromoteFileVersion(String path, int version) throws IOException {
    	Container file = null;
    	
    	StringBuilder sb = new StringBuilder();
    	sb.append(path);
    	sb.append(File.separator);
    	sb.append(BitcasaRESTConstants.PARAM_VERSIONS);
    	sb.append(File.separator);
    	sb.append(Integer.toString(version));
    	
    	TreeMap<String, String> queryParams = new TreeMap<String, String>();
    	queryParams.put(BitcasaRESTConstants.PARAM_OPERATION, BitcasaRESTConstants.OPERATION_PROMOTE);
    	
    	String url = bitcasaRESTUtility.getRequestUrl(credential, BitcasaRESTConstants.METHOD_FILES, sb.toString(), queryParams);
    	Log.d(TAG, "PromoteFileVersion: " + url);
		HttpsURLConnection connection = null;
		InputStream is = null;
		try {
			connection = (HttpsURLConnection) new URL(url).openConnection();
			connection.setRequestMethod(BitcasaRESTConstants.REQUEST_METHOD_POST);
			bitcasaRESTUtility.setRequestHeaders(credential, connection, null);
			BitcasaError error = bitcasaRESTUtility.checkRequestResponse(connection);
			if (error == null) {
				is = connection.getInputStream();	
				BitcasaParseJSON parser = new BitcasaParseJSON(ApiMethod.PROMOTE_FILE_VERSION , null);
				if (parser.readJsonStream(is))
					file = parser.meta;
			}
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} catch (BitcasaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (is != null)
				is.close();
			if (connection != null)
				connection.disconnect();
		}
    	
    	return file;
    }
}
