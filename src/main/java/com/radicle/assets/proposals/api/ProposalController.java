package com.radicle.assets.proposals.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.radicle.assets.proposals.service.ProposalRepository;
import com.radicle.assets.proposals.service.domain.Proposal;

@RestController
public class ProposalController {

	@Autowired private ProposalRepository proposalRepository;

	@PostMapping(value = "/v2/proposals")
	public Proposal save(@RequestBody Proposal proposal) {
		if (proposal.getId() == null) {
			proposal.setStatus("draft");
			return proposalRepository.insert(proposal);
		}
		proposal.setStatus("draft");
		return proposalRepository.save(proposal);
	}
	
	@DeleteMapping(value = "/v2/proposals/{proposalId}")
	public boolean deletePro(@PathVariable String proposalId) {
		Optional<Proposal> op = proposalRepository.findById(proposalId);
		if (op.isEmpty()) return false;
		if (op.get().getStatus().equals("draft")) {
			proposalRepository.deleteById(op.get().getId());
		} else {
			return false;
		}
		return true;
	}
	
	@GetMapping(value = "/v2/proposals")
	public List<Proposal> fetch() {
		return proposalRepository.findAll();
	}
	
	@PutMapping(value = "/v2/proposals/lock")
	public List<Proposal> lock(@RequestBody Proposal proposal) {
		return proposalRepository.findAll();
	}
}
