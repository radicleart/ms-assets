package com.radicle.assets.service.domain;

import java.util.Map;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;

import com.fasterxml.jackson.annotation.JsonAlias;

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
@TypeAlias(value = "ERC721Asset")
public class Asset {

	@Id private String uuid;
	private long created;
	private long updated;
	private String owner;
	private String assetHash;
	private String image;
	private String name;
	private String description;
	private Map<String, String> attributes;
	@JsonAlias({"image_data"}) private String imageData;
	@JsonAlias({"external_url"}) private String externalUrl;
	@JsonAlias({"background_color"}) private String backgroundColor;
	@JsonAlias({"animation_url"}) private String animationUrl;
	@JsonAlias({"youtube_url"}) private String youtubeUrl;

	public Asset() {
		super();
		this.uuid = UUID.randomUUID().toString();
	}

}
