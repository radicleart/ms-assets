package com.radicle.assets.api;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.radicle.assets.service.AssetService;
import com.radicle.assets.service.domain.Asset;

@RestController
@CrossOrigin(origins = { "*" }, maxAge = 6000)
public class AssetController {

	@Autowired private AssetService assetService;

	@GetMapping(value = "/api/server/time")
	public Long servertime() {
		return System.currentTimeMillis();
	}

	@GetMapping(value = "/api/loop/{assethash}")
	public Asset fetchLoop(HttpServletRequest request, @PathVariable String assethash) {
		Optional<Asset> dc = assetService.findByAssetHash(assethash);
		if (dc.isPresent()) {
			return dc.get();
		} else {
			Asset a = new Asset();
			a.setName("Aluminium - Bauxite ");
			a.setDescription(assethash + ": Silicate of aluminium contains at least one slicon oxide group.");
			a.setImage("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSVU2uNXGH4NJxFJJUu1zk5jQz_vLfGW6ysebpAiOjyErIYhick&usqp=CAU");
			return a;
		}
	}

	@PostMapping(value = "/asset")
	public Asset save(HttpServletRequest request, @RequestBody Asset asset) {
		asset = assetService.save(asset);
		return asset;
	}

	@PutMapping(value = "/asset")
	public Asset update(HttpServletRequest request, @RequestBody Asset asset) {
		asset = assetService.save(asset);
		return asset;
	}

	@GetMapping(value = "/assets/{owner}")
	public List<Asset> getByOwner(HttpServletRequest request, @PathVariable String owner) {
		List<Asset> assets = assetService.findByOwner(owner);
		return assets;
	}

	@GetMapping(value = "/asset/{assetHash}")
	public Optional<Asset> getByHash(HttpServletRequest request, @PathVariable String assetHash) {
		Optional<Asset> asset = assetService.findByAssetHash(assetHash);
		return asset;
	}
}
