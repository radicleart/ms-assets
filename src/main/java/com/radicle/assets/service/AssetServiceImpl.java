package com.radicle.assets.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.radicle.assets.service.domain.Asset;

@Service
public class AssetServiceImpl implements AssetService {

	@Autowired private AssetRepository assetRepository;

	@Override
	public Asset save(Asset asset) {
		asset.setUpdated(new Date().getTime());
		return assetRepository.save(asset);
	}

	@Override
	public List<Asset> findByOwner(String owner) {
		List<Asset> assets = assetRepository.findByOwner(owner);
		return assets;
	}

	@Override
	public Optional<Asset> findByAssetHash(String assethash) {
		Optional<Asset> asset = assetRepository.findByAssetHash(assethash);
		return asset;
	}


}
