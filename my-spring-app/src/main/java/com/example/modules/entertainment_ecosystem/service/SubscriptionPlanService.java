package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.SubscriptionPlan;
import com.example.modules.entertainment_ecosystem.repository.SubscriptionPlanRepository;
import com.example.modules.entertainment_ecosystem.model.Subscription;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class SubscriptionPlanService extends BaseService<SubscriptionPlan> {

    protected final SubscriptionPlanRepository subscriptionplanRepository;

    public SubscriptionPlanService(SubscriptionPlanRepository repository)
    {
        super(repository);
        this.subscriptionplanRepository = repository;
    }

    @Override
    public SubscriptionPlan save(SubscriptionPlan subscriptionplan) {

        if (subscriptionplan.getSubscriptions() != null) {
            for (Subscription item : subscriptionplan.getSubscriptions()) {
            item.setPlan(subscriptionplan);
            }
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

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        existing.getSubscriptions().clear();
        if (subscriptionplanRequest.getSubscriptions() != null) {
            for (var item : subscriptionplanRequest.getSubscriptions()) {
            item.setPlan(existing);
            existing.getSubscriptions().add(item);
            }
        }

    


        return subscriptionplanRepository.save(existing);
    }
}