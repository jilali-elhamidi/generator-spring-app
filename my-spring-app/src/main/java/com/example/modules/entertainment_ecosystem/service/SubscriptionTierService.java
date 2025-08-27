package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.SubscriptionTier;
import com.example.modules.entertainment_ecosystem.repository.SubscriptionTierRepository;

import com.example.modules.entertainment_ecosystem.model.SubscriptionPlan;
import com.example.modules.entertainment_ecosystem.repository.SubscriptionPlanRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class SubscriptionTierService extends BaseService<SubscriptionTier> {

    protected final SubscriptionTierRepository subscriptiontierRepository;
    
    protected final SubscriptionPlanRepository subscriptionPlanRepository;
    

    public SubscriptionTierService(SubscriptionTierRepository repository, SubscriptionPlanRepository subscriptionPlanRepository)
    {
        super(repository);
        this.subscriptiontierRepository = repository;
        
        this.subscriptionPlanRepository = subscriptionPlanRepository;
        
    }

    @Transactional
    @Override
    public SubscriptionTier save(SubscriptionTier subscriptiontier) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
        if (subscriptiontier.getSubscriptionPlan() != null) {
            if (subscriptiontier.getSubscriptionPlan().getId() != null) {
                SubscriptionPlan existingSubscriptionPlan = subscriptionPlanRepository.findById(subscriptiontier.getSubscriptionPlan().getId())
                    .orElseThrow(() -> new RuntimeException("SubscriptionPlan not found with id "
                        + subscriptiontier.getSubscriptionPlan().getId()));
                subscriptiontier.setSubscriptionPlan(existingSubscriptionPlan);
            } else {
                // Nouvel objet → sauvegarde d'abord
                SubscriptionPlan newSubscriptionPlan = subscriptionPlanRepository.save(subscriptiontier.getSubscriptionPlan());
                subscriptiontier.setSubscriptionPlan(newSubscriptionPlan);
            }

            subscriptiontier.getSubscriptionPlan().setTier(subscriptiontier);
        }
        
    return subscriptiontierRepository.save(subscriptiontier);
}

    @Transactional
    @Override
    public SubscriptionTier update(Long id, SubscriptionTier subscriptiontierRequest) {
        SubscriptionTier existing = subscriptiontierRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("SubscriptionTier not found"));

    // Copier les champs simples
        existing.setName(subscriptiontierRequest.getName());
        existing.setFeatures(subscriptiontierRequest.getFeatures());
        existing.setPrice(subscriptiontierRequest.getPrice());
        existing.setBillingPeriod(subscriptiontierRequest.getBillingPeriod());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
        if (subscriptiontierRequest.getSubscriptionPlan() != null &&subscriptiontierRequest.getSubscriptionPlan().getId() != null) {

        SubscriptionPlan subscriptionPlan = subscriptionPlanRepository.findById(subscriptiontierRequest.getSubscriptionPlan().getId())
                .orElseThrow(() -> new RuntimeException("SubscriptionPlan not found"));

        existing.setSubscriptionPlan(subscriptionPlan);
        subscriptionPlan.setTier(existing);
        
        }
    
    return subscriptiontierRepository.save(existing);
}

    // Pagination simple
    public Page<SubscriptionTier> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<SubscriptionTier> search(Map<String, String> filters, Pageable pageable) {
        return super.search(SubscriptionTier.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<SubscriptionTier> saveAll(List<SubscriptionTier> subscriptiontierList) {
        return super.saveAll(subscriptiontierList);
    }

}