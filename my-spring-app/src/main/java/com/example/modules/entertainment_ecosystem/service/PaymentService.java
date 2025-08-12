package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Payment;
import com.example.modules.entertainment_ecosystem.repository.PaymentRepository;

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
        if (payment.getBooking() != null) {
        payment.getBooking().setPayment(payment);
        }

        return paymentRepository.save(payment);
    }


    public Payment update(Long id, Payment paymentRequest) {
        Payment existing = paymentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Payment not found"));

    // Copier les champs simples
        existing.setAmount(paymentRequest.getAmount());
        existing.setPaymentDate(paymentRequest.getPaymentDate());
        existing.setMethod(paymentRequest.getMethod());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        return paymentRepository.save(existing);
    }
}