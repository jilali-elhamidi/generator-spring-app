package com.example.modules.ecommerce.service;

import com.example.core.service.BaseService;
import com.example.modules.ecommerce.model.Payment;
import com.example.modules.ecommerce.repository.PaymentRepository;
import com.example.modules.ecommerce.model.Order;
import com.example.modules.ecommerce.repository.OrderRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class PaymentService extends BaseService<Payment> {

    protected final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentService(PaymentRepository repository,OrderRepository orderRepository)
    {
        super(repository);
        this.paymentRepository = repository;
            this.orderRepository = orderRepository;
    }

    @Override
    public Payment save(Payment payment) {
        if (payment.getOrder() != null) {
        
        
            // Vérifier si l'entité est déjà persistée
            payment.setOrder(
            orderRepository.findById(payment.getOrder().getId())
            .orElseThrow(() -> new RuntimeException("order not found"))
            );
        
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

    

        if (paymentRequest.getOrder() != null
        && paymentRequest.getOrder().getId() != null) {

        Order order = orderRepository.findById(
        paymentRequest.getOrder().getId()
        ).orElseThrow(() -> new RuntimeException("Order not found"));

        // Mise à jour de la relation côté propriétaire
        existing.setOrder(order);

        // Si la relation est bidirectionnelle et que le champ inverse existe
        
            order.setPayment(existing);
        
        }

    


        return paymentRepository.save(existing);
    }
}