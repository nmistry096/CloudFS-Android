package com.bitcasa_fs.client.datamodel;

public class ApplicationData {
	private String original_path;
	private String relative_id_path;
	private String nonce;
	private String payload;
	private String digest;
	private String album_art;
	
	public String getOriginal_path() {
		return original_path;
	}

	public void setOriginal_path(String original_path) {
		this.original_path = original_path;
	}

	public String getRelative_id_path() {
		return relative_id_path;
	}

	public void setRelative_id_path(String relative_id_path) {
		this.relative_id_path = relative_id_path;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public String getAlbum_art() {
		return album_art;
	}

	public void setAlbum_art(String album_art) {
		this.album_art = album_art;
	}
	
}
