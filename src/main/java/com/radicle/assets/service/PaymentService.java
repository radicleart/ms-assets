package com.radicle.assets.service;

import java.util.List;

import com.radicle.assets.service.domain.Payment;


public interface PaymentService
{
	public String getPaymentAddress(String paymentId);
	public Payment getInvoice(Payment payment);

	public Payment save(Payment payment);
	public List<Payment> findPaymentsByStatus(Integer status);
	public Payment findByPaymentId(String paymentId);
}
