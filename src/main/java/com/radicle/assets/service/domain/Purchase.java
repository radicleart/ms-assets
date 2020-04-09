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
@TypeAlias(value = "Purchase")
public class Purchase implements Serializable {

	private static final long serialVersionUID = 3208986242997040844L;
	@Id private String uuid;
	private Long purchaseDate;
	private Long amount;

	public Purchase() {
		super();
		this.uuid = UUID.randomUUID().toString();
	}
} 
