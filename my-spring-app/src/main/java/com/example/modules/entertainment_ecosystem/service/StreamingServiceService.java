package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.StreamingService;
import com.example.modules.entertainment_ecosystem.repository.StreamingServiceRepository;
import com.example.modules.entertainment_ecosystem.model.StreamingPlatform;
import com.example.modules.entertainment_ecosystem.repository.StreamingPlatformRepository;
import com.example.modules.entertainment_ecosystem.model.SubscriptionPlan;
import com.example.modules.entertainment_ecosystem.repository.SubscriptionPlanRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class StreamingServiceService extends BaseService<StreamingService> {

    protected final StreamingServiceRepository streamingserviceRepository;
    private final StreamingPlatformRepository platformsRepository;
    private final SubscriptionPlanRepository plansRepository;

    public StreamingServiceService(StreamingServiceRepository repository, StreamingPlatformRepository platformsRepository, SubscriptionPlanRepository plansRepository)
    {
        super(repository);
        this.streamingserviceRepository = repository;
        this.platformsRepository = platformsRepository;
        this.plansRepository = plansRepository;
    }

    @Override
    public StreamingService save(StreamingService streamingservice) {
    // ---------- OneToMany ----------
        if (streamingservice.getPlatforms() != null) {
            List<StreamingPlatform> managedPlatforms = new ArrayList<>();
            for (StreamingPlatform item : streamingservice.getPlatforms()) {
                if (item.getId() != null) {
                    StreamingPlatform existingItem = platformsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("StreamingPlatform not found"));

                     existingItem.setStreamingService(streamingservice);
                     managedPlatforms.add(existingItem);
                } else {
                    item.setStreamingService(streamingservice);
                    managedPlatforms.add(item);
                }
            }
            streamingservice.setPlatforms(managedPlatforms);
        }
    
        if (streamingservice.getPlans() != null) {
            List<SubscriptionPlan> managedPlans = new ArrayList<>();
            for (SubscriptionPlan item : streamingservice.getPlans()) {
                if (item.getId() != null) {
                    SubscriptionPlan existingItem = plansRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("SubscriptionPlan not found"));

                     existingItem.setService(streamingservice);
                     managedPlans.add(existingItem);
                } else {
                    item.setService(streamingservice);
                    managedPlans.add(item);
                }
            }
            streamingservice.setPlans(managedPlans);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return streamingserviceRepository.save(streamingservice);
}


    public StreamingService update(Long id, StreamingService streamingserviceRequest) {
        StreamingService existing = streamingserviceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("StreamingService not found"));

    // Copier les champs simples
        existing.setName(streamingserviceRequest.getName());
        existing.setLogoUrl(streamingserviceRequest.getLogoUrl());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
        existing.getPlatforms().clear();

        if (streamingserviceRequest.getPlatforms() != null) {
            for (var item : streamingserviceRequest.getPlatforms()) {
                StreamingPlatform existingItem;
                if (item.getId() != null) {
                    existingItem = platformsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("StreamingPlatform not found"));
                } else {
                existingItem = item;
                }

                existingItem.setStreamingService(existing);
                existing.getPlatforms().add(existingItem);
            }
        }
        
        existing.getPlans().clear();

        if (streamingserviceRequest.getPlans() != null) {
            for (var item : streamingserviceRequest.getPlans()) {
                SubscriptionPlan existingItem;
                if (item.getId() != null) {
                    existingItem = plansRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("SubscriptionPlan not found"));
                } else {
                existingItem = item;
                }

                existingItem.setService(existing);
                existing.getPlans().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return streamingserviceRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<StreamingService> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        StreamingService entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getPlatforms() != null) {
            for (var child : entity.getPlatforms()) {
                // retirer la référence inverse
                child.setStreamingService(null);
            }
            entity.getPlatforms().clear();
        }
        
        if (entity.getPlans() != null) {
            for (var child : entity.getPlans()) {
                // retirer la référence inverse
                child.setService(null);
            }
            entity.getPlans().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
}