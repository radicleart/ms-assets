package com.radicle.assets.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.radicle.assets.service.domain.Asset;

@Service
public class AssetServiceImpl implements AssetService {

	@Autowired private AssetRepository assetRepository;

	@Override
	public List<Asset> findAssetsByStatus(Integer status) {
		List<Asset> assets = assetRepository.findByStatus(status);
		return assets;
	}

	@Override
	public Asset findByAssetId(String assetId) {
		if (assetId == null) {
			return null;
		}
		Asset asset = assetRepository.findByAssetId(assetId);
		return asset;
	}

	@Override
	public Asset findByAssetHash(String assetHash) {
		if (assetHash == null) {
			return null;
		}
		Asset asset = assetRepository.findByAssetHash(assetHash);
		return asset;
	}

	@Override
	public Asset save(Asset asset) {
		asset.setUpdated(new Date().getTime());
		return assetRepository.save(asset);
	}
}
