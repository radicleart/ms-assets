package com.radicle.assets.proposals.service;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.radicle.assets.proposals.service.domain.Proposal;


@Repository
public interface ProposalRepository extends MongoRepository<Proposal, String> {

    public List<Proposal> findByProposer(String proposer);
}
