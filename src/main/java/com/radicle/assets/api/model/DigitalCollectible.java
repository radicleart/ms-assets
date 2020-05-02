package com.radicle.assets.api.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@TypeAlias(value = "DigitalCollectible")
public class DigitalCollectible implements Serializable {

	private static final long serialVersionUID = -3400559415907788678L;
	@Id private String uuid;
	private Long updated;
	private String name;
	private String description;
	private String image;
	private Map<String, String> attributes;

	public DigitalCollectible() {
		super();
		this.uuid = UUID.randomUUID().toString();
	}
}
