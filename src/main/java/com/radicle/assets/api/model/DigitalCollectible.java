package com.radicle.assets.api.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

	private static final String HTTPS_LOOPBOMB_COM_DISPLAY_ASSET = "https://loopbomb.com/display?asset=";
	private static final String HTTPS_TEST_LOOPBOMB_COM_DISPLAY_ASSET = "https://test.loopbomb.com/display?asset=";
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
		String encUrl;
		try {
			encUrl = URLEncoder.encode(asset.getExternalUrl(), StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			encUrl = asset.getExternalUrl();
		}
		if (asset.getExternalUrl() != null && asset.getExternalUrl().indexOf("loopbomb.com/display") > -1) {
			dc.setExternal_url(asset.getExternalUrl());
		} else {
			if (asset.getNetwork() == 1) {
				dc.setExternal_url(HTTPS_LOOPBOMB_COM_DISPLAY_ASSET + encUrl);
			} else {
				dc.setExternal_url(HTTPS_TEST_LOOPBOMB_COM_DISPLAY_ASSET + encUrl);
			}
		}
		dc.setImage(asset.getImageUrl());
		dc.setImage_data(asset.getImageData());
		dc.setName(asset.getName());
		dc.setYoutube_url(asset.getYoutubeUrl());
		return dc;
	}
}
