package com.radicle.assets.proposals.service.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TypeAlias(value = "Proposal")
@Document
public class Proposal {

	@Id private String id;
	private long created;
	private long updated;
	private boolean onChain;
	private String status;
	private String title;
	private String description;
	private String chain;
	private String contractId;
	private String proposalUrl;
	private String githubPullRequest;
	private String githubIssue;
	private String proposer;
	private String seconder;

}
