package com.radicle.assets.api.model;

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
@TypeAlias(value = "DigitalResource")
public class DigitalResource implements Serializable {

	private static final long serialVersionUID = -3400559415907788678L;
	@Id private String uuid;
	private Long updated;
	private String resourceId;

	public DigitalResource() {
		super();
		this.uuid = UUID.randomUUID().toString();
	}
}
