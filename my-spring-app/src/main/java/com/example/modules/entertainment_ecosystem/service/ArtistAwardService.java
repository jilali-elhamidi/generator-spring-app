package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ArtistAward;
import com.example.modules.entertainment_ecosystem.repository.ArtistAwardRepository;
import com.example.modules.entertainment_ecosystem.model.Artist;
import com.example.modules.entertainment_ecosystem.repository.ArtistRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class ArtistAwardService extends BaseService<ArtistAward> {

    protected final ArtistAwardRepository artistawardRepository;
    private final ArtistRepository artistRepository;

    public ArtistAwardService(ArtistAwardRepository repository, ArtistRepository artistRepository)
    {
        super(repository);
        this.artistawardRepository = repository;
        this.artistRepository = artistRepository;
    }

    @Override
    public ArtistAward save(ArtistAward artistaward) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (artistaward.getArtist() != null) {
            if (artistaward.getArtist().getId() != null) {
                Artist existingArtist = artistRepository.findById(
                    artistaward.getArtist().getId()
                ).orElseThrow(() -> new RuntimeException("Artist not found with id "
                    + artistaward.getArtist().getId()));
                artistaward.setArtist(existingArtist);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Artist newArtist = artistRepository.save(artistaward.getArtist());
                artistaward.setArtist(newArtist);
            }
        }
        
    // ---------- OneToOne ----------
    return artistawardRepository.save(artistaward);
}


    public ArtistAward update(Long id, ArtistAward artistawardRequest) {
        ArtistAward existing = artistawardRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ArtistAward not found"));

    // Copier les champs simples
        existing.setName(artistawardRequest.getName());
        existing.setYear(artistawardRequest.getYear());

    // ---------- Relations ManyToOne ----------
        if (artistawardRequest.getArtist() != null &&
            artistawardRequest.getArtist().getId() != null) {

            Artist existingArtist = artistRepository.findById(
                artistawardRequest.getArtist().getId()
            ).orElseThrow(() -> new RuntimeException("Artist not found"));

            existing.setArtist(existingArtist);
        } else {
            existing.setArtist(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return artistawardRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<ArtistAward> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        ArtistAward entity = entityOpt.get();
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