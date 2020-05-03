package com.radicle.assets.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

import com.radicle.assets.service.domain.Payment;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired private PaymentRepository paymentRepository;
	@Autowired private RestOperations restTemplate;
	private static final Logger logger = LogManager.getLogger(PaymentServiceImpl.class);
	@Value("${radicle.lsat.invoice-server}") String invoiceServer;
	@Value("${radicle.lsat.address-server}") String addressServer;

	@Override
	public List<Payment> findPaymentsByStatus(Integer status) {
		List<Payment> payments = paymentRepository.findByStatus(status);
		return payments;
	}

	@Override
	public Payment findByPaymentId(String paymentId) {
		if (paymentId == null) {
			return null;
		}
		Payment payment = paymentRepository.findByPaymentId(paymentId);
		return payment;
	}

	@Override
	public Payment save(Payment payment) {
		payment.setUpdated(new Date().getTime());
		return paymentRepository.save(payment);
	}

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
	public Payment getInvoice(Payment payment) {
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
