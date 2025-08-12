package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.OnlinePlatform;
import com.example.modules.entertainment_ecosystem.repository.OnlinePlatformRepository;
import com.example.modules.entertainment_ecosystem.model.OnlineEvent;
import com.example.modules.entertainment_ecosystem.model.StreamingPlatform;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class OnlinePlatformService extends BaseService<OnlinePlatform> {

    protected final OnlinePlatformRepository onlineplatformRepository;

    public OnlinePlatformService(OnlinePlatformRepository repository)
    {
        super(repository);
        this.onlineplatformRepository = repository;
    }

    @Override
    public OnlinePlatform save(OnlinePlatform onlineplatform) {

        if (onlineplatform.getLiveEvents() != null) {
            for (OnlineEvent item : onlineplatform.getLiveEvents()) {
            item.setPlatform(onlineplatform);
            }
        }

        if (onlineplatform.getStreams() != null) {
            for (StreamingPlatform item : onlineplatform.getStreams()) {
            item.setOnlinePlatform(onlineplatform);
            }
        }

        return onlineplatformRepository.save(onlineplatform);
    }


    public OnlinePlatform update(Long id, OnlinePlatform onlineplatformRequest) {
        OnlinePlatform existing = onlineplatformRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("OnlinePlatform not found"));

    // Copier les champs simples
        existing.setName(onlineplatformRequest.getName());
        existing.setUrl(onlineplatformRequest.getUrl());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        existing.getLiveEvents().clear();
        if (onlineplatformRequest.getLiveEvents() != null) {
            for (var item : onlineplatformRequest.getLiveEvents()) {
            item.setPlatform(existing);
            existing.getLiveEvents().add(item);
            }
        }

        existing.getStreams().clear();
        if (onlineplatformRequest.getStreams() != null) {
            for (var item : onlineplatformRequest.getStreams()) {
            item.setOnlinePlatform(existing);
            existing.getStreams().add(item);
            }
        }

        return onlineplatformRepository.save(existing);
    }
}