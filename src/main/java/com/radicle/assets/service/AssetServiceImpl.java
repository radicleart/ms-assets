package com.radicle.assets.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.radicle.assets.service.domain.Asset;
import com.radicle.assets.service.domain.PurchaseOrder;

@Service
public class AssetServiceImpl implements AssetService {

	@Autowired private AssetRepository assetRepository;
	@Autowired private MongoTemplate mongoTemplate;

	@Override
	public Asset save(Asset asset) {
		asset.setUpdated(System.currentTimeMillis());
		return assetRepository.save(asset);
	}

	@Override
	public Asset findByAssetHash(String assetHash) {
		if (assetHash == null) {
			return null;
		}
		List<Asset> pms = assetRepository.findByAssetHash(assetHash);
		Asset asset = null;
		if (pms.size() > 0) {
			asset = pms.get(0);
			return asset;
		}
		return null;
	}

	@Override
	public List<Asset> findAssetsByBuyer(String buyerDid) {
		List<Asset> assets = new ArrayList<>();
		Criteria satusCrit = Criteria.where("status").gt(0);
		List<Asset> results = null;
		Criteria regex = Criteria.where("purchaseCycles.buyer.did").is(buyerDid);
		results = mongoTemplate.find(new Query().addCriteria(regex).addCriteria(satusCrit), Asset.class);
		return assets;
	}

	@Override
	public List<Asset> findAssetsBySeller(String sellerDid) {
		List<Asset> assets = new ArrayList<>();
		Criteria satusCrit = Criteria.where("status").gt(0);
		List<Asset> results = null;
		Criteria regex = Criteria.where("purchaseCycles.seller.did").is(sellerDid);
		results = mongoTemplate.find(new Query().addCriteria(regex).addCriteria(satusCrit), Asset.class);
		return assets;
	}

	@Override
	public List<Asset> findAssetsByStatus(Integer status) {
		List<Asset> assets = assetRepository.findByStatus(status);
		return assets;
	}

	@Override
	public PurchaseOrder save(PurchaseOrder purchaseOrder) {
		// TODO Auto-generated method stub
		return null;
	}
}
