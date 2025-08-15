package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ArtistSocialMedia;
import com.example.modules.entertainment_ecosystem.repository.ArtistSocialMediaRepository;
import com.example.modules.entertainment_ecosystem.model.Artist;
import com.example.modules.entertainment_ecosystem.repository.ArtistRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class ArtistSocialMediaService extends BaseService<ArtistSocialMedia> {

    protected final ArtistSocialMediaRepository artistsocialmediaRepository;
    private final ArtistRepository artistRepository;

    public ArtistSocialMediaService(ArtistSocialMediaRepository repository,ArtistRepository artistRepository)
    {
        super(repository);
        this.artistsocialmediaRepository = repository;
        this.artistRepository = artistRepository;
    }

    @Override
    public ArtistSocialMedia save(ArtistSocialMedia artistsocialmedia) {


    

    if (artistsocialmedia.getArtist() != null
        && artistsocialmedia.getArtist().getId() != null) {
        Artist existingArtist = artistRepository.findById(
        artistsocialmedia.getArtist().getId()
        ).orElseThrow(() -> new RuntimeException("Artist not found"));
        artistsocialmedia.setArtist(existingArtist);
        }
    

    


        return artistsocialmediaRepository.save(artistsocialmedia);
    }


    public ArtistSocialMedia update(Long id, ArtistSocialMedia artistsocialmediaRequest) {
        ArtistSocialMedia existing = artistsocialmediaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ArtistSocialMedia not found"));

    // Copier les champs simples
        existing.setPlatform(artistsocialmediaRequest.getPlatform());
        existing.setUrl(artistsocialmediaRequest.getUrl());

// Relations ManyToOne : mise à jour conditionnelle
        if (artistsocialmediaRequest.getArtist() != null &&
        artistsocialmediaRequest.getArtist().getId() != null) {

        Artist existingArtist = artistRepository.findById(
        artistsocialmediaRequest.getArtist().getId()
        ).orElseThrow(() -> new RuntimeException("Artist not found"));

        existing.setArtist(existingArtist);
        } else {
        existing.setArtist(null);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    


        return artistsocialmediaRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<ArtistSocialMedia> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

ArtistSocialMedia entity = entityOpt.get();

// --- Dissocier OneToMany ---

    


// --- Dissocier ManyToMany ---

    


// --- Dissocier OneToOne ---

    


// --- Dissocier ManyToOne ---

    
        if (entity.getArtist() != null) {
        entity.setArtist(null);
        }
    


repository.delete(entity);
return true;
}
}