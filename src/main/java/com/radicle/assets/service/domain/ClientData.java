package com.radicle.assets.service.domain;

import lombok.*;
import org.springframework.data.annotation.TypeAlias;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@TypeAlias(value = "Asset")
public class ClientData implements Serializable {
	private static final long serialVersionUID = -5834939072933725987L;
	private List<String> assets;

	public ClientData() {
		super();
	}

	public boolean addAssets(List<String> assetHashes) {
		return this.assets.addAll(assetHashes);
	}
}
