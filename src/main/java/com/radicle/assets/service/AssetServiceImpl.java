package com.radicle.assets.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.radicle.assets.service.domain.Asset;

@Service
public class AssetServiceImpl implements AssetService {

	@Autowired private AssetRepository assetRepository;
	@Autowired private MongoTemplate mongoTemplate;

	@Override
	public Asset save(Asset assetFromJson) {
		Optional<Asset> assetFromDb = assetRepository.findByAssetHash(assetFromJson.getAssetHash());
		Asset asset;
		if (assetFromDb.isPresent()) {
			asset = assetFromDb.get();
			asset.setTxId(assetFromJson.getTxId());
			asset.setTokenId(assetFromJson.getTokenId());
		} else {
			asset = assetFromJson;
		}
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

	@Override
	public Optional<Asset> findByTokenId(Long tokenId) {
		Optional<Asset> asset = assetRepository.findByTokenId(tokenId);
		return asset;
	}

	/**
	 * Token is incremented whent he user calls the mint contract
	 */
	public Long getNextTokenId(String assetHash) {
		Query q = new Query().with(Sort.by(Sort.Direction.DESC, "tokenId")).limit(1);
		Asset a = mongoTemplate.findOne(q, Asset.class);
		Long tokenId = 0L;
		if (a != null) {
			if (a.getAssetHash().contentEquals(assetHash)) {
				return a.getTokenId();
			} else if (a.getTokenId() == null) {
				tokenId = 1L;
			} else {
				tokenId = a.getTokenId();
				tokenId++;
			}
		}
		return tokenId;
	}


}
