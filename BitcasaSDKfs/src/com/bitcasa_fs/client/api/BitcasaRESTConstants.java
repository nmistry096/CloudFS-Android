/**
 * Bitcasa Client Android SDK
 * Copyright (C) 2013 Bitcasa, Inc.
 * 215 Castro Street, 2nd Floor
 * Mountain View, CA 94041
 *
 * This file contains an SDK in Java for accessing the Bitcasa infinite drive in Android platform.
 *
 * For support, please send email to support@bitcasa.com.
 */
package com.bitcasa_fs.client.api;

public class BitcasaRESTConstants {
	
	public static final String API_VERSION_2 = "/v2";
	public static final String FORESLASH = "/";
	
	public static final String UTF_8_ENCODING = "UTF-8";	
	
	public static final String HEADER_CONTENT_TYPE = "Content-Type";
	public static final String HEADER_CONTENT_TYPE_APP_URLENCODED = "application/x-www-form-urlencoded; charset=\"utf-8\"";
	public static final String HEADER_ACCEPT_CHARSET = "Accept-Charset";
	public static final String HEADER_XAUTH = "XAuth";
	public static final String HEADER_RANGE = "Range";
	public static final String HEADER_CONNECTION = "Connection";
	public static final String HEADER_CONNECTION_KEEP_ALIVE = "Keep-Alive";
	public static final String HEADER_ENCTYPE = "ENCTYPE";
	public static final String HEADER_ENCTYPE_MULTIPART = "multipart/form-data";
	public static final String HEADER_CONTENT_TYPE_MULTIPART_BOUNDARY = "multipart/form-data;boundary=";
	public static final String HEADER_FILE = "file";
	public static final String HEADER_AUTORIZATION = "Authorization";
	public static final String HEADER_DATE = "Date";
		
	public static final String REQUEST_METHOD_GET = "GET";
	public static final String REQUEST_METHOD_POST = "POST";
	public static final String REQUEST_METHOD_PUT = "PUT";
	public static final String REQUEST_METHOD_DELETE = "DELETE";
	public static final String REQUEST_METHOD_HEAD = "HEAD";
	
	public static final String METHOD_AUTHENTICATE = "/authenticate";
	public static final String METHOD_OAUTH2 = "/oauth2";
	public static final String METHOD_ACCESS_TOKEN = "/access_token";
	public static final String METHOD_AUTHORIZE = "/authorize";
	public static final String METHOD_TOKEN = "/token";
	public static final String METHOD_FOLDERS = "/folders";
	public static final String METHOD_FILES = "/files";
	public static final String METHOD_USER = "/user";
	public static final String METHOD_PROFILE = "/profile/";
	public static final String METHOD_META = "/meta";
	public static final String METHOD_PING = "/ping";
	public static final String METHOD_SHARES = "/shares/";
	public static final String METHOD_UNLOCK = "/unlock";
	public static final String METHOD_INFO = "/info";
	public static final String METHOD_HISTORY = "/history";
	public static final String METHOD_TRASH = "/trash/";
	
	public static final String PARAM_CLIENT_ID = "client_id";
	public static final String PARAM_REDIRECT = "redirect";
	public static final String PARAM_USER = "user";
	public static final String PARAM_PASSWORD = "password";
	public static final String PARAM_CURRENT_PASSWORD = "current_password";
	public static final String PARAM_SECRET = "secret";
	public static final String PARAM_CODE = "code";
	public static final String PARAM_RESPONSE_TYPE = "response_type";
	public static final String PARAM_REDIRECT_URI = "redirect_uri";
	public static final String PARAM_GRANT_TYPE = "grant_type";
	public static final String PARAM_PATH = "path";
	public static final String PARAM_FOLDER_NAME = "folder_name";	
	public static final String PARAM_ACCESS_TOKEN = "access_token";
	public static final String PARAM_DEPTH = "depth";
	public static final String PARAM_FILTER = "filter";
	public static final String PARAM_LATEST = "latest";
	public static final String PARAM_CATEGORY = "category";
	public static final String PARAM_ID = "id";
	public static final String PARAM_INDIRECT = "indirect";
	public static final String PARAM_FILENAME = "filename";
	public static final String PARAM_EXISTS = "exists";
	public static final String PARAM_OPERATION = "operation";
	public static final String PARAM_USERNAME = "username";
	public static final String PARAM_VERSION = "version";
	public static final String PARAM_VERSIONS = "versions";
	public static final String PARAM_VERSION_CONFLICT = "version-conflict";
	public static final String PARAM_COMMIT = "commit";
	public static final String PARAM_FORCE = "force";
	public static final String PARAM_TRUE = "true";
	public static final String PARAM_FALSE = "false";
	public static final String PARAM_START = "start";
	public static final String PARAM_STOP = "stop";
	
	public static final String BODY_FOLDERNAME = "folder_name";
	public static final String BODY_FILE = "file";
	public static final String BODY_FROM = "from";
	public static final String BODY_TO = "to";
	public static final String BODY_EXISTS = "exists";
	public static final String BODY_NAME = "name";
	public static final String BODY_PATH = "path";
	public static final String BODY_RESTORE = "restore";
	public static final String BODY_RESCUE_PATH = "rescue-path";
	public static final String BODY_RECREATE_PATH = "recreate-path";
	
	public static final String OPERATION_COPY = "copy";
	public static final String OPERATION_MOVE = "move";
	public static final String OPERATION_CREATE = "create";
	public static final String OPERATION_PROMOTE = "promote";
	
	public static final String EXISTS_FAIL = "fail";
	public static final String EXISTS_OVERWRITE = "overwrite";
	public static final String EXISTS_RENAME = "rename";
	
	public static final String VERSION_FAIL = "fail";
	public static final String VERSION_IGNORE = "ignore";
	
	public static final String RESTORE_FAIL = "fail";
	public static final String RESTORE_RESCUE = "rescue";
	public static final String RESTORE_RECREATE = "recreate";
	
	public static final String START_VERSION = "start-version";
	public static final String STOP_VERSION = "stop-version";
	public static final String LIMIT = "limit";
	
	// update progress interval
	public static final long PROGRESS_UPDATE_INTERVAL			= 2000;
	
	public static final String DATE_FORMAT = "EEE, d MMM yyyy HH:mm:ss z";
	public static final String FORM_URLENCODED = "application/x-www-form-urlencoded; charset=\"utf-8\"";
	public static final String OAUTH_TOKEN = "/oauth2/token";
	
	public enum FileOperation {
        DELETE, COPY, MOVE, ADDFOLDER, ALTERMETA, META;
    }
	
	public static enum Exists {
		FAIL, OVERWRITE, RENAME, REUSE;
	}
	
	public static enum ApiMethod {
		GENERAL, ACCOUNT, GETLIST, ADD_FOLDER, DELETE, COPY, MOVE, META, 
		LISTSHARE, SHARE, BROWSE_SHARE, LISTHISTORY, LIST_FILE_VERSIONS, 
		LIST_SINGLE_FILE_VERSION, PROMOTE_FILE_VERSION, UPLOAD;
	}
	
	public static enum VersionExists {
		FAIL, IGNORE;		
	}
	
	public static enum RestoreOptions {
		FAIL, RESCUE, RECREATE;
	}
}
