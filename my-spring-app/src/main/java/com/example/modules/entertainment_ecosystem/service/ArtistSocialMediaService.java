package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ArtistSocialMedia;
import com.example.modules.entertainment_ecosystem.repository.ArtistSocialMediaRepository;

import com.example.modules.entertainment_ecosystem.model.Artist;
import com.example.modules.entertainment_ecosystem.repository.ArtistRepository;

import com.example.modules.entertainment_ecosystem.model.SocialMediaPlatform;
import com.example.modules.entertainment_ecosystem.repository.SocialMediaPlatformRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class ArtistSocialMediaService extends BaseService<ArtistSocialMedia> {

    protected final ArtistSocialMediaRepository artistsocialmediaRepository;
    
    protected final ArtistRepository artistRepository;
    
    protected final SocialMediaPlatformRepository platformRepository;
    

    public ArtistSocialMediaService(ArtistSocialMediaRepository repository, ArtistRepository artistRepository, SocialMediaPlatformRepository platformRepository)
    {
        super(repository);
        this.artistsocialmediaRepository = repository;
        
        this.artistRepository = artistRepository;
        
        this.platformRepository = platformRepository;
        
    }

    @Transactional
    @Override
    public ArtistSocialMedia save(ArtistSocialMedia artistsocialmedia) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (artistsocialmedia.getArtist() != null) {
            if (artistsocialmedia.getArtist().getId() != null) {
                Artist existingArtist = artistRepository.findById(
                    artistsocialmedia.getArtist().getId()
                ).orElseThrow(() -> new RuntimeException("Artist not found with id "
                    + artistsocialmedia.getArtist().getId()));
                artistsocialmedia.setArtist(existingArtist);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Artist newArtist = artistRepository.save(artistsocialmedia.getArtist());
                artistsocialmedia.setArtist(newArtist);
            }
        }
        
        if (artistsocialmedia.getPlatform() != null) {
            if (artistsocialmedia.getPlatform().getId() != null) {
                SocialMediaPlatform existingPlatform = platformRepository.findById(
                    artistsocialmedia.getPlatform().getId()
                ).orElseThrow(() -> new RuntimeException("SocialMediaPlatform not found with id "
                    + artistsocialmedia.getPlatform().getId()));
                artistsocialmedia.setPlatform(existingPlatform);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                SocialMediaPlatform newPlatform = platformRepository.save(artistsocialmedia.getPlatform());
                artistsocialmedia.setPlatform(newPlatform);
            }
        }
        
    // ---------- OneToOne ----------
    return artistsocialmediaRepository.save(artistsocialmedia);
}

    @Transactional
    @Override
    public ArtistSocialMedia update(Long id, ArtistSocialMedia artistsocialmediaRequest) {
        ArtistSocialMedia existing = artistsocialmediaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ArtistSocialMedia not found"));

    // Copier les champs simples
        existing.setUrl(artistsocialmediaRequest.getUrl());

    // ---------- Relations ManyToOne ----------
        if (artistsocialmediaRequest.getArtist() != null &&
            artistsocialmediaRequest.getArtist().getId() != null) {

            Artist existingArtist = artistRepository.findById(
                artistsocialmediaRequest.getArtist().getId()
            ).orElseThrow(() -> new RuntimeException("Artist not found"));

            existing.setArtist(existingArtist);
        } else {
            existing.setArtist(null);
        }
        
        if (artistsocialmediaRequest.getPlatform() != null &&
            artistsocialmediaRequest.getPlatform().getId() != null) {

            SocialMediaPlatform existingPlatform = platformRepository.findById(
                artistsocialmediaRequest.getPlatform().getId()
            ).orElseThrow(() -> new RuntimeException("SocialMediaPlatform not found"));

            existing.setPlatform(existingPlatform);
        } else {
            existing.setPlatform(null);
        }
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return artistsocialmediaRepository.save(existing);
}

    // Pagination simple
    public Page<ArtistSocialMedia> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<ArtistSocialMedia> search(Map<String, String> filters, Pageable pageable) {
        return super.search(ArtistSocialMedia.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<ArtistSocialMedia> saveAll(List<ArtistSocialMedia> artistsocialmediaList) {
        return super.saveAll(artistsocialmediaList);
    }

}