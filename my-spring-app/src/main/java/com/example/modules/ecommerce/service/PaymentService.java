package com.example.modules.ecommerce.service;

import com.example.core.service.BaseService;
import com.example.modules.ecommerce.model.Payment;
import com.example.modules.ecommerce.repository.PaymentRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class PaymentService extends BaseService<Payment> {

    protected final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository repository)
    {
        super(repository);
        this.paymentRepository = repository;
    }

    @Override
    public Payment save(Payment payment) {
        if (payment.getOrder() != null) {
        payment.getOrder().setPayment(payment);
        }

        return paymentRepository.save(payment);
    }


    public Payment update(Long id, Payment paymentRequest) {
        Payment existing = paymentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Payment not found"));

    // Copier les champs simples
        existing.setMethod(paymentRequest.getMethod());
        existing.setPaymentDate(paymentRequest.getPaymentDate());
        existing.setAmount(paymentRequest.getAmount());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        return paymentRepository.save(existing);
    }
}