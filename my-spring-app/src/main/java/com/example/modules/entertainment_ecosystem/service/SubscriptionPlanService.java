package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.SubscriptionPlan;
import com.example.modules.entertainment_ecosystem.repository.SubscriptionPlanRepository;
import com.example.modules.entertainment_ecosystem.model.Subscription;
import com.example.modules.entertainment_ecosystem.model.StreamingService;
import com.example.modules.entertainment_ecosystem.repository.StreamingServiceRepository;
import com.example.modules.entertainment_ecosystem.model.StreamingContentLicense;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class SubscriptionPlanService extends BaseService<SubscriptionPlan> {

    protected final SubscriptionPlanRepository subscriptionplanRepository;
    private final StreamingServiceRepository serviceRepository;

    public SubscriptionPlanService(SubscriptionPlanRepository repository,StreamingServiceRepository serviceRepository)
    {
        super(repository);
        this.subscriptionplanRepository = repository;
        this.serviceRepository = serviceRepository;
    }

    @Override
    public SubscriptionPlan save(SubscriptionPlan subscriptionplan) {

        if (subscriptionplan.getService() != null && subscriptionplan.getService().getId() != null) {
        StreamingService service = serviceRepository.findById(subscriptionplan.getService().getId())
                .orElseThrow(() -> new RuntimeException("StreamingService not found"));
        subscriptionplan.setService(service);
        }

        if (subscriptionplan.getSubscriptions() != null) {
            for (Subscription item : subscriptionplan.getSubscriptions()) {
            item.setPlan(subscriptionplan);
            }
        }

        if (subscriptionplan.getIncludedStreamingContentLicenses() != null) {
            for (StreamingContentLicense item : subscriptionplan.getIncludedStreamingContentLicenses()) {
            item.setSubscriptionPlan(subscriptionplan);
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

        if (subscriptionplanRequest.getService() != null && subscriptionplanRequest.getService().getId() != null) {
        StreamingService service = serviceRepository.findById(subscriptionplanRequest.getService().getId())
                .orElseThrow(() -> new RuntimeException("StreamingService not found"));
        existing.setService(service);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        existing.getSubscriptions().clear();
        if (subscriptionplanRequest.getSubscriptions() != null) {
            for (var item : subscriptionplanRequest.getSubscriptions()) {
            item.setPlan(existing);
            existing.getSubscriptions().add(item);
            }
        }

        existing.getIncludedStreamingContentLicenses().clear();
        if (subscriptionplanRequest.getIncludedStreamingContentLicenses() != null) {
            for (var item : subscriptionplanRequest.getIncludedStreamingContentLicenses()) {
            item.setSubscriptionPlan(existing);
            existing.getIncludedStreamingContentLicenses().add(item);
            }
        }

    

    

    


        return subscriptionplanRepository.save(existing);
    }
}