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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class StreamingServiceService extends BaseService<StreamingService> {

    protected final StreamingServiceRepository streamingserviceRepository;
    
    protected final StreamingPlatformRepository platformsRepository;
    
    protected final SubscriptionPlanRepository plansRepository;
    

    public StreamingServiceService(StreamingServiceRepository repository, StreamingPlatformRepository platformsRepository, SubscriptionPlanRepository plansRepository)
    {
        super(repository);
        this.streamingserviceRepository = repository;
        
        this.platformsRepository = platformsRepository;
        
        this.plansRepository = plansRepository;
        
    }

    @Transactional
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

    @Transactional
    @Override
    public StreamingService update(Long id, StreamingService streamingserviceRequest) {
        StreamingService existing = streamingserviceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("StreamingService not found"));

    // Copier les champs simples
        existing.setName(streamingserviceRequest.getName());
        existing.setLogoUrl(streamingserviceRequest.getLogoUrl());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
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

    // Pagination simple
    public Page<StreamingService> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<StreamingService> search(Map<String, String> filters, Pageable pageable) {
        return super.search(StreamingService.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<StreamingService> saveAll(List<StreamingService> streamingserviceList) {
        return super.saveAll(streamingserviceList);
    }

}