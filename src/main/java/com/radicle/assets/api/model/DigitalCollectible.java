package com.radicle.assets.api.model;

import java.util.List;

import com.radicle.assets.service.domain.Asset;

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
public class DigitalCollectible {

	private String name;
	private String description;
	private String image;
	private String background_color;
	private String animation_url;
	private String image_data;
	private String external_url;
	private String youtube_url;
	List<OSAttribute> attributes;

	public DigitalCollectible() {
		super();
	}
	public static DigitalCollectible fromAsset(Asset asset) {
		DigitalCollectible dc = new DigitalCollectible();
		dc.setAnimation_url(asset.getAnimationUrl());
		dc.setBackground_color(asset.getBackgroundColor());
		dc.setDescription("Loop #" + asset.getTokenId());
		dc.setExternal_url(asset.getExternalUrl());
		dc.setImage(asset.getImageUrl());
		dc.setImage_data(asset.getImageData());
		dc.setName(asset.getName());
		dc.setYoutube_url(asset.getYoutubeUrl());
		return dc;
	}
}
