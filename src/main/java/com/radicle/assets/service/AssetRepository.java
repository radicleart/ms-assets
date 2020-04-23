package com.radicle.assets.service;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.radicle.assets.service.domain.Asset;

@Repository
public interface AssetRepository extends MongoRepository<Asset, String> {

    public List<Asset> findByProductId(String productId);
    public List<Asset> findByStatus(Integer status);
}
