package com.example.modules.ecommerce.service;

import com.example.core.service.BaseService;
import com.example.modules.ecommerce.model.Payment;
import com.example.modules.ecommerce.repository.PaymentRepository;


import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class PaymentService extends BaseService<Payment> {

protected final PaymentRepository paymentRepository;



public PaymentService(
PaymentRepository repository

) {
super(repository);
this.paymentRepository = repository;

}

@Override
public Payment save(Payment payment) {







return paymentRepository.save(payment);
}
}
