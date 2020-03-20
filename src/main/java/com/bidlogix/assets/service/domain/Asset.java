package com.bidlogix.assets.service.domain;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.Size;

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
	private Integer status;
	private String assetHash;
	@Size(max = 200)
	private String title;
	@Size(max = 40000)
	private String description;
	@Size(max = 40000)
	private String summary;
	@Size(max = 100)
	private String lotNumber;
	private List<Section> sections;

	public Asset() {
		super();
		this.uuid = UUID.randomUUID().toString();
	}
} 
