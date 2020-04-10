package com.radicle.assets.service;

import java.util.List;

import com.radicle.assets.service.domain.Asset;
import com.radicle.assets.service.domain.PurchaseOrder;

public interface AssetService
{
	public Asset save(Asset asset);
	public Asset findByAssetHash(String assetHash);
	public List<Asset> findAssetsByBuyer(String buyerDid);
	public List<Asset> findAssetsBySeller(String sellerDid);
	public List<Asset> findAssetsByStatus(Integer status);
	public PurchaseOrder save(PurchaseOrder purchaseOrder);
}
