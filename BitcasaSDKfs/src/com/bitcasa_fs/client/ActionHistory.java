package com.bitcasa_fs.client;

import java.util.HashMap;

import com.bitcasa_fs.client.api.BitcasaRESTConstants.HistoryActions;
import com.bitcasa_fs.client.datamodel.BaseAction;

public class ActionHistory {
	
	public HashMap<String, BaseAction> actions;
	
	public ActionHistory() {
		actions = new HashMap<String, BaseAction>();
	}
	
	public String getKey(HistoryActions action, int version) {
		return action+"-"+version;
	}
	
	public void addAction(BaseAction action) {
		String key = getKey(action.getAction(), action.getVersion());
		actions.put(key, action);
	}
	
	public void removeAction(String key) {
		actions.remove(key);
	}
	
	public void removeAll() {
		actions.clear();
	}
	
	public int getSize() {
		return actions.size();
	}
}
