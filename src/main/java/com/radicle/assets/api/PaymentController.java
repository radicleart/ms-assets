package com.radicle.assets.api;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.radicle.assets.service.AssetService;
import com.radicle.assets.service.PaymentService;
import com.radicle.assets.service.domain.Asset;
import com.radicle.assets.service.domain.AssetLifecycleEnum;

@RestController
@CrossOrigin(origins = { "http://localhost:8080", "http://localhost:8081", "https://loopbomb.com", "https://test.loopbomb.com", "https://radicle.art", "https://tart.radiclesociety.org",
		"https://tdbid.radiclesociety.org", "https://dbid.io", "https://brightblock.org" }, maxAge = 6000)
public class PaymentController {

	private static final Logger logger = LogManager.getLogger(PaymentController.class);
	@Autowired private AssetService assetService;
	@Autowired private PaymentService bitcoinService;
	public static final String BTC_METHOD = "bitcoin";
	public static final String LND_METHOD = "lightning";
	public static final String FREE_METHOD = "promotion";

	@MessageMapping("/mynews")
	@SendTo("/topic/news")
	public String processMessageFromClient(@Payload String message, Principal principal) throws Exception {
		return principal.getName();
	}

	@RequestMapping(value = "/payment/save", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Asset savePayment(HttpServletRequest request, @RequestBody Asset payment) throws JsonProcessingException, NoSuchAlgorithmException {
		if (payment.getBitcoinAddress() == null) {
			try {
				String bitcoinAddress = bitcoinService.getPaymentAddress(payment.getPaymentId());
				payment.setBitcoinAddress(bitcoinAddress);
			} catch (Exception e) {
				payment.setBitcoinAddress(null);
				logger.error("ERROR: bitcoin service unavailable." + e.getMessage());
			}
		}
		try {
			payment = bitcoinService.getInvoice(payment.getUserInvoiceModel().getAmount(), payment.getUserInvoiceModel().getMemo());
		} catch (Exception e) {
			logger.error("ERROR: lightning service saving payment: " + e.getMessage());
		}
		payment = assetService.save(payment);
		return payment;
	}

	@RequestMapping(value = "/payment/addresses", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Asset addresses(HttpServletRequest request) throws JsonProcessingException, NoSuchAlgorithmException {
		Asset payment = new Asset();
		try {
			String bitcoinAddress = bitcoinService.getPaymentAddress(payment.getPaymentId());
			payment.setBitcoinAddress(bitcoinAddress);
		} catch (Exception e) {
			logger.error("ERROR: bitcoin service unavbailable." + e.getMessage());
		}
		try {
			payment = bitcoinService.getInvoice(payment.getUserInvoiceModel().getAmount(), payment.getUserInvoiceModel().getMemo());
		} catch (Exception e) {
			logger.error("ERROR: lightning service payment address: " + e.getMessage());
		}
		return payment;
	}

	@RequestMapping(value = "/payment/update/assets", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Asset updateAssets(HttpServletRequest request, @RequestBody Asset payment1) throws NoSuchAlgorithmException {
		Asset payment = assetService.findByAssetId(payment1.getPaymentId());
		if (payment == null) {
			throw new RuntimeException("Payment not found");
		}
		payment.getClientData().addAssets(payment1.getClientData().getAssets());
		payment = assetService.save(payment);
		return payment;
	}

	@RequestMapping(value = "/payment/update/amount", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Asset updateAmount(HttpServletRequest request, @RequestBody Asset payment1) throws NoSuchAlgorithmException {
		Asset payment = assetService.findByAssetId(payment1.getPaymentId());
		if (payment == null) {
			throw new RuntimeException("Asset not found");
		}
		payment.setBitcoinAmount(payment1.getBitcoinAmount());
		try {
			payment = bitcoinService.getInvoice(payment.getUserInvoiceModel().getAmount(), payment.getUserInvoiceModel().getMemo());
		} catch (Exception e) {
			payment.setPaymentRequest("Error: " + e.getMessage().substring(9));
			payment.setPaymentHash("Error: " + e.getMessage().substring(9));
			logger.error("ERROR: lightning service update amount" + e.getMessage());
		}
		payment = assetService.save(payment);
		return payment;
	}

	@RequestMapping(value = "/payment/load/{paymentId}/{bitcoinAmount}", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Asset loadAsset(HttpServletRequest request, @PathVariable String paymentId, @PathVariable Float bitcoinAmount) throws NoSuchAlgorithmException {
		Asset payment = assetService.findByAssetId(paymentId);
		payment = addAddressesIfNull(payment, bitcoinAmount);
		return payment;
	}

	@RequestMapping(value = "/payment/confirm/{paymentId}", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Asset confirmAsset(HttpServletRequest request, @PathVariable String paymentId) throws NoSuchAlgorithmException {
		Asset payment = assetService.findByAssetId(paymentId);
		payment.setStatus(AssetLifecycleEnum.BUYER_TX_CONFIRMED.getStatus());
		assetService.save(payment);
		return payment;
	}

	@RequestMapping(value = "/payment/start-free-session/{paymentId}", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Asset freeOffer(HttpServletRequest request, @PathVariable String paymentId) throws NoSuchAlgorithmException {
		Asset payment = assetService.findByAssetId(paymentId);
		if (payment == null) {
			throw new RuntimeException("Asset not found");
		}
		payment.setMethod(FREE_METHOD);
		payment.setStatus(AssetLifecycleEnum.FREE_CREDITS.getStatus());
		payment = assetService.save(payment);
		return payment;
	}

	private Asset addAddressesIfNull(Asset payment, Float bitcoinAmount) {
		try {
			if (payment.getBitcoinAddress() == null) {
				String bitcoinAddress = bitcoinService.getPaymentAddress(payment.getPaymentId());
				payment.setBitcoinAddress(bitcoinAddress);
				assetService.save(payment);
			}
		} catch (Exception e) {
			logger.error("ERROR: bitcoin service unavbailable." + e.getMessage());
		}
		try {
			if (payment.getPaymentRequest() == null || payment.isExpired()) {
				if (payment.getBitcoinAmount() == null) {
					payment.setBitcoinAmount(bitcoinAmount);
				}
				payment = bitcoinService.getInvoice(payment.getUserInvoiceModel().getAmount(), payment.getUserInvoiceModel().getMemo());
				assetService.save(payment);
			}
		} catch (Exception e) {
			payment.setPaymentRequest("Error: " + e.getMessage().substring(9));
			payment.setPaymentHash("Error: " + e.getMessage().substring(9));
			logger.error("ERROR: lightning service loading payment: " + e.getMessage());
		}
		return payment;
	}
	
}
