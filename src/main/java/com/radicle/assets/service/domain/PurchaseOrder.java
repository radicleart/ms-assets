package com.radicle.assets.service.domain;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@TypeAlias(value = "PurchaseOrder")
public class PurchaseOrder implements Serializable {

	private static final long serialVersionUID = 3208986242997040844L;
	@Id private String uuid;
	private String assetHash;
	private String contentId;
	private String purchaseDate;
	private Float amount;
	private Float amountBtc;
	private String addressTo;
	private String addressFrom;

	public PurchaseOrder() {
		super();
		this.uuid = UUID.randomUUID().toString();
	}

} 
