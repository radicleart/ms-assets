package com.radicle.assets.service;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.radicle.assets.service.domain.Asset;


@Repository
public interface AssetRepository extends MongoRepository<Asset, String> {

    public List<Asset> findByStatus(Integer status);
    public Asset findByAssetId(String assetId);
    public Asset findByAssetHash(String assetHash);
}
