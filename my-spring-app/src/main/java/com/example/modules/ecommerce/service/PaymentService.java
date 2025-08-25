package com.example.modules.ecommerce.service;

import com.example.core.service.BaseService;
import com.example.modules.ecommerce.model.Payment;
import com.example.modules.ecommerce.repository.PaymentRepository;
import com.example.modules.ecommerce.model.Order;
import com.example.modules.ecommerce.repository.OrderRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class PaymentService extends BaseService<Payment> {

    protected final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentService(PaymentRepository repository, OrderRepository orderRepository)
    {
        super(repository);
        this.paymentRepository = repository;
        this.orderRepository = orderRepository;
    }

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


    public Payment update(Long id, Payment paymentRequest) {
        Payment existing = paymentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Payment not found"));

    // Copier les champs simples
        existing.setMethod(paymentRequest.getMethod());
        existing.setPaymentDate(paymentRequest.getPaymentDate());
        existing.setAmount(paymentRequest.getAmount());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
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
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Payment> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        Payment entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
        if (entity.getOrder() != null) {
            entity.getOrder().setPayment(null);
            entity.setOrder(null);
        }
        
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
    @Transactional
    public List<Payment> saveAll(List<Payment> paymentList) {

        return paymentRepository.saveAll(paymentList);
    }

}