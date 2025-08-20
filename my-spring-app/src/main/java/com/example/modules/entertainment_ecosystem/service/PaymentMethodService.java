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

    public PaymentMethodService(PaymentMethodRepository repository,PaymentRepository paymentsRepository)
    {
        super(repository);
        this.paymentmethodRepository = repository;
        this.paymentsRepository = paymentsRepository;
    }

    @Override
    public PaymentMethod save(PaymentMethod paymentmethod) {


    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (paymentmethod.getPayments() != null) {
            List<Payment> managedPayments = new ArrayList<>();
            for (Payment item : paymentmethod.getPayments()) {
            if (item.getId() != null) {
            Payment existingItem = paymentsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Payment not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setMethod(paymentmethod);
            managedPayments.add(existingItem);
            } else {
            item.setMethod(paymentmethod);
            managedPayments.add(item);
            }
            }
            paymentmethod.setPayments(managedPayments);
            }
        
    


    

    

        return paymentmethodRepository.save(paymentmethod);
    }


    public PaymentMethod update(Long id, PaymentMethod paymentmethodRequest) {
        PaymentMethod existing = paymentmethodRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("PaymentMethod not found"));

    // Copier les champs simples
        existing.setName(paymentmethodRequest.getName());
        existing.setType(paymentmethodRequest.getType());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée
        // Vider la collection existante
        existing.getPayments().clear();

        if (paymentmethodRequest.getPayments() != null) {
        for (var item : paymentmethodRequest.getPayments()) {
        Payment existingItem;
        if (item.getId() != null) {
        existingItem = paymentsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Payment not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setMethod(existing);

        // Ajouter directement dans la collection existante
        existing.getPayments().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    


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
        
            child.setMethod(null); // retirer la référence inverse
        
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