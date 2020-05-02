package com.radicle.assets.service;

import com.radicle.assets.service.domain.Asset;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AssetRepository extends MongoRepository<Asset, String> {

    public List<Asset> findByStatus(Integer status);
    public Asset findByPaymentId(String paymentId);
    public Asset findByAssetHash(String assetHash);
}
