package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.SubscriptionPlan;
import com.example.modules.entertainment_ecosystem.repository.SubscriptionPlanRepository;

import com.example.modules.entertainment_ecosystem.model.Subscription;
import com.example.modules.entertainment_ecosystem.repository.SubscriptionRepository;

import com.example.modules.entertainment_ecosystem.model.StreamingService;
import com.example.modules.entertainment_ecosystem.repository.StreamingServiceRepository;

import com.example.modules.entertainment_ecosystem.model.StreamingContentLicense;
import com.example.modules.entertainment_ecosystem.repository.StreamingContentLicenseRepository;

import com.example.modules.entertainment_ecosystem.model.SubscriptionTier;
import com.example.modules.entertainment_ecosystem.repository.SubscriptionTierRepository;

import com.example.modules.entertainment_ecosystem.model.SubscriptionFeature;
import com.example.modules.entertainment_ecosystem.repository.SubscriptionFeatureRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class SubscriptionPlanService extends BaseService<SubscriptionPlan> {

    protected final SubscriptionPlanRepository subscriptionplanRepository;
    
    protected final SubscriptionRepository subscriptionsRepository;
    
    protected final StreamingServiceRepository serviceRepository;
    
    protected final StreamingContentLicenseRepository includedStreamingContentLicensesRepository;
    
    protected final SubscriptionTierRepository tierRepository;
    
    protected final SubscriptionFeatureRepository featuresRepository;
    

    public SubscriptionPlanService(SubscriptionPlanRepository repository, SubscriptionRepository subscriptionsRepository, StreamingServiceRepository serviceRepository, StreamingContentLicenseRepository includedStreamingContentLicensesRepository, SubscriptionTierRepository tierRepository, SubscriptionFeatureRepository featuresRepository)
    {
        super(repository);
        this.subscriptionplanRepository = repository;
        
        this.subscriptionsRepository = subscriptionsRepository;
        
        this.serviceRepository = serviceRepository;
        
        this.includedStreamingContentLicensesRepository = includedStreamingContentLicensesRepository;
        
        this.tierRepository = tierRepository;
        
        this.featuresRepository = featuresRepository;
        
    }

    @Transactional
    @Override
    public SubscriptionPlan save(SubscriptionPlan subscriptionplan) {
    // ---------- OneToMany ----------
        if (subscriptionplan.getSubscriptions() != null) {
            List<Subscription> managedSubscriptions = new ArrayList<>();
            for (Subscription item : subscriptionplan.getSubscriptions()) {
                if (item.getId() != null) {
                    Subscription existingItem = subscriptionsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Subscription not found"));

                     existingItem.setPlan(subscriptionplan);
                     managedSubscriptions.add(existingItem);
                } else {
                    item.setPlan(subscriptionplan);
                    managedSubscriptions.add(item);
                }
            }
            subscriptionplan.setSubscriptions(managedSubscriptions);
        }
    
        if (subscriptionplan.getIncludedStreamingContentLicenses() != null) {
            List<StreamingContentLicense> managedIncludedStreamingContentLicenses = new ArrayList<>();
            for (StreamingContentLicense item : subscriptionplan.getIncludedStreamingContentLicenses()) {
                if (item.getId() != null) {
                    StreamingContentLicense existingItem = includedStreamingContentLicensesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("StreamingContentLicense not found"));

                     existingItem.setSubscriptionPlan(subscriptionplan);
                     managedIncludedStreamingContentLicenses.add(existingItem);
                } else {
                    item.setSubscriptionPlan(subscriptionplan);
                    managedIncludedStreamingContentLicenses.add(item);
                }
            }
            subscriptionplan.setIncludedStreamingContentLicenses(managedIncludedStreamingContentLicenses);
        }
    
    // ---------- ManyToMany ----------
        if (subscriptionplan.getFeatures() != null &&
            !subscriptionplan.getFeatures().isEmpty()) {

            List<SubscriptionFeature> attachedFeatures = new ArrayList<>();
            for (SubscriptionFeature item : subscriptionplan.getFeatures()) {
                if (item.getId() != null) {
                    SubscriptionFeature existingItem = featuresRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("SubscriptionFeature not found with id " + item.getId()));
                    attachedFeatures.add(existingItem);
                } else {

                    SubscriptionFeature newItem = featuresRepository.save(item);
                    attachedFeatures.add(newItem);
                }
            }

            subscriptionplan.setFeatures(attachedFeatures);

            // côté propriétaire (SubscriptionFeature → SubscriptionPlan)
            attachedFeatures.forEach(it -> it.getSubscriptionPlans().add(subscriptionplan));
        }
        
    // ---------- ManyToOne ----------
        if (subscriptionplan.getService() != null) {
            if (subscriptionplan.getService().getId() != null) {
                StreamingService existingService = serviceRepository.findById(
                    subscriptionplan.getService().getId()
                ).orElseThrow(() -> new RuntimeException("StreamingService not found with id "
                    + subscriptionplan.getService().getId()));
                subscriptionplan.setService(existingService);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                StreamingService newService = serviceRepository.save(subscriptionplan.getService());
                subscriptionplan.setService(newService);
            }
        }
        
    // ---------- OneToOne ----------
        if (subscriptionplan.getTier() != null) {
            if (subscriptionplan.getTier().getId() != null) {
                SubscriptionTier existingTier = tierRepository.findById(subscriptionplan.getTier().getId())
                    .orElseThrow(() -> new RuntimeException("SubscriptionTier not found with id "
                        + subscriptionplan.getTier().getId()));
                subscriptionplan.setTier(existingTier);
            } else {
                // Nouvel objet → sauvegarde d'abord
                SubscriptionTier newTier = tierRepository.save(subscriptionplan.getTier());
                subscriptionplan.setTier(newTier);
            }

            subscriptionplan.getTier().setSubscriptionPlan(subscriptionplan);
        }
        
    return subscriptionplanRepository.save(subscriptionplan);
}

    @Transactional
    @Override
    public SubscriptionPlan update(Long id, SubscriptionPlan subscriptionplanRequest) {
        SubscriptionPlan existing = subscriptionplanRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("SubscriptionPlan not found"));

    // Copier les champs simples
        existing.setName(subscriptionplanRequest.getName());
        existing.setPrice(subscriptionplanRequest.getPrice());
        existing.setDescription(subscriptionplanRequest.getDescription());

    // ---------- Relations ManyToOne ----------
        if (subscriptionplanRequest.getService() != null &&
            subscriptionplanRequest.getService().getId() != null) {

            StreamingService existingService = serviceRepository.findById(
                subscriptionplanRequest.getService().getId()
            ).orElseThrow(() -> new RuntimeException("StreamingService not found"));

            existing.setService(existingService);
        } else {
            existing.setService(null);
        }
        
    // ---------- Relations ManyToMany ----------
        if (subscriptionplanRequest.getFeatures() != null) {
            existing.getFeatures().clear();

            List<SubscriptionFeature> featuresList = subscriptionplanRequest.getFeatures().stream()
                .map(item -> featuresRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("SubscriptionFeature not found")))
                .collect(Collectors.toList());

            existing.getFeatures().addAll(featuresList);

            // Mettre à jour le côté inverse
            featuresList.forEach(it -> {
                if (!it.getSubscriptionPlans().contains(existing)) {
                    it.getSubscriptionPlans().add(existing);
                }
            });
        }
        
    // ---------- Relations OneToMany ----------
        existing.getSubscriptions().clear();

        if (subscriptionplanRequest.getSubscriptions() != null) {
            for (var item : subscriptionplanRequest.getSubscriptions()) {
                Subscription existingItem;
                if (item.getId() != null) {
                    existingItem = subscriptionsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Subscription not found"));
                } else {
                existingItem = item;
                }

                existingItem.setPlan(existing);
                existing.getSubscriptions().add(existingItem);
            }
        }
        
        existing.getIncludedStreamingContentLicenses().clear();

        if (subscriptionplanRequest.getIncludedStreamingContentLicenses() != null) {
            for (var item : subscriptionplanRequest.getIncludedStreamingContentLicenses()) {
                StreamingContentLicense existingItem;
                if (item.getId() != null) {
                    existingItem = includedStreamingContentLicensesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("StreamingContentLicense not found"));
                } else {
                existingItem = item;
                }

                existingItem.setSubscriptionPlan(existing);
                existing.getIncludedStreamingContentLicenses().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
        if (subscriptionplanRequest.getTier() != null &&subscriptionplanRequest.getTier().getId() != null) {

        SubscriptionTier tier = tierRepository.findById(subscriptionplanRequest.getTier().getId())
                .orElseThrow(() -> new RuntimeException("SubscriptionTier not found"));

        existing.setTier(tier);
        tier.setSubscriptionPlan(existing);
        }
    
    return subscriptionplanRepository.save(existing);
}

    // Pagination simple
    public Page<SubscriptionPlan> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<SubscriptionPlan> search(Map<String, String> filters, Pageable pageable) {
        return super.search(SubscriptionPlan.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<SubscriptionPlan> saveAll(List<SubscriptionPlan> subscriptionplanList) {
        return super.saveAll(subscriptionplanList);
    }

}