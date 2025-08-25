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

    public SocialMediaPlatformService(SocialMediaPlatformRepository repository, ArtistSocialMediaRepository artistSocialMediaRepository)
    {
        super(repository);
        this.socialmediaplatformRepository = repository;
        this.artistSocialMediaRepository = artistSocialMediaRepository;
    }

    @Override
    public SocialMediaPlatform save(SocialMediaPlatform socialmediaplatform) {
    // ---------- OneToMany ----------
        if (socialmediaplatform.getArtistSocialMedia() != null) {
            List<ArtistSocialMedia> managedArtistSocialMedia = new ArrayList<>();
            for (ArtistSocialMedia item : socialmediaplatform.getArtistSocialMedia()) {
                if (item.getId() != null) {
                    ArtistSocialMedia existingItem = artistSocialMediaRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ArtistSocialMedia not found"));

                     existingItem.setPlatform(socialmediaplatform);
                     managedArtistSocialMedia.add(existingItem);
                } else {
                    item.setPlatform(socialmediaplatform);
                    managedArtistSocialMedia.add(item);
                }
            }
            socialmediaplatform.setArtistSocialMedia(managedArtistSocialMedia);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return socialmediaplatformRepository.save(socialmediaplatform);
}


    public SocialMediaPlatform update(Long id, SocialMediaPlatform socialmediaplatformRequest) {
        SocialMediaPlatform existing = socialmediaplatformRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("SocialMediaPlatform not found"));

    // Copier les champs simples
        existing.setName(socialmediaplatformRequest.getName());
        existing.setUrl(socialmediaplatformRequest.getUrl());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
        existing.getArtistSocialMedia().clear();

        if (socialmediaplatformRequest.getArtistSocialMedia() != null) {
            for (var item : socialmediaplatformRequest.getArtistSocialMedia()) {
                ArtistSocialMedia existingItem;
                if (item.getId() != null) {
                    existingItem = artistSocialMediaRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ArtistSocialMedia not found"));
                } else {
                existingItem = item;
                }

                existingItem.setPlatform(existing);
                existing.getArtistSocialMedia().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
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
                // retirer la référence inverse
                child.setPlatform(null);
            }
            entity.getArtistSocialMedia().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
    @Transactional
    public List<SocialMediaPlatform> saveAll(List<SocialMediaPlatform> socialmediaplatformList) {

        return socialmediaplatformRepository.saveAll(socialmediaplatformList);
    }

}