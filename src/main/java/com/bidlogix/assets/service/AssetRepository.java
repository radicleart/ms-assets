package com.bidlogix.assets.service;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bidlogix.assets.service.domain.Asset;

@Repository
public interface AssetRepository extends MongoRepository<Asset, String> {

    public List<Asset> findByAssetHash(String assetHash);
    public List<Asset> findByStatus(Integer status);
}
