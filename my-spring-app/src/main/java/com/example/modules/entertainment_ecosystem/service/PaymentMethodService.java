package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.PaymentMethod;
import com.example.modules.entertainment_ecosystem.repository.PaymentMethodRepository;
import com.example.modules.entertainment_ecosystem.model.Payment;
import com.example.modules.entertainment_ecosystem.repository.PaymentRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class PaymentMethodService extends BaseService<PaymentMethod> {

    protected final PaymentMethodRepository paymentmethodRepository;
    private final PaymentRepository paymentsRepository;

    public PaymentMethodService(PaymentMethodRepository repository, PaymentRepository paymentsRepository)
    {
        super(repository);
        this.paymentmethodRepository = repository;
        this.paymentsRepository = paymentsRepository;
    }

    @Override
    public PaymentMethod save(PaymentMethod paymentmethod) {
    // ---------- OneToMany ----------
        if (paymentmethod.getPayments() != null) {
            List<Payment> managedPayments = new ArrayList<>();
            for (Payment item : paymentmethod.getPayments()) {
                if (item.getId() != null) {
                    Payment existingItem = paymentsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Payment not found"));

                     existingItem.setMethod(paymentmethod);
                     managedPayments.add(existingItem);
                } else {
                    item.setMethod(paymentmethod);
                    managedPayments.add(item);
                }
            }
            paymentmethod.setPayments(managedPayments);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return paymentmethodRepository.save(paymentmethod);
}


    public PaymentMethod update(Long id, PaymentMethod paymentmethodRequest) {
        PaymentMethod existing = paymentmethodRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("PaymentMethod not found"));

    // Copier les champs simples
        existing.setName(paymentmethodRequest.getName());
        existing.setType(paymentmethodRequest.getType());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
        existing.getPayments().clear();

        if (paymentmethodRequest.getPayments() != null) {
            for (var item : paymentmethodRequest.getPayments()) {
                Payment existingItem;
                if (item.getId() != null) {
                    existingItem = paymentsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Payment not found"));
                } else {
                existingItem = item;
                }

                existingItem.setMethod(existing);
                existing.getPayments().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return paymentmethodRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<PaymentMethod> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        PaymentMethod entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getPayments() != null) {
            for (var child : entity.getPayments()) {
                // retirer la référence inverse
                child.setMethod(null);
            }
            entity.getPayments().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
}