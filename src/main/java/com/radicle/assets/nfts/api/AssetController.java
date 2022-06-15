package com.radicle.assets.nfts.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.radicle.assets.nfts.api.model.DigitalCollectible;
import com.radicle.assets.nfts.api.model.OSAttribute;
import com.radicle.assets.nfts.service.AssetService;
import com.radicle.assets.nfts.service.domain.Asset;

@RestController
@CrossOrigin(origins = { "*" }, maxAge = 6000)
public class AssetController {

    private static final Logger logger = LogManager.getLogger(AssetController.class);
	@Autowired private AssetService assetService;

	@GetMapping(value = "/api/v1/network")
	public void updateNetwork(HttpServletRequest request) {
		logger.info("ContractEvent /api/v1/network");
		assetService.updateNetwork();
	}

	@GetMapping(value = "/api/server/time")
	public Long servertime() {
		return System.currentTimeMillis();
	}

//	@GetMapping(value = "/api/loop/{tokenId}")
//	public DigitalCollectible fetchLoop(HttpServletRequest request, @PathVariable Long tokenId) {
//		logger.info("ContractEvent requested: " + tokenId);
//		Optional<ContractEvent> asset = assetService.findByTokenIdAndNetwork(tokenId, 4);
//		if (asset.isEmpty()) {
//			return null;
//		} else {
//			DigitalCollectible dc = DigitalCollectible.fromAsset(asset.get());
//			return dc;
//		}
//	}

	@GetMapping(value = "/api/v1/loop/{tokenId}")
	public DigitalCollectible fetchV1Loop(HttpServletRequest request, @PathVariable Long tokenId) {
		logger.info("ContractEvent requested: " + tokenId);
		Optional<Asset> asset = assetService.findByTokenId(tokenId);
		if (asset.isEmpty()) {
			return null;
		} else {
			DigitalCollectible dc = DigitalCollectible.fromAsset(asset.get());
			dc = addAttributes(dc, asset.get().getCreated());
			return dc;
		}
	}
	
	private DigitalCollectible addAttributes(DigitalCollectible dc, long created) {
		List<OSAttribute> attributes = new ArrayList<>();
		attributes.add(new OSAttribute(null, "Version", "One"));
		attributes.add(new OSAttribute("date", "captured", String.valueOf(created)));
		dc.setAttributes(attributes);
		return dc;
	}

	@GetMapping(value = "/api/v1/loop/{network}/{tokenId}")
	public DigitalCollectible fetchV1Loop(HttpServletRequest request, @PathVariable Integer network, @PathVariable Long tokenId) {
		logger.info("ContractEvent requested: " + tokenId);
		Optional<Asset> asset = assetService.findByTokenIdAndNetwork(tokenId, network);
		if (asset.isEmpty()) {
			return null;
		} else {
			DigitalCollectible dc = DigitalCollectible.fromAsset(asset.get());
			dc = addAttributes(dc, asset.get().getCreated());
			
			return dc;
		}
	}

	@GetMapping(value = "/api/v2/loop/{network}/{tokenId}")
	public DigitalCollectible fetchV2Loop(HttpServletRequest request, @PathVariable Integer network, @PathVariable Long tokenId) {
		logger.info("V2 ContractEvent requested: network=" + network + " tokenId=" + tokenId);
		Optional<Asset> asset = assetService.findByTokenIdAndNetwork(tokenId, network);
		if (asset.isEmpty()) {
			return null;
		} else {
			DigitalCollectible dc = DigitalCollectible.fromAsset(asset.get());
			dc = addAttributes(dc, asset.get().getCreated());
			
			return dc;
		}
	}

	private void setNetwork(HttpServletRequest request, Asset asset) {
		String origin = request.getHeader("origin");
		if (origin.contentEquals("https://loopbomb.com")) {
			asset.setNetwork(1);
		} else {
			asset.setNetwork(4);
		}
	}
	
	@PostMapping(value = "/asset")
	public Asset save(HttpServletRequest request, @RequestBody Asset asset) {
		setNetwork(request, asset);
		asset = assetService.save(asset);
		logger.info("New ContractEvent: " + asset.toString());
		return asset;
	}

	@PutMapping(value = "/asset")
	public Asset update(HttpServletRequest request, @RequestBody Asset asset) {
		setNetwork(request, asset);
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
