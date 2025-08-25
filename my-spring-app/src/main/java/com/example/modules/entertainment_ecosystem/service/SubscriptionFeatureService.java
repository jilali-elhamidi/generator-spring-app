package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.SubscriptionFeature;
import com.example.modules.entertainment_ecosystem.repository.SubscriptionFeatureRepository;
import com.example.modules.entertainment_ecosystem.model.SubscriptionPlan;
import com.example.modules.entertainment_ecosystem.repository.SubscriptionPlanRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class SubscriptionFeatureService extends BaseService<SubscriptionFeature> {

    protected final SubscriptionFeatureRepository subscriptionfeatureRepository;
    private final SubscriptionPlanRepository subscriptionPlansRepository;

    public SubscriptionFeatureService(SubscriptionFeatureRepository repository, SubscriptionPlanRepository subscriptionPlansRepository)
    {
        super(repository);
        this.subscriptionfeatureRepository = repository;
        this.subscriptionPlansRepository = subscriptionPlansRepository;
    }

    @Override
    public SubscriptionFeature save(SubscriptionFeature subscriptionfeature) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
        if (subscriptionfeature.getSubscriptionPlans() != null &&
            !subscriptionfeature.getSubscriptionPlans().isEmpty()) {

            List<SubscriptionPlan> attachedSubscriptionPlans = new ArrayList<>();
            for (SubscriptionPlan item : subscriptionfeature.getSubscriptionPlans()) {
                if (item.getId() != null) {
                    SubscriptionPlan existingItem = subscriptionPlansRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("SubscriptionPlan not found with id " + item.getId()));
                    attachedSubscriptionPlans.add(existingItem);
                } else {

                    SubscriptionPlan newItem = subscriptionPlansRepository.save(item);
                    attachedSubscriptionPlans.add(newItem);
                }
            }

            subscriptionfeature.setSubscriptionPlans(attachedSubscriptionPlans);

            // côté propriétaire (SubscriptionPlan → SubscriptionFeature)
            attachedSubscriptionPlans.forEach(it -> it.getFeatures().add(subscriptionfeature));
        }
        
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return subscriptionfeatureRepository.save(subscriptionfeature);
}


    public SubscriptionFeature update(Long id, SubscriptionFeature subscriptionfeatureRequest) {
        SubscriptionFeature existing = subscriptionfeatureRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("SubscriptionFeature not found"));

    // Copier les champs simples
        existing.setName(subscriptionfeatureRequest.getName());
        existing.setDescription(subscriptionfeatureRequest.getDescription());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
        if (subscriptionfeatureRequest.getSubscriptionPlans() != null) {
            existing.getSubscriptionPlans().clear();

            List<SubscriptionPlan> subscriptionPlansList = subscriptionfeatureRequest.getSubscriptionPlans().stream()
                .map(item -> subscriptionPlansRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("SubscriptionPlan not found")))
                .collect(Collectors.toList());

            existing.getSubscriptionPlans().addAll(subscriptionPlansList);

            // Mettre à jour le côté inverse
            subscriptionPlansList.forEach(it -> {
                if (!it.getFeatures().contains(existing)) {
                    it.getFeatures().add(existing);
                }
            });
        }
        
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return subscriptionfeatureRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<SubscriptionFeature> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        SubscriptionFeature entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
        if (entity.getSubscriptionPlans() != null) {
            for (SubscriptionPlan item : new ArrayList<>(entity.getSubscriptionPlans())) {
                
                item.getFeatures().remove(entity); // retire côté inverse
            }
            entity.getSubscriptionPlans().clear(); // puis vide côté courant
        }
        
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
    @Transactional
    public List<SubscriptionFeature> saveAll(List<SubscriptionFeature> subscriptionfeatureList) {

        return subscriptionfeatureRepository.saveAll(subscriptionfeatureList);
    }

}