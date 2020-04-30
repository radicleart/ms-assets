package com.radicle.assets.service.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClientData implements Serializable {
	private static final long serialVersionUID = -5834939072933725987L;
	private List<String> assets = new ArrayList<String>();

	public ClientData() {
		super();
	}

	public List<String> getAssets() {
		return assets;
	}

	public boolean addAssets(List<String> assetHashes) {
		return this.assets.addAll(assetHashes);
	}

	public void setAssets(List<String> assets) {
		this.assets = assets;
	}

}
