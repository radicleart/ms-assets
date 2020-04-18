package com.radicle.assets.api;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.radicle.assets.service.AssetService;
import com.radicle.assets.service.domain.Asset;
import com.radicle.assets.service.domain.AssetLifecycleEnum;

@RestController
@CrossOrigin(origins = { "*" }, maxAge = 6000)
public class AssetController {

	private static final Logger logger = LogManager.getLogger(AssetController.class);
	@Autowired private AssetService assetService;

	@MessageMapping("/mynews")
	@SendTo("/topic/news")
	public String processMessageFromClient(@Payload String message, Principal principal) {
		logger.info("Assets: /topic/news - principal: ", principal);
		return principal.getName();
	}

	@GetMapping(value = "/buy-now")
	public String initBuyNow(HttpServletRequest request) {
		return "okay";
	}

	@PostMapping(value = "/buy-now")
	public Asset buyNow(HttpServletRequest request, @RequestBody Asset purchaseOrder) {
		purchaseOrder = assetService.save(purchaseOrder);
		return purchaseOrder;
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

	@GetMapping(value = "/asset")
	public List<Asset> get(HttpServletRequest request, @RequestBody Asset asset) {
		List<Asset> assets = assetService.findAssetsByStatus(AssetLifecycleEnum.BUYER_TX_CONFIRMING.getStatus());
		return assets;
	}

	@GetMapping(value = "/asset/{assetHash}")
	public Asset get(HttpServletRequest request, @PathVariable String assetHash) {
		Asset asset = assetService.findByAssetHash(assetHash);
		return asset;
	}
}
