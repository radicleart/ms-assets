package com.radicle.assets.service;

import com.radicle.assets.service.domain.Asset;

import java.util.List;


public interface AssetService
{
	public Asset save(Asset asset);
	public List<Asset> findAssetsByStatus(Integer status);
	public Asset findByPaymentId(String paymentId);
	public Asset findByAssetHash(String assetHash);
}
