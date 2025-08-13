package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.StreamingService;
import com.example.modules.entertainment_ecosystem.repository.StreamingServiceRepository;
import com.example.modules.entertainment_ecosystem.model.StreamingPlatform;
import com.example.modules.entertainment_ecosystem.model.SubscriptionPlan;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class StreamingServiceService extends BaseService<StreamingService> {

    protected final StreamingServiceRepository streamingserviceRepository;

    public StreamingServiceService(StreamingServiceRepository repository)
    {
        super(repository);
        this.streamingserviceRepository = repository;
    }

    @Override
    public StreamingService save(StreamingService streamingservice) {

        if (streamingservice.getPlatforms() != null) {
            for (StreamingPlatform item : streamingservice.getPlatforms()) {
            item.setStreamingService(streamingservice);
            }
        }

        if (streamingservice.getPlans() != null) {
            for (SubscriptionPlan item : streamingservice.getPlans()) {
            item.setService(streamingservice);
            }
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
            for (var item : streamingserviceRequest.getPlatforms()) {
            item.setStreamingService(existing);
            existing.getPlatforms().add(item);
            }
        }

        existing.getPlans().clear();
        if (streamingserviceRequest.getPlans() != null) {
            for (var item : streamingserviceRequest.getPlans()) {
            item.setService(existing);
            existing.getPlans().add(item);
            }
        }

    

    


        return streamingserviceRepository.save(existing);
    }
}