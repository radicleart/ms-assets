package com.radicle.assets.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class Joke {

	private String tagline;
	private String punchline;

	public Joke() {
		super();
	}

	public Joke(String tagline, String punchline) {
		super();
		this.tagline = tagline;
		this.punchline = punchline;
	}
}
