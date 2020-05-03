package com.radicle.assets.api;

import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.radicle.assets.service.PaymentService;
import com.radicle.assets.service.domain.Payment;
import com.radicle.assets.service.domain.PaymentLifecycleEnum;

@RestController
@CrossOrigin(origins = { "*" }, maxAge = 6000)
public class PaymentController {

	private static final Logger logger = LogManager.getLogger(PaymentController.class);
	@Autowired private PaymentService paymentService;
	public static final String BTC_METHOD = "bitcoin";
	public static final String LND_METHOD = "lightning";
	public static final String FREE_METHOD = "promotion";

	@RequestMapping(value = "/payment/save", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Payment savePayment(HttpServletRequest request, @RequestBody Payment payment) throws JsonProcessingException, NoSuchAlgorithmException {
		if (payment.getBitcoinAddress() == null) {
			try {
				String bitcoinAddress = paymentService.getPaymentAddress(payment.getPaymentId());
				payment.setBitcoinAddress(bitcoinAddress);
			} catch (Exception e) {
				payment.setBitcoinAddress(null);
				logger.error("ERROR: bitcoin service unavailable." + e.getMessage());
			}
		}
		try {
			payment = paymentService.getInvoice(payment);
		} catch (Exception e) {
			logger.error("ERROR: lightning service saving payment: " + e.getMessage());
		}
		payment = paymentService.save(payment);
		return payment;
	}

	@RequestMapping(value = "/payment/addresses", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Payment addresses(HttpServletRequest request) throws JsonProcessingException, NoSuchAlgorithmException {
		Payment payment = new Payment();
		try {
			String bitcoinAddress = paymentService.getPaymentAddress(payment.getPaymentId());
			payment.setBitcoinAddress(bitcoinAddress);
		} catch (Exception e) {
			logger.error("ERROR: bitcoin service unavbailable." + e.getMessage());
		}
		try {
			payment = paymentService.getInvoice(payment);
		} catch (Exception e) {
			logger.error("ERROR: lightning service payment address: " + e.getMessage());
		}
		return payment;
	}

	@RequestMapping(value = "/payment/update/assets", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Payment updateAssets(HttpServletRequest request, @RequestBody Payment payment1) throws NoSuchAlgorithmException {
		Payment payment = paymentService.findByPaymentId(payment1.getPaymentId());
		if (payment == null) {
			throw new RuntimeException("Payment not found");
		}
		payment.getClientData().addAssets(payment1.getClientData().getAssets());
		payment = paymentService.save(payment);
		return payment;
	}

	@RequestMapping(value = "/payment/update/amount", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Payment updateAmount(HttpServletRequest request, @RequestBody Payment payment1) throws NoSuchAlgorithmException {
		Payment payment = paymentService.findByPaymentId(payment1.getPaymentId());
		if (payment == null) {
			throw new RuntimeException("Asset not found");
		}
		payment.setBitcoinAmount(payment1.getBitcoinAmount());
		try {
			payment = paymentService.getInvoice(payment);
		} catch (Exception e) {
			logger.error("ERROR: getting invoice: " + e.getMessage(), payment);
			if (payment != null) {
				payment.setPaymentRequest("Error: " + e.getMessage().substring(9));
				payment.setPaymentHash("Error: " + e.getMessage().substring(9));
			} else {
				throw e;
			}
		}
		payment = paymentService.save(payment);
		return payment;
	}

	@RequestMapping(value = "/payment/load/{paymentId}/{bitcoinAmount}", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Payment loadAsset(HttpServletRequest request, @PathVariable String paymentId, @PathVariable Float bitcoinAmount) throws NoSuchAlgorithmException {
		Payment payment = paymentService.findByPaymentId(paymentId);
		if (payment == null) {
			return null;
		}
		payment = addAddressesIfNull(payment, bitcoinAmount);
		return payment;
	}

	@RequestMapping(value = "/payment/confirm/{paymentId}", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Payment confirmAsset(HttpServletRequest request, @PathVariable String paymentId) throws NoSuchAlgorithmException {
		Payment payment = paymentService.findByPaymentId(paymentId);
		payment.setStatus(PaymentLifecycleEnum.BUYER_TX_CONFIRMED.getStatus());
		paymentService.save(payment);
		return payment;
	}

	@RequestMapping(value = "/payment/start-free-session/{paymentId}", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Payment freeOffer(HttpServletRequest request, @PathVariable String paymentId) throws NoSuchAlgorithmException {
		Payment payment = paymentService.findByPaymentId(paymentId);
		if (payment == null) {
			throw new RuntimeException("Asset not found");
		}
		payment.setMethod(FREE_METHOD);
		payment.setStatus(PaymentLifecycleEnum.FREE_CREDITS.getStatus());
		payment = paymentService.save(payment);
		return payment;
	}

	private Payment addAddressesIfNull(Payment payment, Float bitcoinAmount) {
		try {
			if (payment.getBitcoinAddress() == null) {
				String bitcoinAddress = paymentService.getPaymentAddress(payment.getPaymentId());
				payment.setBitcoinAddress(bitcoinAddress);
				paymentService.save(payment);
			}
		} catch (Exception e) {
			logger.error("ERROR: bitcoin service unavbailable." + e.getMessage());
		}
		try {
			if (payment.getPaymentRequest() == null || payment.isExpired()) {
				if (payment.getBitcoinAmount() == null) {
					payment.setBitcoinAmount(bitcoinAmount);
				}
				payment = paymentService.getInvoice(payment);
				paymentService.save(payment);
			}
		} catch (Exception e) {
			payment = new Payment();
			payment.setPaymentRequest("Error: " + e.getMessage().substring(9));
			payment.setPaymentHash("Error: " + e.getMessage().substring(9));
			logger.error("ERROR: lightning service loading payment: " + e.getMessage());
		}
		return payment;
	}
	
}
