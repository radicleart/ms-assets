package com.radicle.assets.service;

import com.radicle.assets.service.domain.Asset;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired private RestOperations restTemplate;
	private static final Logger logger = LogManager.getLogger(PaymentServiceImpl.class);
	@Value("${radicle.lsat.invoice-server}") String invoiceServer;
	@Value("${radicle.lsat.address-server}") String addressServer;

	@Override
	public String getPaymentAddress(String paymentId) {
		HttpHeaders headers = new HttpHeaders();
	    HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		ResponseEntity<String> response = null;
		String url = addressServer + "/" + paymentId;
		response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
		return response.getBody();
	}

	@Override
	public Asset getInvoice(Asset payment) {
		HttpHeaders headers = new HttpHeaders();
	    HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		ResponseEntity<Map> response = null;
		String url = invoiceServer + "/" + payment.getUserInvoiceModel().getAmount() + "/" + payment.getUserInvoiceModel().getMemo();
		response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Map.class);
		Map<String, Object> resp = response.getBody();
		payment.setPaymentHash((String)resp.get("PaymentHash"));
		payment.setPaymentRequest((String)resp.get("PaymentRequest"));
		payment.setInvoiceExpiry((Long)resp.get("Expires"));
		return payment;
	}
	
	private HttpHeaders getHeaders(String auth) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", auth);
		return headers;
	}
}
