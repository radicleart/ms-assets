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
@TypeAlias(value = "Asset")
public class Asset implements Serializable {

	private static final long serialVersionUID = -8594314205145970L;
	@Id private String uuid;
	private Long updated;
	private String memo;
	private String productId;
	private int status;
	private String purchaseDate;
	private String tuid;
	private Long amountSat;
	private Float amountBtc;

	public Asset() {
		super();
		this.uuid = UUID.randomUUID().toString();
	}
} 
