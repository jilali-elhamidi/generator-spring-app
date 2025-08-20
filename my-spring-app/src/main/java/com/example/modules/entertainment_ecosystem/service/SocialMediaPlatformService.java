package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.SocialMediaPlatform;
import com.example.modules.entertainment_ecosystem.repository.SocialMediaPlatformRepository;
import com.example.modules.entertainment_ecosystem.model.ArtistSocialMedia;
import com.example.modules.entertainment_ecosystem.repository.ArtistSocialMediaRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class SocialMediaPlatformService extends BaseService<SocialMediaPlatform> {

    protected final SocialMediaPlatformRepository socialmediaplatformRepository;
    private final ArtistSocialMediaRepository artistSocialMediaRepository;

    public SocialMediaPlatformService(SocialMediaPlatformRepository repository,ArtistSocialMediaRepository artistSocialMediaRepository)
    {
        super(repository);
        this.socialmediaplatformRepository = repository;
        this.artistSocialMediaRepository = artistSocialMediaRepository;
    }

    @Override
    public SocialMediaPlatform save(SocialMediaPlatform socialmediaplatform) {


    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (socialmediaplatform.getArtistSocialMedia() != null) {
            List<ArtistSocialMedia> managedArtistSocialMedia = new ArrayList<>();
            for (ArtistSocialMedia item : socialmediaplatform.getArtistSocialMedia()) {
            if (item.getId() != null) {
            ArtistSocialMedia existingItem = artistSocialMediaRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("ArtistSocialMedia not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setPlatform(socialmediaplatform);
            managedArtistSocialMedia.add(existingItem);
            } else {
            item.setPlatform(socialmediaplatform);
            managedArtistSocialMedia.add(item);
            }
            }
            socialmediaplatform.setArtistSocialMedia(managedArtistSocialMedia);
            }
        
    


    

    

        return socialmediaplatformRepository.save(socialmediaplatform);
    }


    public SocialMediaPlatform update(Long id, SocialMediaPlatform socialmediaplatformRequest) {
        SocialMediaPlatform existing = socialmediaplatformRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("SocialMediaPlatform not found"));

    // Copier les champs simples
        existing.setName(socialmediaplatformRequest.getName());
        existing.setUrl(socialmediaplatformRequest.getUrl());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée
        // Vider la collection existante
        existing.getArtistSocialMedia().clear();

        if (socialmediaplatformRequest.getArtistSocialMedia() != null) {
        for (var item : socialmediaplatformRequest.getArtistSocialMedia()) {
        ArtistSocialMedia existingItem;
        if (item.getId() != null) {
        existingItem = artistSocialMediaRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("ArtistSocialMedia not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setPlatform(existing);

        // Ajouter directement dans la collection existante
        existing.getArtistSocialMedia().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    


        return socialmediaplatformRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<SocialMediaPlatform> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

SocialMediaPlatform entity = entityOpt.get();

// --- Dissocier OneToMany ---

    
        if (entity.getArtistSocialMedia() != null) {
        for (var child : entity.getArtistSocialMedia()) {
        
            child.setPlatform(null); // retirer la référence inverse
        
        }
        entity.getArtistSocialMedia().clear();
        }
    


// --- Dissocier ManyToMany ---

    



// --- Dissocier OneToOne ---

    


// --- Dissocier ManyToOne ---

    


repository.delete(entity);
return true;
}
}