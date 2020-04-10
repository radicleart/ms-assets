package com.radicle.assets.service;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.radicle.assets.service.domain.PurchaseOrder;

@Repository
public interface PurchaseOrderRepository extends MongoRepository<PurchaseOrder, String> {

}
