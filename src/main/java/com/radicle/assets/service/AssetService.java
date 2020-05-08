package com.radicle.assets.service;

import java.util.List;
import java.util.Optional;

import com.radicle.assets.service.domain.Asset;


public interface AssetService
{
	public Asset save(Asset payment);
    public List<Asset> findByOwner(String owner);
    public Optional<Asset> findByAssetHash(String assethash);
	public Optional<Asset> findByTokenId(Long tokenId);
}
