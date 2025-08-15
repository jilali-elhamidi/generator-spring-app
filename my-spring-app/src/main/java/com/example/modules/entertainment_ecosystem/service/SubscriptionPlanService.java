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

import org.springframework.stereotype.Service;
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

    public SubscriptionPlanService(SubscriptionPlanRepository repository,SubscriptionRepository subscriptionsRepository,StreamingServiceRepository serviceRepository,StreamingContentLicenseRepository includedStreamingContentLicensesRepository)
    {
        super(repository);
        this.subscriptionplanRepository = repository;
        this.subscriptionsRepository = subscriptionsRepository;
        this.serviceRepository = serviceRepository;
        this.includedStreamingContentLicensesRepository = includedStreamingContentLicensesRepository;
    }

    @Override
    public SubscriptionPlan save(SubscriptionPlan subscriptionplan) {


    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (subscriptionplan.getSubscriptions() != null) {
            List<Subscription> managedSubscriptions = new ArrayList<>();
            for (Subscription item : subscriptionplan.getSubscriptions()) {
            if (item.getId() != null) {
            Subscription existingItem = subscriptionsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Subscription not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setPlan(subscriptionplan);
            managedSubscriptions.add(existingItem);
            } else {
            item.setPlan(subscriptionplan);
            managedSubscriptions.add(item);
            }
            }
            subscriptionplan.setSubscriptions(managedSubscriptions);
            }
        
    

    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (subscriptionplan.getIncludedStreamingContentLicenses() != null) {
            List<StreamingContentLicense> managedIncludedStreamingContentLicenses = new ArrayList<>();
            for (StreamingContentLicense item : subscriptionplan.getIncludedStreamingContentLicenses()) {
            if (item.getId() != null) {
            StreamingContentLicense existingItem = includedStreamingContentLicensesRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("StreamingContentLicense not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setSubscriptionPlan(subscriptionplan);
            managedIncludedStreamingContentLicenses.add(existingItem);
            } else {
            item.setSubscriptionPlan(subscriptionplan);
            managedIncludedStreamingContentLicenses.add(item);
            }
            }
            subscriptionplan.setIncludedStreamingContentLicenses(managedIncludedStreamingContentLicenses);
            }
        
    

    
    if (subscriptionplan.getService() != null
        && subscriptionplan.getService().getId() != null) {
        StreamingService existingService = serviceRepository.findById(
        subscriptionplan.getService().getId()
        ).orElseThrow(() -> new RuntimeException("StreamingService not found"));
        subscriptionplan.setService(existingService);
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
        if (subscriptionplanRequest.getService() != null &&
        subscriptionplanRequest.getService().getId() != null) {

        StreamingService existingService = serviceRepository.findById(
        subscriptionplanRequest.getService().getId()
        ).orElseThrow(() -> new RuntimeException("StreamingService not found"));

        existing.setService(existingService);
        } else {
        existing.setService(null);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée
        // Vider la collection existante
        existing.getSubscriptions().clear();

        if (subscriptionplanRequest.getSubscriptions() != null) {
        for (var item : subscriptionplanRequest.getSubscriptions()) {
        Subscription existingItem;
        if (item.getId() != null) {
        existingItem = subscriptionsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Subscription not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setPlan(existing);

        // Ajouter directement dans la collection existante
        existing.getSubscriptions().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getIncludedStreamingContentLicenses().clear();

        if (subscriptionplanRequest.getIncludedStreamingContentLicenses() != null) {
        for (var item : subscriptionplanRequest.getIncludedStreamingContentLicenses()) {
        StreamingContentLicense existingItem;
        if (item.getId() != null) {
        existingItem = includedStreamingContentLicensesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("StreamingContentLicense not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setSubscriptionPlan(existing);

        // Ajouter directement dans la collection existante
        existing.getIncludedStreamingContentLicenses().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    

    

    


        return subscriptionplanRepository.save(existing);
    }


}