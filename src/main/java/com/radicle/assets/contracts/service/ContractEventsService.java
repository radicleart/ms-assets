package com.radicle.assets.contracts.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface ContractEventsService
{
	public void consumeContractEvents(String contractId) throws JsonMappingException, JsonProcessingException;
	// public MintEventsBean getMintEvents(String contractId, String assetName, int offset, int limit, boolean unanchored, boolean tx_metadata);
	// Long countMintEventsByContractId(String contractId, String assetName);
}
