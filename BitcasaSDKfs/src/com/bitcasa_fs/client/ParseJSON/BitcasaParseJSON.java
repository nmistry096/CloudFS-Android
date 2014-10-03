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
package com.bitcasa_fs.client.ParseJSON;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.util.Log;

import com.bitcasa_fs.client.Account;
import com.bitcasa_fs.client.ActionHistory;
import com.bitcasa_fs.client.BitcasaError;
import com.bitcasa_fs.client.Container;
import com.bitcasa_fs.client.Item;
import com.bitcasa_fs.client.User;
import com.bitcasa_fs.client.api.BitcasaClientApi;
import com.bitcasa_fs.client.api.BitcasaRESTConstants;
import com.bitcasa_fs.client.api.BitcasaRESTConstants.ApiMethod;
import com.bitcasa_fs.client.api.BitcasaRESTConstants.HistoryActions;
import com.bitcasa_fs.client.datamodel.ApplicationData;
import com.bitcasa_fs.client.datamodel.BaseAction;
import com.bitcasa_fs.client.datamodel.Profile;
import com.bitcasa_fs.client.datamodel.ShareItem;
import com.bitcasa_fs.client.exception.BitcasaException;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;


public class BitcasaParseJSON {
	
	private static final String TAG_RESULT = "result";
	private static final String TAG_ACCESS_TOKEN = "access_token";
	private static final String TAG_TOKEN_TYPE = "token_type";
	
	//DELETE
	private static final String TAG_SUCCESS = "success";
	//GETLIST
	private static final String TAG_META = "meta";
	private static final String TAG_ITEMS = "items";
	
	//meta
	private static final String TAG_PARENT_ID = "parent_id";
	private static final String TAG_TYPE = "type";
	private static final String TAG_NAME = "name";
	private static final String TAG_DATE_CREATED = "date_created";
	private static final String TAG_DATE_META_LAST_MODIFIED = "date_meta_last_modified";
	private static final String TAG_DATE_CONTENT_LAST_MODIFIED = "date_content_last_modified";
	private static final String TAG_VERSION = "version";
	private static final String TAG_IS_MIRRORED = "is_mirrored";
	private static final String TAG_APPLICATION_DATA = "application_data";
	private static final String TAG_BLOCKLIST_KEY = "blocklist_key";
	private static final String TAG_EXTENSION = "extension";
	private static final String TAG_BLOCKLIST_ID = "blocklist_id";
	private static final String TAG_SIZE = "size";
	private static final String TAG_MIME = "mime";
	
	//account
	private static final String TAG_USERNAME = "username";
	private static final String TAG_CREATED_AT = "created_at";
	private static final String TAG_FIRST_NAME = "first_name";
	private static final String TAG_LAST_NAME = "last_name";
	private static final String TAG_ACCOUNT_ID = "account_id";
	private static final String TAG_LOCALE = "locale";
	private static final String TAG_ACCOUNT_STATE = "account_state";
	private static final String TAG_DISPLAY_NAME = "display_name";
	private static final String TAG_ID = "id";
	private static final String TAG_STORAGE = "storage";
	private static final String TAG_STORAGE_LIMIT = "limit";
	private static final String TAG_STORAGE_USAGE = "usage";
	private static final String TAG_STORAGE_OTL = "otl";
	private static final String TAG_ACCOUNT_PLAN = "account_plan";
	private static final String TAG_CTA_TEXT = "cta_text";
	private static final String TAG_CTA_VALUE = "cta_value";
	private static final String TAG_EMAIL = "email";
	private static final String TAG_SESSION = "session";
	private static final String TAG_LAST_LOGIN = "last_login";
	private static final String TAG_HOLDS = "holds";
	
	//error
	private static final String TAG_ERROR = "error";
	private static final String TAG_ERROR_CODE = "code";
	private static final String TAG_ERROR_MESSAGE = "message";	
	private static final String TAG_ERROR_DATA = "data";
	
	//share
	private static final String TAG_SHARE_KEY = "share_key";
	private static final String TAG_URL = "url";
	private static final String TAG_SHORT_URL = "short_url";
	private static final String TAG_SHARE = "share";
	
	//history
	private static final String TAG_ACTION = "action";
	private static final String TAG_DATA = "data";
	private static final String TAG_PATH = "path";
	private static final String TAG_EXISTS = "exists";
	private static final String TAG_PATHS = "paths";
	private static final String TAG_SHARE_URL = "share_url";
	private static final String TAG_FROM = "from";
	private static final String TAG_TO = "to";
		
	
	//application data
	private static final String TAG_SERVER = "_server";
	private static final String TAG_BITCASA_ORIGINAL_PATH = "_bitcasa_original_path";
	private static final String TAG_RELATIVE_ID_PATH = "relative_id_path";
	private static final String TAG_NEBULA = "nebula";
	private static final String TAG_NONCE = "nonce";
	private static final String TAG_PAYLOAD = "payload";
	private static final String TAG_DIGEST = "digest";
	private static final String TAG_ALBUM_ART = "album_art";
	
	
	public String accessToken;
	public String tokenType;
	public BitcasaError bitcasaError = new BitcasaError();
	public Container[] files;
	public int numDeleted = 0;
	public ApiMethod requestMethod;
	public String requestAbsoluteParentPathId;
	public Container meta;
	public boolean bSuccessResult;
	public ShareItem[] listShares;
	public ShareItem share;
	public ActionHistory history;
	public Profile profile;
	
	public BitcasaParseJSON(ApiMethod method, String absoluteParentPathId) {
		requestMethod = method;
		requestAbsoluteParentPathId = absoluteParentPathId;
	}
	
	public boolean readJsonStream(InputStream in) throws IOException, BitcasaException {
		JsonReader reader = new JsonReader(new InputStreamReader(in, BitcasaRESTConstants.UTF_8_ENCODING));
		return getResult(reader);
	}
	
	private boolean getResult(JsonReader reader) throws IOException, BitcasaException {
		reader.beginObject();
		while (reader.hasNext()) {
			if (reader.peek() == JsonToken.NULL)
				break;
			String name = reader.nextName();
			if (name.equals(TAG_ERROR) && reader.peek() != JsonToken.NULL) {
				getErrorSecondLevel(reader);
			}
			else if (name.equals(TAG_RESULT) && reader.peek() != JsonToken.NULL) {
				switch(requestMethod) {
				case ACCOUNT:
				case CREATE_TEST_USER_ACCOUNT:
					parseAccountInfo(reader);
					break;
				case GETLIST:
					parseList(reader);
					break;
				case LISTSHARE:
					listShares = parseListShare(reader);
					break;
				case SHARE:
					share = parseShare(reader);
					break;
				case BROWSE_SHARE:
					parseBrowseShare(reader);
					break;
				case LISTHISTORY:
					history = parseListHistory(reader);
					break;
				case LIST_FILE_VERSIONS:
				case RECEIVE_SHARE:
					files = parseItemsArray(reader);
					break;
				case ADD_FOLDER:
					parseAddFolder(reader);
					break;
				case LIST_SINGLE_FILE_VERSION:
				case PROMOTE_FILE_VERSION:
				case UPLOAD:
					meta = parseMeta(reader);
					break;
				case DELETE:
				case COPY:
				case MOVE:
				case META:
				case GENERAL:
					default:
						getResultSecondLevel(reader);
						break;
				}
			}
			else if (name.equals(TAG_ERROR_MESSAGE) && reader.peek() != JsonToken.NULL) {
				bitcasaError.setMessage(reader.nextString());
			}
			else if (name.equals(TAG_ACCESS_TOKEN) && reader.peek() != JsonToken.NULL) {
				accessToken = reader.nextString();
				Log.d("BitcasaParseJSON", accessToken);
			}
			else if (name.equals(TAG_TOKEN_TYPE) && reader.peek() != JsonToken.NULL) {
				tokenType = reader.nextString();
			}
			else {
				reader.skipValue();
			}
		}
		reader.endObject();
		
		if (bitcasaError.getError() != null)
			return false;
		else
			return true;
	}
	
	private void parseAddFolder(JsonReader reader) throws IOException {
		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals(TAG_ITEMS)) {
				files = parseItemsArray(reader);
			}
			else
				reader.skipValue();
		}
		reader.endObject();
	}
	
	private void getErrorSecondLevel(JsonReader reader) throws IOException{
		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals(TAG_ERROR_MESSAGE) && reader.peek() != JsonToken.NULL) {
				bitcasaError.setMessage(reader.nextString());
			}
			else if (name.equals(TAG_ERROR_CODE) && reader.peek() != JsonToken.NULL) {
				bitcasaError.setCode(reader.nextInt());
			}
			else if (name.equals(TAG_ERROR_DATA) && reader.peek() != JsonToken.NULL) {
				bitcasaError.setData(reader.nextString());
			}
			else
				reader.skipValue();
		}
		reader.endObject();
	}
	
	private void getResultSecondLevel(JsonReader reader) throws IOException{
		reader.beginObject();
		
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals(TAG_ACCESS_TOKEN)) {
				accessToken = reader.nextString();
				break;
			}
			else if (name.equals(TAG_ERROR_CODE)){
				bitcasaError.setCode(reader.nextInt());
			}
			else if (name.equals(TAG_ERROR_MESSAGE)) {
				bitcasaError.setMessage(reader.nextString());
			}						
			else if (name.equals(TAG_ITEMS)) {
				//READ ARRAY
				files = parseItemsArray(reader);
			}
			else if (name.equals(TAG_META)) {
				meta = parseMeta(reader);
			}	
			else if (name.equals(TAG_SUCCESS)) {
				bSuccessResult = reader.nextBoolean();
			}
			else if (name.equals(TAG_VERSION)) {
				reader.nextInt();
			}
			else
				reader.skipValue();
				
		}
		
		reader.endObject();
	
	}
	
	private void parseList(JsonReader reader) throws IOException {
		reader.beginObject();	
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals(TAG_META)) {
				meta = parseMeta(reader);
			}
			else if (name.equals(TAG_ITEMS)) {
				reader.beginArray();
				ArrayList<Item> tfiles = new ArrayList<Item>();
				while (reader.hasNext()) {
					Item f = parseMeta(reader);
					if (f != null)
						tfiles.add(f);
				}
				reader.endArray();
				files = tfiles.toArray(new Container[tfiles.size()]);
			}
			else
				reader.skipValue();
		}
		reader.endObject();
	}
	
	private Container parseMeta(JsonReader reader) throws IOException {
		reader.beginObject();		
		Container file = new Container();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals(TAG_ID))
				file.setId(reader.nextString());
			else if (name.equals(TAG_PARENT_ID))
				file.setParent_id(reader.nextString());
			else if (name.equals(TAG_TYPE))
				file.setFile_type(reader.nextString());
			else if (name.equals(TAG_NAME))
				file.setName(reader.nextString());
			else if (name.equals(TAG_DATE_CREATED))
				file.setDate_created(reader.nextLong());
			else if (name.equals(TAG_DATE_META_LAST_MODIFIED))
				file.setDate_meta_last_modified(reader.nextLong());
			else if (name.equals(TAG_DATE_CONTENT_LAST_MODIFIED))
				file.setDate_content_last_modified(reader.nextLong());
			else if (name.equals(TAG_VERSION))
				file.setVersion(reader.nextInt());
			else if (name.equals(TAG_IS_MIRRORED))
				file.setIs_mirrored(reader.nextBoolean());
			else if (name.equals(TAG_APPLICATION_DATA))
				file.setApplicationData(parseApplicationData(reader));
			else if (name.equals(TAG_BLOCKLIST_KEY))
				file.setBlocklist_key(reader.nextString());
			else if (name.equals(TAG_EXTENSION))
				file.setExtension(reader.nextString());
			else if (name.equals(TAG_BLOCKLIST_ID))
				file.setBlocklist_id(reader.nextString());
			else if (name.equals(TAG_SIZE))
				file.setSize(reader.nextLong());
			else if (name.equals(TAG_MIME))
				file.setMime(reader.nextString());
			else
				reader.skipValue();
		}
		reader.endObject();
		
		if (requestAbsoluteParentPathId != null)
			file.setAbsoluteParentPathId(requestAbsoluteParentPathId + file.getId());
		else
			file.setAbsoluteParentPathId(File.separator + file.getId());
		return file;
	}
	
	private ApplicationData parseApplicationData(JsonReader reader) throws IOException {
		ApplicationData data = new ApplicationData();
		reader.beginObject();
		while(reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals(TAG_SERVER)) {
				reader.beginObject();
				while(reader.hasNext()) {
					String server = reader.nextName();
					if (server.equals(TAG_RELATIVE_ID_PATH)) {
						data.setRelative_id_path(reader.nextString());
					}
					else if (server.equals(TAG_NEBULA)) {
						reader.beginObject();
						while(reader.hasNext()) {
							String nebula = reader.nextName();
							if (nebula.equals(TAG_NONCE)) {
								data.setNonce(reader.nextString());
							}
							else if (nebula.equals(TAG_PAYLOAD)) {
								data.setPayload(reader.nextString());
							}
							else if (nebula.equals(TAG_DIGEST)) {
								data.setDigest(reader.nextString());
							}
							else
								reader.skipValue();
						}
						reader.endObject();
					}
					else if (server.equals(TAG_ALBUM_ART)) {
						data.setAlbum_art(reader.nextString());
					}
					else
						reader.skipValue();
				}
				reader.endObject();
			}
			else if (name.equals(TAG_BITCASA_ORIGINAL_PATH)) {
				data.setOriginal_path(reader.nextString());
			}
			else
				reader.skipValue();
		}
		reader.endObject();
		return data;
	}
	
	private void parseAccountInfo(JsonReader reader) throws IOException {
		if (profile == null)
			profile = new Profile();
		
		reader.beginObject();		
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals(TAG_USERNAME)) {
				profile.getUserData().setUsername(reader.nextString());
			}
			else if (name.equals(TAG_CREATED_AT)) {
				profile.getUserData().setCreated_time(reader.nextLong());
			}			
			else if (name.equals(TAG_FIRST_NAME)) {
				profile.getUserData().setFirst_name(reader.nextString());
			}
			else if (name.equals(TAG_LAST_NAME)) {
				profile.getUserData().setLast_name(reader.nextString());
			}
			else if (name.equals(TAG_ACCOUNT_ID)) {
				profile.getAccountData().setAccount_id(reader.nextString());
			}
			else if (name.equals(TAG_LOCALE)) {
				profile.getAccountData().setAccountLocale(reader.nextString());
			}
			else if (name.equals(TAG_ACCOUNT_STATE)) {
				getAccountState(reader);
			}
			else if (name.equals(TAG_STORAGE)) {
				getAccountStorage(reader);
			}
			else if (name.equals(TAG_ACCOUNT_PLAN)) {
				getAccountPlan(reader);
			}
			else if (name.equals(TAG_EMAIL)) {
				profile.getUserData().setEmail(reader.nextString());
			}
			else if (name.equals(TAG_SESSION)) {
				getAccountSession(reader);
			}	
			else if (name.equals(TAG_LAST_LOGIN)) {
				profile.getAccountData().setLast_login(reader.nextLong());
			}
			else if (name.equals(TAG_ID)) {
				profile.getAccountData().setId(reader.nextString());
			}
			else
				reader.skipValue();
				
		}
		
		reader.endObject();
	}
	
	private void getAccountState(JsonReader reader) throws IOException {
		if (profile == null)
			profile = new Profile();
		reader.beginObject();
		while (reader.hasNext()) {
			String storageName = reader.nextName();
			if (storageName.equals(TAG_DISPLAY_NAME))
				profile.getAccountData().setState_display_name(reader.nextString());
			else if (storageName.equals(TAG_ID))
				profile.getAccountData().setState_id(reader.nextString());
			else
				reader.skipValue();
		}
		reader.endObject();
	}
	
	private void getAccountStorage(JsonReader reader) throws IOException {
		if (profile == null)
			profile = new Profile();
		reader.beginObject();
		while (reader.hasNext()) {
			String storageName = reader.nextName();
			if (storageName.equals(TAG_STORAGE_LIMIT))
				profile.getAccountData().setQuota(reader.nextLong());
			else if (storageName.equals(TAG_STORAGE_USAGE))
				profile.getAccountData().setUsage(reader.nextLong());
			else if (storageName.equals(TAG_STORAGE_OTL))
				profile.getAccountData().setStorage_otl(reader.nextBoolean());
			else
				reader.skipValue();
		}
		reader.endObject();
	}
	
	private void getAccountPlan(JsonReader reader) throws IOException {
		if (profile == null)
			profile = new Profile();
		reader.beginObject();
		while (reader.hasNext()) {
			String storageName = reader.nextName();
			if (storageName.equals(TAG_CTA_TEXT) && reader.peek() != JsonToken.NULL)
				profile.getAccountData().setPlan_cta_text(reader.nextString());
			else if (storageName.equals(TAG_DISPLAY_NAME))
				profile.getAccountData().setPlan(reader.nextString());
			else if (storageName.equals(TAG_CTA_VALUE) && reader.peek() != JsonToken.NULL)
				profile.getAccountData().setPlan_cta_value(reader.nextString());
			else if (storageName.equals(TAG_ID))
				profile.getAccountData().setPlan_id(reader.nextString());
			else
				reader.skipValue();
		}
		reader.endObject();
	}
	
	private void getAccountSession(JsonReader reader) throws IOException {
		if (profile == null)
			profile = new Profile();
		reader.beginObject();
		while (reader.hasNext()) {
			String storageName = reader.nextName();
			if (storageName.equals(TAG_LOCALE))
				profile.getAccountData().setSessionLocale(reader.nextString());
			else
				reader.skipValue();
		}
		reader.endObject();
	}
	
	private Container[] parseItemsArray(JsonReader reader) throws IOException {
		reader.beginArray();
		
		ArrayList<Item> files = new ArrayList<Item>();
		while (reader.hasNext()) {
			Item f = parseMeta(reader);
			if (f != null)
				files.add(f);
		}
		reader.endArray();
		
		Container[] result = files.toArray(new Container[files.size()]);
		return result;
	}
	
	private ShareItem[] parseListShare(JsonReader reader) throws IOException {
		ArrayList<ShareItem> listShares= null;
		reader.beginArray();
		listShares = new ArrayList<ShareItem>();
		while (reader.hasNext()) {
			ShareItem share = parseShare(reader);
			listShares.add(share);
		}
		reader.endArray();		
		ShareItem[] result = listShares.toArray(new ShareItem[listShares.size()]);
		return result;
	}
	
	private ShareItem parseShare(JsonReader reader) throws IOException {
		ShareItem share = new ShareItem();
		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals(TAG_SHARE_KEY))
				share.setShare_key(reader.nextString());
			else if (name.equals(TAG_URL))
				share.setUrl(reader.nextString());
			else if (name.equals(TAG_SHORT_URL))
				share.setShort_url(reader.nextString());
			else if (name.equals(TAG_SIZE) && reader.peek() != JsonToken.NULL)
				share.setSize(reader.nextLong());
			else if (name.equals(TAG_DATE_CREATED) && reader.peek() != JsonToken.NULL)
				share.setDate_created(reader.nextLong());
			else
				reader.skipValue();
		}
		reader.endObject();
		return share;
	}
	
	private void parseBrowseShare(JsonReader reader) throws IOException {
		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals(TAG_SHARE))
				share = parseShare(reader);
			else if (name.equals(TAG_META))
				meta = parseMeta(reader);
			else if (name.equals(TAG_ITEMS))
				files = parseItemsArray(reader);
			else
				reader.skipValue();
		}
		reader.endObject();
	}
	
	private ActionHistory parseListHistory(JsonReader reader) throws IOException {
		ActionHistory ah = new ActionHistory();
		
		reader.beginArray();
		while (reader.hasNext()) {
			BaseAction action = new BaseAction();
			reader.beginObject();
			while(reader.hasNext()) {
				String name = reader.nextName();
				if (name.equals(TAG_VERSION))
					action.setVersion(reader.nextInt());
				else if (name.equals(TAG_ACTION))
					action.setAction(HistoryActions.getResult(reader.nextString()));
				else if (name.equals(TAG_DATA)) {
					reader.beginObject();
					while(reader.hasNext()){
						String data = reader.nextName();
						if (data.equals(TAG_SHARE_KEY))
							action.data_share_key = reader.nextString();
						else if (data.equals(TAG_EXISTS))
							action.data_exists = reader.nextString();
						else if (data.equals(TAG_PATH))
							action.data_path = reader.nextString();
						else if (data.equals(TAG_PATHS))
							action.data_paths = getStringArray(reader);
						else if (data.equals(TAG_SHARE_URL))
							action.data_share_url = reader.nextString();
						else if (data.equals(TAG_ID)) {
							reader.beginObject();
							while(reader.hasNext()) {
								String id = reader.nextName();
								if (id.equals(TAG_FROM))
									action.data_id_from = reader.nextString();
								else if (id.equals(TAG_TO))
									action.data_id_to = reader.nextString();
								else
									reader.skipValue();
							}
							reader.endObject();
						}
						else if (data.equals(TAG_NAME)) {
							if (reader.peek() == JsonToken.BEGIN_OBJECT) {
								reader.beginObject();
								while (reader.hasNext()) {
									String dataName = reader.nextName();
									if (dataName.equals(TAG_FROM))
										action.data_name_from = reader.nextString();
									else if (dataName.equals(TAG_TO))
										action.data_name_to = reader.nextString();
									else
										reader.skipValue();
								}
								reader.endObject();
							}
							else
								action.data_name = reader.nextString();
						}
						else if (data.equals(TAG_DATE_CREATED)) {
							if (reader.peek() == JsonToken.BEGIN_OBJECT) {
								reader.beginObject();
								while (reader.hasNext()) {
									String dateCreated = reader.nextName();
									if (dateCreated.equals(TAG_FROM))
										action.data_date_created_from = reader.nextLong();
									else if (dateCreated.equals(TAG_TO))
										action.data_date_created_to = reader.nextLong();
									else
										reader.skipValue();
								}
								reader.endObject();
							}
							else
								action.data_date_created = reader.nextLong();							
						}
						else if (data.equals(TAG_TO))
							action.data_to = reader.nextString();
						else if (data.equals(TAG_PARENT_ID))
							action.data_parent_id = reader.nextString();
						else if (data.equals(TAG_EXTENSION))
							action.data_extension = reader.nextString();
						else if (data.equals(TAG_DATE_META_LAST_MODIFIED))
							action.data_date_meta_last_modified = reader.nextLong();
						else if (data.equals(TAG_DATE_CONTENT_LAST_MODIFIED))
							action.data_date_content_last_modified = reader.nextLong();
						else if (data.equals(TAG_SIZE))
							action.data_size = reader.nextLong();
						else if (data.equals(TAG_MIME))
							action.data_mime = reader.nextString();
						else if (data.equals(TAG_IS_MIRRORED))
							action.data_is_mirrored = reader.nextBoolean();
						else if (name.equals(TAG_APPLICATION_DATA))
							action.applicationData = parseApplicationData(reader);
						else
							reader.skipValue();
					}
					reader.endObject();
				}
				else if (name.equals(TAG_PATH))
					action.path = reader.nextString();
				else if (name.equals(TAG_TYPE))
					action.type = reader.nextString();
				else
					reader.skipValue();
			}
			reader.endObject();
			
			ah.addAction(action);
		}
		reader.endArray();
		
		return ah;
	}
	
	private String[] getStringArray(JsonReader reader) throws IOException {
		List<String> result = new ArrayList<String>();
		reader.beginArray();
		while (reader.hasNext()) {
			result.add(reader.nextString());
		}
		reader.endArray();
		
		return result.toArray(new String[result.size()]);
	}
	
	private JsonObject getObjectFromReader(JsonReader reader) throws IOException {
		JsonObject jo = new JsonObject();
		reader.beginObject();
		while (reader.hasNext()){
			//reader.skipValue();
		}
		reader.endObject();
		return jo;
	}
}