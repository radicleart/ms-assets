package com.radicle.assets.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.radicle.assets.service.AssetService;
import com.radicle.assets.service.domain.PurchaseOrder;

@RestController
@CrossOrigin(origins = { "*" }, maxAge = 6000)
public class PurchaseController {

	@Autowired private AssetService assetService;

	@GetMapping(value = "/buy-now")
	public String initBuyNow(HttpServletRequest request) {
		return "okay";
	}

	@PostMapping(value = "/buy-now")
	public PurchaseOrder buyNow(HttpServletRequest request, @RequestBody PurchaseOrder purchaseOrder) {
		purchaseOrder = assetService.save(purchaseOrder);
		return purchaseOrder;
	}
}
