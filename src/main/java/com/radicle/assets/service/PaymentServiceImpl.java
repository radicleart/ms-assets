package com.radicle.assets.service;

import com.radicle.assets.service.domain.Asset;

public class PaymentServiceImpl implements PaymentService {

	@Override
	public String getPaymentAddress(String paymentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Asset getInvoice(Long amount, String memo) {
//		payment.setPaymentRequest(result.getPaymentRequest());
//		payment.setPaymentHash(result.getDecodedPayment().getPaymentHash());
//		payment.setInvoiceExpiry(result.getDecodedPayment().getExpiry());

		//LndAddInvoiceResponse result = bitcoinService.addInvoice("alice", payment.getUserInvoiceModel());

//		payment.setPaymentRequest(result.getPaymentRequest());
//		payment.setPaymentHash(result.getDecodedPayment().getPaymentHash());
//		Long expires = System.currentTimeMillis() + (result.getDecodedPayment().getExpiry() * 1000L);
//		payment.setInvoiceExpiry(expires);
		return null;
	}

}
