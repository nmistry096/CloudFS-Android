package com.bitcasa_fs.client;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.bitcasa_fs.client.api.BitcasaClientApi;

public class Account {
	private String account_id;
	private String locale;
	
	private String state_display_name;
	private String state_id;
	
	private long storage_usage;
	private long storage_limit;
	private boolean storage_otl;
	
	private String plan_cta_text;
	private String plan_display_name;
	private String plan_cta_value;
	private String plan_id;
	
	private String session_locale;
	private long last_login;
	private String id;
	
	public Account(){
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();		
		sb.append("] \naccount_id[").append(account_id)
		.append("] \nlocale[").append(locale)		
		.append("] \nstate_display_name[").append(state_display_name)
		.append("] \nstate_id[").append(state_id)
		.append("] \nstorage_usage[").append(storage_usage)
		.append("] \nstorage_limit[").append(storage_limit)
		.append("] \nstorage_otl[").append(storage_otl)
		.append("] \nplan_cta_text[").append(plan_cta_text)
		.append("] \nplan_display_name[").append(plan_display_name)
		.append("] \nplan_cta_value[").append(plan_cta_value)
		.append("] \nplan_id[").append(plan_id)
		.append("] \nsession_locale[").append(session_locale)
		.append("] \nlast_login[").append(Long.toString(last_login))
		.append("] \nid[").append(id);
		sb.append("]*****");
		
		return sb.toString();
	}

	public long getUsage() {
		return storage_usage;
	}

	public long getQuota() {
		return storage_limit;
	}

	public String getPlan() {
		return plan_display_name;
	}

	public void setUsage(long used) {
		this.storage_usage = used;
	}

	public void setQuota(long total) {
		this.storage_limit = total;
	}

	public void setPlan(String plan) {
		this.plan_display_name = plan;
	}

	public String getAccount_id() {
		return account_id;
	}

	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}

	public String getLocale() {
		return locale;
	}

	public void setAccountLocale(String locale) {
		this.locale = locale;
	}

	public String getState_display_name() {
		return state_display_name;
	}

	public void setState_display_name(String state_display_name) {
		this.state_display_name = state_display_name;
	}

	public String getState_id() {
		return state_id;
	}

	public void setState_id(String state_id) {
		this.state_id = state_id;
	}

	public boolean isStorage_otl() {
		return storage_otl;
	}

	public void setStorage_otl(boolean storage_otl) {
		this.storage_otl = storage_otl;
	}

	public String getPlan_cta_text() {
		return plan_cta_text;
	}

	public void setPlan_cta_text(String plan_cta_text) {
		this.plan_cta_text = plan_cta_text;
	}

	public String getPlan_cta_value() {
		return plan_cta_value;
	}

	public void setPlan_cta_value(String plan_cta_value) {
		this.plan_cta_value = plan_cta_value;
	}

	public String getPlan_id() {
		return plan_id;
	}

	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}

	public String getSession_locale() {
		return session_locale;
	}

	public void setSessionLocale(String session_locale) {
		this.session_locale = session_locale;
	}

	public long getLast_login() {
		return last_login;
	}

	public void setLast_login(long last_login) {
		this.last_login = last_login;
	}
	
	/**
	 * This method requires a request to network
	 * @param api
	 * @param locale
	 * @return boolean value to indicate if the API call was successful or not
	 */
	public boolean changeSessionLocale(BitcasaClientApi api, String locale) {
		HashMap<String, String> itemsToChange = new HashMap<String, String>();
		itemsToChange.put("session_locale", locale);
		return api.getBitcasaAccountDataApi().alterProfile(this, itemsToChange);
	}
	
	/**
	 * This method requires a request to network
	 * @param api
	 * @param locale
	 * @return boolean value to indicate if the API call was successful or not
	 */
	public boolean changeAccountLocale(BitcasaClientApi api, String locale) {
		HashMap<String, String> itemsToChange = new HashMap<String, String>();
		itemsToChange.put("locale", locale);
		return api.getBitcasaAccountDataApi().alterProfile(this, itemsToChange);
	}
}
