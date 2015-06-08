/**
 * Bitcasa Client Android SDK
 * Copyright (C) 2015 Bitcasa, Inc.
 * 1200 Park Place,
 * Suite 350 San Mateo, CA 94403.
 * <p/>
 * This file contains an SDK in Java for accessing the Bitcasa infinite drive in Android platform.
 * <p/>
 * For support, please send email to sdks@bitcasa.com.
 */
package com.bitcasa.cloudfs.Utils;

/**
 * Constants used in REST api requests
 *
 * @author Valina Li
 */
public class BitcasaRESTConstants {

    public static final String HTTPS = "https://";
    public static final String API_VERSION_2 = "/v2";
    public static final String FORWARDSLASH = "/";

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


    public static final String TAG_BITCASA_ORIGINAL_PATH = "_bitcasa_original_path";
    public static final String TAG_RELATIVE_ID_PATH = "relative_id_path";
    public static final String TAG_NONCE = "nonce";
    public static final String TAG_PAYLOAD = "payload";
    public static final String TAG_DIGEST = "digest";
    public static final String TAG_ALBUM_ART = "album_art";

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
    public static final String METHOD_ITEM = "/filesystem/root";
    public static final String METHOD_USER = "/user";
    public static final String METHOD_PROFILE = "/profile/";
    public static final String METHOD_META = "/meta";
    public static final String METHOD_PING = "/ping";
    public static final String METHOD_SHARES = "/shares/";
    public static final String METHOD_UNLOCK = "/unlock";
    public static final String METHOD_INFO = "/info";
    public static final String METHOD_HISTORY = "/history";
    public static final String METHOD_TRASH = "/trash/";
    public static final String METHOD_ADMIN = "/admin";
    public static final String METHOD_CLOUDFS = "/cloudfs";
    public static final String METHOD_CUSTOMERS = "/customers/";
    public static final String METHOD_PLAN = "plan/";

    public static final String PARAM_CLIENT_ID = "client_id";
    public static final String PARAM_REDIRECT = "redirect";
    public static final String PARAM_USER = "user";
    public static final String PARAM_NAME = "name";
    public static final String PARAM_LIMIT = "limit";
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
    public static final String PARAM_EMAIL = "email";
    public static final String PARAM_FIRSTNAME = "first_name";
    public static final String PARAM_LASTNAME = "last_name";
    public static final String PARAM_PLANCODE = "plan_code";
    public static final String PARAM_PLANID = "plan_id";
    public static final String PARAM_MIME = "mime";

    public static final String BODY_FOLDERNAME = "folder_name";
    public static final String BODY_FILE = "file";
    public static final String BODY_FROM = "from";
    public static final String BODY_TO = "to";
    public static final String BODY_EXISTS = "exists";
    public static final String BODY_NAME = "name";
    public static final String BODY_LIMIT = "limit";
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
    public static final String EXISTS_REUSE = "reuse";

    public static final String VERSION_FAIL = "fail";
    public static final String VERSION_IGNORE = "ignore";

    public static final String RESTORE_FAIL = "fail";
    public static final String RESTORE_RESCUE = "rescue";
    public static final String RESTORE_RECREATE = "recreate";

    public static final String ITEM_STATE_NORMAL = "normal";
    public static final String ITEM_STATE_SHARE = "share";
    public static final String ITEM_STATE_TRASH = "trash";
    public static final String ITEM_STATE_DEAD = "dead";

    public static final String START_VERSION = "start-version";
    public static final String STOP_VERSION = "stop-version";
    public static final String LIMIT = "limit";

    // update progress interval
    public static final long PROGRESS_UPDATE_INTERVAL = 2000;

    public static final int CONNECTION_TIME_OUT = 300000;

    public static final String DATE_FORMAT = "EEE, d MMM yyyy HH:mm:ss z";
    public static final String FORM_URLENCODED = "application/x-www-form-urlencoded; charset=\"utf-8\"";
    public static final String OAUTH_TOKEN = "/oauth2/token";

    public enum FileOperation {
        DELETE, COPY, MOVE, ADDFOLDER, ALTERMETA, META
    }

    public static enum Exists {
        FAIL, OVERWRITE, RENAME, REUSE
    }

    public static enum ApiMethod {
        GENERAL, ACCOUNT, GETLIST, ADD_FOLDER, DELETE, COPY, MOVE, META,
        LISTSHARE, SHARE, BROWSE_SHARE, LISTHISTORY, LIST_FILE_VERSIONS,
        LIST_SINGLE_FILE_VERSION, PROMOTE_FILE_VERSION, UPLOAD, CREATE_TEST_USER_ACCOUNT, RECEIVE_SHARE
    }

    public static enum VersionExists {
        FAIL, IGNORE
    }

    public static enum RestoreMethod {
        FAIL, RESCUE, RECREATE
    }

    public static enum HistoryActions {
        SHARE_RECEIVE("share_receive"),
        SHARE_CREATE("share_create"),
        DEVICE_UPDATE("device_update"),
        DEVICE_CREATE("device_create"),
        DEVICE_DELETE("device_delete"),
        ALTER_META("alter_meta"),
        COPY("copy"),
        MOVE("move"),
        CREATE("create"),
        DELETE("delete"),
        TRASH("trash");

        private final String historyAction;

        private HistoryActions(final String result) {
            this.historyAction = result;
        }

        public static BitcasaRESTConstants.HistoryActions getResult(final String result) {
            final BitcasaRESTConstants.HistoryActions historyActions;
            if ("share_receive".equals(result)) {
                historyActions = BitcasaRESTConstants.HistoryActions.SHARE_RECEIVE;
            } else if ("share_create".equals(result)) {
                historyActions = BitcasaRESTConstants.HistoryActions.SHARE_CREATE;
            } else if ("device_update".equals(result)) {
                historyActions = BitcasaRESTConstants.HistoryActions.DEVICE_UPDATE;
            } else if ("device_create".equals(result)) {
                historyActions = BitcasaRESTConstants.HistoryActions.DEVICE_CREATE;
            } else if ("device_delete".equals(result)) {
                historyActions = BitcasaRESTConstants.HistoryActions.DEVICE_DELETE;
            } else if ("alter_meta".equals(result)) {
                historyActions = BitcasaRESTConstants.HistoryActions.ALTER_META;
            } else if ("copy".equals(result)) {
                historyActions = BitcasaRESTConstants.HistoryActions.COPY;
            } else if ("move".equals(result)) {
                historyActions = BitcasaRESTConstants.HistoryActions.MOVE;
            } else if ("create".equals(result)) {
                historyActions = BitcasaRESTConstants.HistoryActions.CREATE;
            } else if ("delete".equals(result)) {
                historyActions = BitcasaRESTConstants.HistoryActions.DELETE;
            } else if ("trash".equals(result)) {
                historyActions = BitcasaRESTConstants.HistoryActions.TRASH;
            } else {
                historyActions = null;
            }
            return historyActions;
        }
    }

    /**
     * Attribute name used when accessing an action's data.
     */
    public static final String ATTRIBUTE_DATA = "data";

    /**
     * Actions that use the {@link com.bitcasa.cloudfs.model.ActionDataDefault ActionDataDefault} object.
     */
    public static final String[] DEFAULT_ACTIONS = {"share_receive", "share_create", "device_create", "device_delete",
            "copy", "move", "create", "delete", "trash"};

    /**
     * Actions that use the {@link com.bitcasa.cloudfs.model.ActionDataAlter ActionDataAlter} object.
     */
    public static final String[] ALTER_ACTIONS = {"device_update", "alter_meta"};
}
