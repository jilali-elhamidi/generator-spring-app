package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.SubscriptionTier;
import com.example.modules.entertainment_ecosystem.repository.SubscriptionTierRepository;
import com.example.modules.entertainment_ecosystem.model.SubscriptionPlan;
import com.example.modules.entertainment_ecosystem.repository.SubscriptionPlanRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class SubscriptionTierService extends BaseService<SubscriptionTier> {

    protected final SubscriptionTierRepository subscriptiontierRepository;
    private final SubscriptionPlanRepository subscriptionPlanRepository;

    public SubscriptionTierService(SubscriptionTierRepository repository,SubscriptionPlanRepository subscriptionPlanRepository)
    {
        super(repository);
        this.subscriptiontierRepository = repository;
        this.subscriptionPlanRepository = subscriptionPlanRepository;
    }

    @Override
    public SubscriptionTier save(SubscriptionTier subscriptiontier) {


    


    

    
        if (subscriptiontier.getSubscriptionPlan() != null) {
        
        
            // Vérifier si l'entité est déjà persistée
            subscriptiontier.setSubscriptionPlan(
            subscriptionPlanRepository.findById(subscriptiontier.getSubscriptionPlan().getId())
            .orElseThrow(() -> new RuntimeException("subscriptionPlan not found"))
            );
        
        subscriptiontier.getSubscriptionPlan().setTier(subscriptiontier);
        }

        return subscriptiontierRepository.save(subscriptiontier);
    }


    public SubscriptionTier update(Long id, SubscriptionTier subscriptiontierRequest) {
        SubscriptionTier existing = subscriptiontierRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("SubscriptionTier not found"));

    // Copier les champs simples
        existing.setName(subscriptiontierRequest.getName());
        existing.setFeatures(subscriptiontierRequest.getFeatures());
        existing.setPrice(subscriptiontierRequest.getPrice());
        existing.setBillingPeriod(subscriptiontierRequest.getBillingPeriod());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

        if (subscriptiontierRequest.getSubscriptionPlan() != null
        && subscriptiontierRequest.getSubscriptionPlan().getId() != null) {

        SubscriptionPlan subscriptionPlan = subscriptionPlanRepository.findById(
        subscriptiontierRequest.getSubscriptionPlan().getId()
        ).orElseThrow(() -> new RuntimeException("SubscriptionPlan not found"));

        // Mise à jour de la relation côté propriétaire
        existing.setSubscriptionPlan(subscriptionPlan);

        // Si la relation est bidirectionnelle et que le champ inverse existe
        
            subscriptionPlan.setTier(existing);
        
        }

    


        return subscriptiontierRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<SubscriptionTier> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

SubscriptionTier entity = entityOpt.get();

// --- Dissocier OneToMany ---

    


// --- Dissocier ManyToMany ---

    



// --- Dissocier OneToOne ---

    
        if (entity.getSubscriptionPlan() != null) {
        // Dissocier côté inverse automatiquement
        entity.getSubscriptionPlan().setTier(null);
        // Dissocier côté direct
        entity.setSubscriptionPlan(null);
        }
    


// --- Dissocier ManyToOne ---

    


repository.delete(entity);
return true;
}
}