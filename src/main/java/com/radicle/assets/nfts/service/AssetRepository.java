package com.radicle.assets.nfts.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.radicle.assets.nfts.service.domain.Asset;


@Repository
public interface AssetRepository extends MongoRepository<Asset, String> {

    public List<Asset> findByOwner(String owner);
    public Optional<Asset> findByAssetHash(String assethash);
    public Optional<Asset> findByTokenIdAndNetwork(Long tokenId, Integer network);
    public Optional<Asset> findByTokenId(Long tokenId);
}
