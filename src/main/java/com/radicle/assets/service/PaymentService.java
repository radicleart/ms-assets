package com.radicle.assets.service;

import com.radicle.assets.service.domain.Asset;

public interface PaymentService {

	String getPaymentAddress(String paymentId);
	Asset getInvoice(Long amount, String memo);

}
