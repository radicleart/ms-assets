package com.bidlogix.assets.service;

import java.util.List;

import com.bidlogix.assets.service.domain.Asset;

public interface AssetService
{
	public Asset save(Asset asset);
	public Asset findByAssetHash(String assetHash);
	public List<Asset> findAssetsByBuyer(String buyerDid);
	public List<Asset> findAssetsBySeller(String sellerDid);
	public List<Asset> findAssetsByStatus(Integer status);
}
