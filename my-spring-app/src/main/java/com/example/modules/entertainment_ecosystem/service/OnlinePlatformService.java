package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.OnlinePlatform;
import com.example.modules.entertainment_ecosystem.repository.OnlinePlatformRepository;
import com.example.modules.entertainment_ecosystem.model.StreamingPlatform;
import com.example.modules.entertainment_ecosystem.repository.StreamingPlatformRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class OnlinePlatformService extends BaseService<OnlinePlatform> {

    protected final OnlinePlatformRepository onlineplatformRepository;
    private final StreamingPlatformRepository streamsRepository;

    public OnlinePlatformService(OnlinePlatformRepository repository,StreamingPlatformRepository streamsRepository)
    {
        super(repository);
        this.onlineplatformRepository = repository;
        this.streamsRepository = streamsRepository;
    }

    @Override
    public OnlinePlatform save(OnlinePlatform onlineplatform) {


    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (onlineplatform.getStreams() != null) {
            List<StreamingPlatform> managedStreams = new ArrayList<>();
            for (StreamingPlatform item : onlineplatform.getStreams()) {
            if (item.getId() != null) {
            StreamingPlatform existingItem = streamsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("StreamingPlatform not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setOnlinePlatform(onlineplatform);
            managedStreams.add(existingItem);
            } else {
            item.setOnlinePlatform(onlineplatform);
            managedStreams.add(item);
            }
            }
            onlineplatform.setStreams(managedStreams);
            }
        
    

    

        return onlineplatformRepository.save(onlineplatform);
    }


    public OnlinePlatform update(Long id, OnlinePlatform onlineplatformRequest) {
        OnlinePlatform existing = onlineplatformRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("OnlinePlatform not found"));

    // Copier les champs simples
        existing.setName(onlineplatformRequest.getName());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée
        // Vider la collection existante
        existing.getStreams().clear();

        if (onlineplatformRequest.getStreams() != null) {
        for (var item : onlineplatformRequest.getStreams()) {
        StreamingPlatform existingItem;
        if (item.getId() != null) {
        existingItem = streamsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("StreamingPlatform not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setOnlinePlatform(existing);

        // Ajouter directement dans la collection existante
        existing.getStreams().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    


        return onlineplatformRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<OnlinePlatform> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

OnlinePlatform entity = entityOpt.get();

// --- Dissocier OneToMany ---

    
        if (entity.getStreams() != null) {
        for (var child : entity.getStreams()) {
        
            child.setOnlinePlatform(null); // retirer la référence inverse
        
        }
        entity.getStreams().clear();
        }
    


// --- Dissocier ManyToMany ---

    


// --- Dissocier OneToOne ---

    


// --- Dissocier ManyToOne ---

    


repository.delete(entity);
return true;
}
}