package com.radicle.assets.nfts.api;

import java.security.Principal;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.radicle.assets.nfts.api.model.Joke;
import com.radicle.assets.nfts.service.AssetService;
import com.radicle.assets.nfts.service.domain.Asset;

@RestController
@CrossOrigin(origins = { "*" }, maxAge = 6000)
public class BuyNowController {

	private static final Logger logger = LogManager.getLogger(BuyNowController.class);
	@Autowired private AssetService assetService;

	@MessageMapping("/mynews")
	@SendTo("/topic/news")
	public String processMessageFromClient(@Payload String message, Principal principal) {
		logger.info("Assets: /topic/news - principal: ", principal);
		return principal.getName();
	}

	@GetMapping(value = "/buy-now")
	public String initBuyNow(HttpServletRequest request) {
		// access granted - return valuable resource
		return "okay";
	}

	/**
	 * Called from fe-lsat to initiate payment 402 flow..
	 * @param request
	 * @param purchaseOrder - simulates a real purchase order
	 * @return
	 */
	@PostMapping(value = "/buy-now")
	public List<Joke> buyNow(HttpServletRequest request, @RequestBody Asset purchaseOrder) {
		logger.info("Jokes: must have received a valid LSAT");
		List<Joke> jokes = new ArrayList<Joke>();
		jokes.add(new Joke("What is black, white and red all over?", "A newspaper"));
		jokes.add(new Joke("What is brown and sticky", "A stick"));
		return jokes;
	}
}
