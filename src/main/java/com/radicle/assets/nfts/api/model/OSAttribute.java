package com.radicle.assets.nfts.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class OSAttribute {

	private String display_type;
	private String trait_type;
	private String value;

	public OSAttribute() {
		super();
	}

	public OSAttribute(String display_type, String trait_type, String value) {
		super();
		this.display_type = display_type;
		this.trait_type = trait_type;
		this.value = value;
	}
}
