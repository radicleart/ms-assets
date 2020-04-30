package com.radicle.assets.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.radicle.assets.api.model.DigitalCollectible;

@RestController
@CrossOrigin(origins = { "*" }, maxAge = 6000)
public class DigitalCollectibleController {

	@GetMapping(value = "/api/loop/{tokenId}")
	public DigitalCollectible fetchLoop(HttpServletRequest request, @PathVariable String tokenId) {
		DigitalCollectible dc = new DigitalCollectible();
		dc.setName("Aluminium - Bauxite ");
		dc.setDescription(tokenId + ": Silicate of aluminium contains at least one slicon oxide group.");
		dc.setImage("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSVU2uNXGH4NJxFJJUu1zk5jQz_vLfGW6ysebpAiOjyErIYhick&usqp=CAU");
		return dc;
	}

}
