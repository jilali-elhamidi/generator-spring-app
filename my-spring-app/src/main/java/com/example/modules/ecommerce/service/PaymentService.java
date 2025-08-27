package com.example.modules.ecommerce.service;

import com.example.core.service.BaseService;
import com.example.modules.ecommerce.model.Payment;
import com.example.modules.ecommerce.repository.PaymentRepository;

import com.example.modules.ecommerce.model.Order;
import com.example.modules.ecommerce.repository.OrderRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class PaymentService extends BaseService<Payment> {

    protected final PaymentRepository paymentRepository;
    
    protected final OrderRepository orderRepository;
    

    public PaymentService(PaymentRepository repository, OrderRepository orderRepository)
    {
        super(repository);
        this.paymentRepository = repository;
        
        this.orderRepository = orderRepository;
        
    }

    @Transactional
    @Override
    public Payment save(Payment payment) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
        if (payment.getOrder() != null) {
            if (payment.getOrder().getId() != null) {
                Order existingOrder = orderRepository.findById(payment.getOrder().getId())
                    .orElseThrow(() -> new RuntimeException("Order not found with id "
                        + payment.getOrder().getId()));
                payment.setOrder(existingOrder);
            } else {
                // Nouvel objet → sauvegarde d'abord
                Order newOrder = orderRepository.save(payment.getOrder());
                payment.setOrder(newOrder);
            }

            payment.getOrder().setPayment(payment);
        }
        
    return paymentRepository.save(payment);
}

    @Transactional
    @Override
    public Payment update(Long id, Payment paymentRequest) {
        Payment existing = paymentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Payment not found"));

    // Copier les champs simples
        existing.setMethod(paymentRequest.getMethod());
        existing.setPaymentDate(paymentRequest.getPaymentDate());
        existing.setAmount(paymentRequest.getAmount());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
        if (paymentRequest.getOrder() != null &&paymentRequest.getOrder().getId() != null) {

        Order order = orderRepository.findById(paymentRequest.getOrder().getId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        existing.setOrder(order);
        order.setPayment(existing);
        
        }
    
    return paymentRepository.save(existing);
}

    // Pagination simple
    public Page<Payment> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<Payment> search(Map<String, String> filters, Pageable pageable) {
        return super.search(Payment.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<Payment> saveAll(List<Payment> paymentList) {
        return super.saveAll(paymentList);
    }

}