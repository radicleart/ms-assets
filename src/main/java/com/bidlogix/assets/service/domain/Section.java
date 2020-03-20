package com.bidlogix.assets.service.domain;

import java.io.Serializable;
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
@TypeAlias(value = "Section")
public class Section implements Serializable {

	private static final long serialVersionUID = 3208986242997040844L;
	@Id private String uuid;
	private Long updated;
	@Size(max = 200)
	private String title;
	@Size(max = 40000)
	private String description;
	@Size(max = 40000)
	private String summary;

	public Section() {
		super();
		this.uuid = UUID.randomUUID().toString();
	}
} 
