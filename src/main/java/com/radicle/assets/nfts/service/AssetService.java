package com.radicle.assets.nfts.service;

import java.util.List;
import java.util.Optional;

import com.radicle.assets.nfts.service.domain.Asset;


public interface AssetService
{
	public String getPaymentAddress(String paymentId);
	public Asset save(Asset payment);
    public List<Asset> findByOwner(String owner);
    public Optional<Asset> findByAssetHash(String assethash);
	public Optional<Asset> findByTokenIdAndNetwork(Long tokenId, Integer network);
	public Optional<Asset> findByTokenId(Long tokenId);
	public void updateNetwork();
}
