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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class SubscriptionPlanService extends BaseService<SubscriptionPlan> {

    protected final SubscriptionPlanRepository subscriptionplanRepository;
    private final SubscriptionRepository subscriptionsRepository;
    private final StreamingServiceRepository serviceRepository;
    private final StreamingContentLicenseRepository includedStreamingContentLicensesRepository;
    private final SubscriptionTierRepository tierRepository;
    private final SubscriptionFeatureRepository featuresRepository;

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

            List<SubscriptionFeature> attachedFeatures = subscriptionplan.getFeatures().stream()
            .map(item -> featuresRepository.findById(item.getId())
                .orElseThrow(() -> new RuntimeException("SubscriptionFeature not found with id " + item.getId())))
            .toList();

            subscriptionplan.setFeatures(attachedFeatures);

            // côté propriétaire (SubscriptionFeature → SubscriptionPlan)
            attachedFeatures.forEach(it -> it.getSubscriptionPlans().add(subscriptionplan));
        }
        
    // ---------- ManyToOne ----------
        if (subscriptionplan.getService() != null &&
            subscriptionplan.getService().getId() != null) {

            StreamingService existingService = serviceRepository.findById(
                subscriptionplan.getService().getId()
            ).orElseThrow(() -> new RuntimeException("StreamingService not found"));

            subscriptionplan.setService(existingService);
        }
        
    // ---------- OneToOne ----------
        if (subscriptionplan.getTier() != null) {
            
            
                // Vérifier si l'entité est déjà persistée
            subscriptionplan.setTier(
                tierRepository.findById(subscriptionplan.getTier().getId())
                    .orElseThrow(() -> new RuntimeException("tier not found"))
            );
            
            subscriptionplan.getTier().setSubscriptionPlan(subscriptionplan);
        }
        

    return subscriptionplanRepository.save(subscriptionplan);
}


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
        
    // ---------- Relations ManyToOne ----------
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
            if (subscriptionplanRequest.getTier() != null &&
            subscriptionplanRequest.getTier().getId() != null) {

            SubscriptionTier tier = tierRepository.findById(
                subscriptionplanRequest.getTier().getId()
            ).orElseThrow(() -> new RuntimeException("SubscriptionTier not found"));

            existing.setTier(tier);

            
            tier.setSubscriptionPlan(existing);
            
        }
        

    return subscriptionplanRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<SubscriptionPlan> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        SubscriptionPlan entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getSubscriptions() != null) {
            for (var child : entity.getSubscriptions()) {
                
                child.setPlan(null); // retirer la référence inverse
                
            }
            entity.getSubscriptions().clear();
        }
        
        if (entity.getIncludedStreamingContentLicenses() != null) {
            for (var child : entity.getIncludedStreamingContentLicenses()) {
                
                child.setSubscriptionPlan(null); // retirer la référence inverse
                
            }
            entity.getIncludedStreamingContentLicenses().clear();
        }
        
    // --- Dissocier ManyToMany ---
        if (entity.getFeatures() != null) {
            for (SubscriptionFeature item : new ArrayList<>(entity.getFeatures())) {
                
                item.getSubscriptionPlans().remove(entity); // retire côté inverse
                
            }
            entity.getFeatures().clear(); // puis vide côté courant
        }
        
    // --- Dissocier OneToOne ---
        if (entity.getTier() != null) {
            entity.getTier().setSubscriptionPlan(null);
            entity.setTier(null);
        }
        
    // --- Dissocier ManyToOne ---
        if (entity.getService() != null) {
            entity.setService(null);
        }
        
        repository.delete(entity);
        return true;
    }
}