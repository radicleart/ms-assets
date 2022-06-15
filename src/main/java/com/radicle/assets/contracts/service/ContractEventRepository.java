package com.radicle.assets.contracts.service;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.radicle.assets.contracts.service.domain.ContractEvent;


@Repository
public interface ContractEventRepository extends MongoRepository<ContractEvent, String> {

	@Query(value = "{ 'contractLog.contract_id' : ?#{[0]} }")
    List<ContractEvent> findByContractId(String contractId);
}
