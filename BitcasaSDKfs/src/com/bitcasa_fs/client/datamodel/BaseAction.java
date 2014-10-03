package com.bitcasa_fs.client.datamodel;

import com.bitcasa_fs.client.api.BitcasaRESTConstants.HistoryActions;

public class BaseAction {
	public HistoryActions historyAction;
	public int historyVersion;
	
	//ShareReceive, copy, move
	public String data_exists;	
	//ShareReceive
	public String data_path;
	
	//ShareCreate
	public String data_share_key;
	public String[] data_paths;
	public String data_share_url;
	
	//DeviceUpdate
	public String data_id_from;
	public String data_id_to;
	
	//DeviceCreate, DeviceDelete
	public String data_id;	
	//DeviceCreate, copy, move, create
	public String data_name;		
	
	//alterMeta, Copy, Move, create, delete, trash
	public String path;
	public String type;
	//alterMeta
	public String data_name_from;
	public String data_name_to;
	public long data_date_created_from;
	public long data_date_created_to;
	
	//Copy, move
	public String data_to;
	
	//Create
	public String data_parent_id;
	public String data_extension;
	public long data_date_created;
	public long data_date_meta_last_modified;
	public long data_date_content_last_modified;
	public long data_size;
	public String data_mime;
	public boolean data_is_mirrored;
	
	//application data
	public ApplicationData applicationData;
	
	public BaseAction() {
		applicationData = new ApplicationData();
	}
	
	public void setAction(HistoryActions action) {
		historyAction = action;
	}
	public void setVersion(int version) {
		historyVersion = version;
	}
	
	public HistoryActions getAction() {
		return historyAction;
	}
	public int getVersion() {
		return historyVersion;
	}
	
	public class ActionShareReceive extends BaseAction {
		
		public ActionShareReceive(BaseAction action) {
			setAction(action.historyAction);
			setDataExists(action.data_exists);
			setDataPath(action.data_path);
			setVersion(action.historyVersion);
		}
		
		public String getDataExists() {
			return data_exists;
		}
		public void setDataExists(String data_exists) {
			this.data_exists = data_exists;
		}
		public String getDataPath() {
			return data_path;
		}
		public void setDataPath(String data_path) {
			this.data_path = data_path;
		}
		
	}
	
	public class ActionShareCreate extends BaseAction {
		
		public ActionShareCreate(BaseAction action) {
			setAction(action.historyAction);
			setVersion(action.historyVersion);
			setDataPaths(action.data_paths);
			setDataShareKey(action.data_share_key);
			setDataShareUrl(action.data_share_url);
		}
		
		public String getDataShareKey() {
			return data_share_key;
		}
		public void setDataShareKey(String data_share_key) {
			this.data_share_key = data_share_key;
		}
		public String[] getDataPaths() {
			return data_paths;
		}
		public void setDataPaths(String[] data_paths) {
			this.data_paths = data_paths;
		}
		public String getDataShareUrl() {
			return data_share_url;
		}
		public void setDataShareUrl(String data_url) {
			this.data_share_url = data_url;
		}
		
	}
	
	public class ActionDeviceUpdate extends BaseAction {
		
		public ActionDeviceUpdate(BaseAction action) {
			setAction(action.historyAction);
			setDataIdFrom(action.data_id_from);
			setDataIdTo(action.data_id_to);
			setVersion(action.historyVersion);
		}
		
		public String getDataIdFrom() {
			return data_id_from;
		}
		public void setDataIdFrom(String data_id_from) {
			this.data_id_from = data_id_from;
		}
		public String getDataIdTo() {
			return data_id_to;
		}
		public void setDataIdTo(String data_id_to) {
			this.data_id_to = data_id_to;
		}
		
	}
	
	public class ActionDeviceCreate extends BaseAction {
		public ActionDeviceCreate(BaseAction action){
			setAction(action.historyAction);
			setDataId(action.data_id);
			setDataName(action.data_name);
			setVersion(action.historyVersion);
		}
		
		public String getDataId() {
			return data_id;
		}
		public void setDataId(String data_id) {
			this.data_id = data_id;
		}
		public String getDataName() {
			return data_name;
		}
		public void setDataName(String data_name) {
			this.data_name = data_name;
		}
		
	}
	
	public class ActionDeviceDelete extends BaseAction {
		public ActionDeviceDelete(BaseAction action){
			setAction(action.historyAction);
			setDataId(action.data_id);
			setVersion(action.historyVersion);
		}
		
		public String getDataId() {
			return data_id;
		}
		public void setDataId(String data_id) {
			this.data_id = data_id;
		}
		
	}
	
	public class ActionAlterMeta extends BaseAction {
		public ActionAlterMeta(BaseAction action){
			setAction(action.historyAction);
			setDataCreatedTo(action.data_date_created_to);
			setDataNameFrom(action.data_name_from);
			setDataNameTo(action.data_name_to);
			setDateCreatedFrom(action.data_date_created_from);
			setPath(action.path);
			setType(action.type);
			setVersion(action.historyVersion);
		}
		
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getDataNameFrom() {
			return data_name_from;
		}
		public void setDataNameFrom(String data_name_from) {
			this.data_name_from = data_name_from;
		}
		public String getDataNameTo() {
			return data_name_to;
		}
		public void setDataNameTo(String data_name_to) {
			this.data_name_to = data_name_to;
		}
		public long getDateCreatedFrom() {
			return data_date_created_from;
		}
		public void setDateCreatedFrom(long data_date_created_from) {
			this.data_date_created_from = data_date_created_from;
		}
		public long getDataCreatedTo() {
			return data_date_created_to;
		}
		public void setDataCreatedTo(long data_date_created_to) {
			this.data_date_created_to = data_date_created_to;
		}
		
	}
	
	public class ActionCopy extends BaseAction {
		public ActionCopy(BaseAction action){
			setAction(action.historyAction);
			setDataExists(action.data_exists);
			setDataName(action.data_name);
			setDataTo(action.data_to);
			setPath(action.path);
			setType(action.type);
			setVersion(action.historyVersion);
		}
		
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getDataTo() {
			return data_to;
		}
		public void setDataTo(String data_to) {
			this.data_to = data_to;
		}
		public String getDataExists() {
			return data_exists;
		}
		public void setDataExists(String data_exists) {
			this.data_exists = data_exists;
		}
		public String getDataName() {
			return data_name;
		}
		public void setDataName(String data_name) {
			this.data_name = data_name;
		}
		
	}
	
	public class ActionMove extends BaseAction {
		public ActionMove(BaseAction action){
			setAction(action.historyAction);
			setDataExists(action.data_exists);
			setDataName(action.data_name);
			setDataTo(action.data_to);
			setPath(action.path);
			setType(action.type);
			setVersion(action.historyVersion);
		}
		
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getDataTo() {
			return data_to;
		}
		public void setDataTo(String data_to) {
			this.data_to = data_to;
		}
		public String getDataExists() {
			return data_exists;
		}
		public void setDataExists(String data_exists) {
			this.data_exists = data_exists;
		}
		public String getDataName() {
			return data_name;
		}
		public void setDataName(String data_name) {
			this.data_name = data_name;
		}
	}
	
	public class ActionCreate extends BaseAction {
		public ActionCreate(BaseAction action){
			setAction(action.historyAction);
			setApplicationData(action.applicationData);
			setDataDateContentLastModified(action.data_date_content_last_modified);
			setDataDateCreated(action.data_date_created);
			setDataDateMetaLastModified(action.data_date_meta_last_modified);
			setDataExtension(action.data_extension);
			setDataIsMirrored(action.data_is_mirrored);
			setDataMime(action.data_mime);
			setDataName(action.data_name);
			setDataParentId(action.data_parent_id);
			setDataSize(action.data_size);
			setPath(action.path);
			setType(action.type);
			setVersion(action.historyVersion);
		}
		
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getDataParentId() {
			return data_parent_id;
		}
		public void setDataParentId(String data_parent_id) {
			this.data_parent_id = data_parent_id;
		}
		public String getDataName() {
			return data_name;
		}
		public void setDataName(String data_name) {
			this.data_name = data_name;
		}
		public String getDataExtension() {
			return data_extension;
		}
		public void setDataExtension(String data_extension) {
			this.data_extension = data_extension;
		}
		public long getDataDateCreated() {
			return data_date_created;
		}
		public void setDataDateCreated(long data_date_created) {
			this.data_date_created = data_date_created;
		}
		public long getDataDateMetaLastModified() {
			return data_date_meta_last_modified;
		}
		public void setDataDateMetaLastModified(long data_date_meta_last_modified) {
			this.data_date_meta_last_modified = data_date_meta_last_modified;
		}
		public long getDataDateContentLastModified() {
			return data_date_content_last_modified;
		}
		public void setDataDateContentLastModified(
				long data_date_content_last_modified) {
			this.data_date_content_last_modified = data_date_content_last_modified;
		}
		public long getDataSize() {
			return data_size;
		}
		public void setDataSize(long data_size) {
			this.data_size = data_size;
		}
		public String getDataMime() {
			return data_mime;
		}
		public void setDataMime(String data_mime) {
			this.data_mime = data_mime;
		}
		public boolean getDataIsMirrored() {
			return data_is_mirrored;
		}
		public void setDataIsMirrored(boolean data_is_mirrored) {
			this.data_is_mirrored = data_is_mirrored;
		}
		public ApplicationData getApplicationData() {
			return applicationData;
		}
		public void setApplicationData(ApplicationData applicationData) {
			this.applicationData = applicationData;
		}
	}
	
	public class ActionDelete extends BaseAction {
		public ActionDelete(BaseAction action){
			setAction(action.historyAction);
			setPath(action.path);
			setType(action.type);
			setVersion(action.historyVersion);
		}
		
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		
	}
	
	public class ActionTrash extends BaseAction {
		public ActionTrash(BaseAction action) {
			setAction(action.historyAction);
			setPath(action.path);
			setType(action.type);
			setVersion(action.historyVersion);
		}
		
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
	}
}
