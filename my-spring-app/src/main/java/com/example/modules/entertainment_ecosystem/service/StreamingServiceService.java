package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.StreamingService;
import com.example.modules.entertainment_ecosystem.repository.StreamingServiceRepository;
import com.example.modules.entertainment_ecosystem.model.StreamingPlatform;
import com.example.modules.entertainment_ecosystem.repository.StreamingPlatformRepository;
import com.example.modules.entertainment_ecosystem.model.SubscriptionPlan;
import com.example.modules.entertainment_ecosystem.repository.SubscriptionPlanRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class StreamingServiceService extends BaseService<StreamingService> {

    protected final StreamingServiceRepository streamingserviceRepository;
    private final StreamingPlatformRepository platformsRepository;
    private final SubscriptionPlanRepository plansRepository;

    public StreamingServiceService(StreamingServiceRepository repository,StreamingPlatformRepository platformsRepository,SubscriptionPlanRepository plansRepository)
    {
        super(repository);
        this.streamingserviceRepository = repository;
        this.platformsRepository = platformsRepository;
        this.plansRepository = plansRepository;
    }

    @Override
    public StreamingService save(StreamingService streamingservice) {


    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (streamingservice.getPlatforms() != null) {
            List<StreamingPlatform> managedPlatforms = new ArrayList<>();
            for (StreamingPlatform item : streamingservice.getPlatforms()) {
            if (item.getId() != null) {
            StreamingPlatform existingItem = platformsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("StreamingPlatform not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setStreamingService(streamingservice);
            managedPlatforms.add(existingItem);
            } else {
            item.setStreamingService(streamingservice);
            managedPlatforms.add(item);
            }
            }
            streamingservice.setPlatforms(managedPlatforms);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (streamingservice.getPlans() != null) {
            List<SubscriptionPlan> managedPlans = new ArrayList<>();
            for (SubscriptionPlan item : streamingservice.getPlans()) {
            if (item.getId() != null) {
            SubscriptionPlan existingItem = plansRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("SubscriptionPlan not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setService(streamingservice);
            managedPlans.add(existingItem);
            } else {
            item.setService(streamingservice);
            managedPlans.add(item);
            }
            }
            streamingservice.setPlans(managedPlans);
            }
        
    

    
    

        return streamingserviceRepository.save(streamingservice);
    }


    public StreamingService update(Long id, StreamingService streamingserviceRequest) {
        StreamingService existing = streamingserviceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("StreamingService not found"));

    // Copier les champs simples
        existing.setName(streamingserviceRequest.getName());
        existing.setLogoUrl(streamingserviceRequest.getLogoUrl());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée
        existing.getPlatforms().clear();

        if (streamingserviceRequest.getPlatforms() != null) {
        List<StreamingPlatform> managedPlatforms = new ArrayList<>();

        for (var item : streamingserviceRequest.getPlatforms()) {
        if (item.getId() != null) {
        StreamingPlatform existingItem = platformsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("StreamingPlatform not found"));
        existingItem.setStreamingService(existing);
        managedPlatforms.add(existingItem);
        } else {
        item.setStreamingService(existing);
        managedPlatforms.add(item);
        }
        }
        existing.setPlatforms(managedPlatforms);
        }
        existing.getPlans().clear();

        if (streamingserviceRequest.getPlans() != null) {
        List<SubscriptionPlan> managedPlans = new ArrayList<>();

        for (var item : streamingserviceRequest.getPlans()) {
        if (item.getId() != null) {
        SubscriptionPlan existingItem = plansRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("SubscriptionPlan not found"));
        existingItem.setService(existing);
        managedPlans.add(existingItem);
        } else {
        item.setService(existing);
        managedPlans.add(item);
        }
        }
        existing.setPlans(managedPlans);
        }

    

    


        return streamingserviceRepository.save(existing);
    }


}