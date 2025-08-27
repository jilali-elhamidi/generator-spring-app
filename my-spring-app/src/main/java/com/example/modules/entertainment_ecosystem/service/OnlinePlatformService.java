package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.OnlinePlatform;
import com.example.modules.entertainment_ecosystem.repository.OnlinePlatformRepository;

import com.example.modules.entertainment_ecosystem.model.StreamingPlatform;
import com.example.modules.entertainment_ecosystem.repository.StreamingPlatformRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class OnlinePlatformService extends BaseService<OnlinePlatform> {

    protected final OnlinePlatformRepository onlineplatformRepository;
    
    protected final StreamingPlatformRepository streamsRepository;
    

    public OnlinePlatformService(OnlinePlatformRepository repository, StreamingPlatformRepository streamsRepository)
    {
        super(repository);
        this.onlineplatformRepository = repository;
        
        this.streamsRepository = streamsRepository;
        
    }

    @Transactional
    @Override
    public OnlinePlatform save(OnlinePlatform onlineplatform) {
    // ---------- OneToMany ----------
        if (onlineplatform.getStreams() != null) {
            List<StreamingPlatform> managedStreams = new ArrayList<>();
            for (StreamingPlatform item : onlineplatform.getStreams()) {
                if (item.getId() != null) {
                    StreamingPlatform existingItem = streamsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("StreamingPlatform not found"));

                     existingItem.setOnlinePlatform(onlineplatform);
                     managedStreams.add(existingItem);
                } else {
                    item.setOnlinePlatform(onlineplatform);
                    managedStreams.add(item);
                }
            }
            onlineplatform.setStreams(managedStreams);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return onlineplatformRepository.save(onlineplatform);
}

    @Transactional
    @Override
    public OnlinePlatform update(Long id, OnlinePlatform onlineplatformRequest) {
        OnlinePlatform existing = onlineplatformRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("OnlinePlatform not found"));

    // Copier les champs simples
        existing.setName(onlineplatformRequest.getName());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
        existing.getStreams().clear();

        if (onlineplatformRequest.getStreams() != null) {
            for (var item : onlineplatformRequest.getStreams()) {
                StreamingPlatform existingItem;
                if (item.getId() != null) {
                    existingItem = streamsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("StreamingPlatform not found"));
                } else {
                existingItem = item;
                }

                existingItem.setOnlinePlatform(existing);
                existing.getStreams().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return onlineplatformRepository.save(existing);
}

    // Pagination simple
    public Page<OnlinePlatform> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<OnlinePlatform> search(Map<String, String> filters, Pageable pageable) {
        return super.search(OnlinePlatform.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<OnlinePlatform> saveAll(List<OnlinePlatform> onlineplatformList) {
        return super.saveAll(onlineplatformList);
    }

}