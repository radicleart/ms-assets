package com.radicle.assets.api;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.radicle.assets.service.PaymentService;
import com.radicle.assets.service.domain.Payment;

@RestController
@CrossOrigin(origins = { "*" }, maxAge = 6000)
public class LsatController {

	private static final Logger logger = LogManager.getLogger(LsatController.class);
	@Autowired private PaymentService assetService;

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
	public Payment buyNow(HttpServletRequest request, @RequestBody Payment purchaseOrder) {
		purchaseOrder = assetService.save(purchaseOrder);
		return purchaseOrder;
	}
}
