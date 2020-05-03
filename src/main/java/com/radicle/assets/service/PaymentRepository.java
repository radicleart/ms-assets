package com.radicle.assets.service;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.radicle.assets.service.domain.Payment;


@Repository
public interface PaymentRepository extends MongoRepository<Payment, String> {

    public List<Payment> findByStatus(Integer status);
    public Payment findByPaymentId(String paymentId);
}
