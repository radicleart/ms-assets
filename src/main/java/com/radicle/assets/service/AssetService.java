package com.radicle.assets.service;

import java.util.List;

import com.radicle.assets.service.domain.Asset;


public interface AssetService
{
	public Asset save(Asset asset);
	public List<Asset> findAssetsByStatus(Integer status);
	public Asset findByAssetId(String assetId);
	public Asset findByAssetHash(String assetHash);
}
